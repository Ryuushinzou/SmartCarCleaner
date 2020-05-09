package ui.controller;

import net.jodah.expiringmap.ExpiringMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginService {

    private static final String LOGIN_URL = "http://localhost:8080/login?userName=washingStation&password=washingStation";
    private static final String ACTIVE_JWT = "Active-JWT";
    private Map<String, String> jwtMap = ExpiringMap.builder().expiration(10, TimeUnit.MINUTES).build();

    public String getValidJwt() {

        String jwt = jwtMap.get(ACTIVE_JWT);
        while (jwt == null) {
            try {
                login();
            } catch (IOException e) {
                e.printStackTrace();
            }
            jwt = jwtMap.get(ACTIVE_JWT);
        }
        return jwt;
    }

    private void login() throws IOException {

        URL obj = new URL(LOGIN_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            jwtMap.put(ACTIVE_JWT, response.toString());
        }
    }
}
