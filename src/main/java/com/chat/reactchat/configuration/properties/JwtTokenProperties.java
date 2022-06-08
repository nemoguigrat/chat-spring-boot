package com.chat.reactchat.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@Getter
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtTokenProperties {
    private final String keystorePath;
    private final String keystorePassword;
    private final String keyAlias;
    private final String privateKeyPassphrase;

}
