package com.cookie.controller;

import com.cookie.global.response.DataResponseDto;
import com.cookie.service.S3UploaderService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
public class S3UploaderController {

    private final S3UploaderService s3UploaderService;

    @PostMapping
    public DataResponseDto<Map<String, String>> uploadFile(MultipartFile multipartFile) {

        return DataResponseDto.from(s3UploaderService.uploadFile(multipartFile));
    }
}
