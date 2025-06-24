package com.example.demo.google;

import com.example.demo.constant.constant;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class LoginGoogle {

    private static final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(),
            new GsonFactory()
    )
            .setAudience(Collections.singletonList(constant.GOOGLE_CLIENT_ID))
            .build();

    public static GoogleIdToken.Payload verifyToken(String idTokenString) throws GeneralSecurityException, IOException {
        if (idTokenString == null || idTokenString.isEmpty()) {
            return null;
        }

        GoogleIdToken idToken = verifier.verify(idTokenString);
        return (idToken != null) ? idToken.getPayload() : null;
    }
}
