package com.fsse2203.project_backend.security;

import com.fsse2203.project_backend.data.user.CreateUserData;
import com.fsse2203.project_backend.data.user.UserDetailsData;
import com.fsse2203.project_backend.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//每有request就行一次，靠佢做verify
@Component
public class FirebaseSecurityFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Autowired
    public FirebaseSecurityFilter (UserService userService) {
        this.userService = userService;
    }

    private Logger log =
            LoggerFactory.getLogger(FirebaseSecurityFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        verifyToken(request);
        filterChain.doFilter(request, response);
    }

    private void verifyToken(HttpServletRequest request) {
        FirebaseToken decodedToken = null;
        String token = getBearerToken(request); //extract出黎的access token
        try {
            if (token != null) {
                decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            log.error("Firebase Exception", e);
        }
        if (decodedToken != null) {
            //-----自己加的-----//
            UserDetailsData loginUser
                    = userService.getUserDetailsByFirebaseUid(decodedToken.getUid());

            if (loginUser == null ) {
                //uid是null的話,姐係database入面無,就create一個加返入去
                CreateUserData createUserData = new CreateUserData();
                createUserData.setFirebaseUid(decodedToken.getUid());
                createUserData.setEmail(decodedToken.getEmail());
                userService.createUser(createUserData);
            }
            //----------------//
            //將firebase token放入springboot入面:
            PreAuthenticatedAuthenticationToken authentication = new
                    PreAuthenticatedAuthenticationToken(decodedToken, token, null);
            authentication.setDetails(new
                    WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    //receive the access token
    private String getBearerToken(HttpServletRequest request) {
        log.info("start to run getBearer token");
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (authorization != null && !authorization.isEmpty() &&
                authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }
}
