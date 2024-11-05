package com.vti.profile.controller;

import com.vti.profile.dto.response.UserProfileResponse;
import org.springframework.web.bind.annotation.*;

import com.vti.profile.dto.request.ProfileCreationRequest;
import com.vti.profile.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/users")
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }

    @GetMapping("/users/{profileId}")
    UserProfileResponse getProfile(@PathVariable String profileId) {
        return userProfileService.getProfile(profileId);
    }

    @GetMapping("/users/userId={userId}")
    UserProfileResponse getProfileByUserId(@PathVariable String userId) {
        return userProfileService.getProfileByUserId(userId);
    }
}
