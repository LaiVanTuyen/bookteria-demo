package com.vti.profile.mapper;

import org.mapstruct.Mapper;

import com.vti.profile.dto.request.ProfileCreationRequest;
import com.vti.profile.dto.response.UserProfileReponse;
import com.vti.profile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileReponse toUserProfileReponse(UserProfile entity);
}
