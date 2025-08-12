package com.vti.profile.controller;

import com.vti.profile.dto.ApiResponse;
import com.vti.profile.dto.request.ProfileCreationRequest;
import com.vti.profile.dto.response.UserProfileResponse;
import com.vti.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/internal")
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users/register")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.createProfile(request))
                .build();
    }

    @GetMapping("/{profileId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String profileId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfile(profileId))
                .build();
    }

    @GetMapping("/users/{userId}")
    ApiResponse<UserProfileResponse> getProfileByUserId(@PathVariable String userId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfileByUserId(userId))
                .build();
    }
}
