package com.vti.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vti.identity.dto.request.RoleRequest;
import com.vti.identity.dto.response.RoleResponse;
import com.vti.identity.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
