package com.vti.profile.controller;

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
public class InternalUserProfileController {
    UserProfileService userProfileService;

    @PostMapping("/internal/users")
    UserProfileResponse createProfile(@RequestBody ProfileCreationRequest request) {
        return userProfileService.createProfile(request);
    }

    @GetMapping("/internal/{profileId}")
    UserProfileResponse getProfile(@PathVariable String profileId) {
        return userProfileService.getProfile(profileId);
    }

    @GetMapping("/internal/users/{userId}")
    UserProfileResponse getProfileByUserId(@PathVariable String userId) {
        return userProfileService.getProfileByUserId(userId);
    }
}
