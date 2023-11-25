package com.cookie.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info().title("Cookie API")
            .description("5th Neordinary Hackathon Team O의 Cookie 서비스 API 문서입니다.")
            .version("1.0.0");

        return new OpenAPI()
            .components(new Components())
            .info(info);
    }
}
