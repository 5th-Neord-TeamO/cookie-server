package com.cookie.controller;

import com.cookie.global.response.DataResponseDto;
import com.cookie.service.S3UploaderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "S3 파일 업로드 관련 API")
@RestController
@RequestMapping("/api/v1/s3")
@RequiredArgsConstructor
public class S3UploaderController {

    private final S3UploaderService s3UploaderService;

    @PostMapping
    public DataResponseDto<Map<String, String>> uploadFile(@RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile) {
        return DataResponseDto.from(s3UploaderService.uploadFile(multipartFile));
    }
}
