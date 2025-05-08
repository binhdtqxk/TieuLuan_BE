package com.essay.TieuLuan_BE.dto.mapper;

import com.essay.TieuLuan_BE.dto.RoleDto;
import com.essay.TieuLuan_BE.entity.Role;

public class RoleDtoMapper {
    public static RoleDto toRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setRole(role.getRole());
        return roleDto;
    }
}
