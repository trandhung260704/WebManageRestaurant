package com.example.demo.google;

import com.example.demo.Entity.Person;
import com.example.demo.constant.constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;

import java.io.IOException;

public class LoginGoogle {

    public static String getAccessToken(String code) throws IOException {
        String response = Request.post(constant.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(
                        Form.form()
                                .add("client_id", constant.GOOGLE_CLIENT_ID)
                                .add("client_secret", constant.GOOGLE_CLIENT_SECRET)
                                .add("redirect_uri", constant.GOOGLE_REDIRECT_URI)
                                .add("code", code)
                                .add("grant_type", constant.GOOGLE_GRANT_TYPE)
                                .build()
                )
                .execute().returnContent().asString();

        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        return jsonObject.get("access_token").getAsString();
    }

    public static Person getUserInfo(String accessToken) throws IOException {
        String requestUrl = constant.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.get(requestUrl).execute().returnContent().asString();

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();

        Person person = new Person();
        person.setEmail(json.has("email") ? json.get("email").getAsString() : null);
        person.setName(json.has("name") ? json.get("name").getAsString() : null);
        person.setGender(json.has("gender") ? json.get("gender").getAsString() : null);

        return person;
    }
}
