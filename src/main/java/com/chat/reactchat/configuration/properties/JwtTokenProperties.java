package com.chat.reactchat.configuration.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProperties {
    private String secret;

    private Long lifetime;
}
