package com.essay.TieuLuan_BE.dto.mapper;


import com.essay.TieuLuan_BE.dto.DirectMessageDto;
import com.essay.TieuLuan_BE.entity.DirectMessage;

public class DirectMessageDtoMapper {
    public static DirectMessageDto toDto(DirectMessage dm) {
        DirectMessageDto dto = new DirectMessageDto();
        dto.setId(dm.getId());
        dto.setSender(UserDtoMapper.toUserSummaryDto(dm.getSender())); //only map fullName, id and image
        dto.setRecipient(UserDtoMapper.toUserSummaryDto(dm.getRecipient()));
        dto.setContent(dm.getContent());
        dto.setImageUrl(dm.getImageUrl());
        dto.setCreatedAt(dm.getCreatedAt());
        return dto;
    }
}
