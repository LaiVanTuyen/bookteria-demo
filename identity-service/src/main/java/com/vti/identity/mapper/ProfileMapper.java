package com.vti.identity.mapper;

import com.vti.identity.dto.request.ProfileCreationRequest;
import com.vti.identity.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
