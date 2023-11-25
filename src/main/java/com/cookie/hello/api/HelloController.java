package com.cookie.hello.api;

import com.cookie.global.response.DataResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public DataResponseDto<String> hello() {
        return DataResponseDto.from("Hello, World!");
    }

}
