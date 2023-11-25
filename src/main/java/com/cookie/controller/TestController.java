package com.cookie.controller;

import com.cookie.global.response.DataResponseDto;
import com.cookie.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping
    public DataResponseDto<String> test(@RequestHeader("Authorization") String authorization) {

        return DataResponseDto.from(testService.test(authorization));
    }
}
