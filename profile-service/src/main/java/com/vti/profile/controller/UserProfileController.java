package com.vti.profile.controller;

import com.vti.profile.dto.ApiResponse;
import com.vti.profile.dto.response.UserProfileResponse;
import org.springframework.web.bind.annotation.*;

import com.vti.profile.dto.request.ProfileCreationRequest;
import com.vti.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users")
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

    @GetMapping("/users")
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.getAllProfiles())
                .build();
    }

}
