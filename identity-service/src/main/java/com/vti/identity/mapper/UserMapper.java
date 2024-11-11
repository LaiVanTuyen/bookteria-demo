package com.vti.identity.mapper;

import com.vti.identity.dto.request.ApiResponse;
import com.vti.identity.dto.response.UserProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.vti.identity.dto.request.UserCreationRequest;
import com.vti.identity.dto.request.UserUpdateRequest;
import com.vti.identity.dto.response.UserResponse;
import com.vti.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
