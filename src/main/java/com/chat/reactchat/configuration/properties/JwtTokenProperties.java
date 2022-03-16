package com.chat.reactchat.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Getter
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProperties {
    private final String secret;

    private final Long lifetime;
}
