package com.vti.profile.repository.httpClient;

import com.vti.profile.configuration.AuthenticationRequestInterceptor;
import com.vti.profile.dto.ApiResponse;
import com.vti.profile.dto.response.FileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service", url = "${file.services.url}",
    configuration = AuthenticationRequestInterceptor.class)
public interface FileClient {
    @PostMapping(value = "/file/media/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FileResponse> uploadMedia(@RequestPart("file") MultipartFile file);
}
