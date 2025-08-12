package com.vti.profile.mapper;

import com.vti.profile.dto.request.UpdateProfileRequest;
import com.vti.profile.dto.response.UserProfileResponse;
import org.mapstruct.Mapper;

import com.vti.profile.dto.request.ProfileCreationRequest;
import com.vti.profile.entity.UserProfile;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);
    void update(@MappingTarget UserProfile entity, UpdateProfileRequest request);
    UserProfileResponse toUserProfileResponse(UserProfile entity);
}
