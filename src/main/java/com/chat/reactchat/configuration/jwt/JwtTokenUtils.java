package com.chat.reactchat.configuration.jwt;

import com.chat.reactchat.configuration.properties.JwtTokenProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@EnableConfigurationProperties(JwtTokenProperties.class)
public class JwtTokenUtils {
    private final JwtTokenProperties properties;

    private final SecretKeySpec key;

    public JwtTokenUtils(JwtTokenProperties properties) {
        this.properties = properties;
        String encodedKey = Base64.getEncoder().encodeToString(properties.getSecret().getBytes());
        this.key = new SecretKeySpec(encodedKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateJwtToken(Long id) {
        return Jwts.builder()
                .setSubject(id.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + properties.getLifetime()))
                .signWith(key).compact();
    }

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public String getIdFromJwtToken(String jwt) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public String getToken(String rawToken) {
        if (StringUtils.hasText(rawToken) && rawToken.startsWith("Bearer ")) {
            return rawToken.substring(7);
        }
        return null;
    }

}
