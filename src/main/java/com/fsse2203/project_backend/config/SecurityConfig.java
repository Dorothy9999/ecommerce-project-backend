package com.fsse2203.project_backend.config;

import com.fsse2203.project_backend.security.FirebaseSecurityFilter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private FirebaseSecurityFilter firebaseSecurityFilter;

    @PostConstruct //do this after Springboot starts
    public void initFirebase() throws IOException {
        InputStream serviceAccount = new
                ClassPathResource("firebase_config.json").getInputStream();
        //put the json file here
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        FirebaseApp.initializeApp(options);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()//停左springboot的auth server,不用它
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .anyRequest().authenticated() //set左any request都要authenticate
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .addFilterBefore(firebaseSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                //每做一次request前都做一次firebaseSecurityfilter, which is係我地個class
                .csrf().disable();

        ;
    }
}
