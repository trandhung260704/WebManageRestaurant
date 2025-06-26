package com.example.demo.constant;

public class constant {
    public static final String GOOGLE_CLIENT_ID = System.getenv("618245494307-18lbr06vl4h53ou72dv82c5r5uhgovqp.apps.googleusercontent.com");
    public static final String GOOGLE_CLIENT_SECRET = System.getenv("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");
    public static final String GOOGLE_REDIRECT_URI = "http://localhost:8080/oauth2/callback";
    public static final String GOOGLE_GRANT_TYPE = "authorization_code";
    public static final String GOOGLE_LINK_GET_TOKEN = "https://oauth2.googleapis.com/token";
    public static final String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v2/userinfo?access_token=";
}
