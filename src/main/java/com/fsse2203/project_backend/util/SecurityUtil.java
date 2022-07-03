package com.fsse2203.project_backend.util;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;


public class SecurityUtil {
    public static String getFirebaseUid (Authentication authentication) {
        FirebaseToken token = (FirebaseToken)authentication.getPrincipal();
        return token.getUid();
    }
}
