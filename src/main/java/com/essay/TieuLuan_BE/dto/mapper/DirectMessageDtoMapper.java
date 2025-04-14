//package com.essay.TieuLuan_BE.dto.mapper;
//
//import com.essay.TieuLuan_BE.dto.DirectMessageDto;
//import com.essay.TieuLuan_BE.entity.DirectMessage;
//
//public class DirectMessageDtoMapper {
//    public static DirectMessageDto toDirectMessageDto(DirectMessage directMessage) {
//        if (directMessage == null) {
//            return null;
//        }
//        DirectMessageDto dto = new DirectMessageDto();
//        dto.setId(directMessage.getId());
//        dto.setSender(UserDtoMapper.toUserDto(directMessage.getSender()));
//        dto.setRecipient(UserDtoMapper.toUserDto(directMessage.getRecipient()));
//        dto.setContent(directMessage.getContent());
//        dto.setCreatedAt(directMessage.getCreatedAt());
//        dto.setRead(directMessage.isRead());
//        return dto;
//    }
//}
