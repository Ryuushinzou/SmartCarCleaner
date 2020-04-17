package com.scc.app.authentication;


import com.scc.app.model.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TokenGenerator {

    private final String SECRET_KEY = "mySecretKey";

    private String createJWT(String userName, UserType userType) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Date exp = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));

        JwtBuilder builder = Jwts.builder()
                .setSubject(userName)
                .setExpiration(exp)
                .setAudience(userType.toString())
                .signWith(signatureAlgorithm, signingKey);

        System.out.println("JWT:" + builder.compact());

        return builder.compact();
    }

    public String authenticateUser(String userName, UserType userType) {

        return createJWT(userName, userType);
    }

    private Claims decodeJWT(String jwt) {

        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
    }

    public boolean authenticatedUser(String authenticatedJwt) {

        Claims claims = decodeJWT(authenticatedJwt);
        Date expirationDate = claims.getExpiration();
        return expirationDate.after(new Date());
    }
}