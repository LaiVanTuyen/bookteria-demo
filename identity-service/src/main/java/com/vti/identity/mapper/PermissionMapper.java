package com.vti.identity.mapper;

import org.mapstruct.Mapper;

import com.vti.identity.dto.request.PermissionRequest;
import com.vti.identity.dto.response.PermissionResponse;
import com.vti.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
