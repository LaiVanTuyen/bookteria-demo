package com.vti.identity.repository.httpClient;

import com.vti.identity.configuration.AuthenticationRequestInterceptor;
import com.vti.identity.dto.request.ApiResponse;
import com.vti.identity.dto.request.ProfileCreationRequest;
import com.vti.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "profile-service", url = "${profile.services.url}", configuration = {AuthenticationRequestInterceptor.class})
public interface ProfileClient {

    @PostMapping(value = "/users/register", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> getProfileByUserId(
            @PathVariable String userId);
}
