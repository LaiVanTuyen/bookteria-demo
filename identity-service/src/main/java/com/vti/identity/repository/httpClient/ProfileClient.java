package com.vti.identity.repository.httpClient;

import com.vti.identity.dto.request.ProfileCreationRequest;
import com.vti.identity.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile}")
public interface ProfileClient {
    @PostMapping(value = "/users/register", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request);

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse getProfileByUserId(@PathVariable String userId);
}
