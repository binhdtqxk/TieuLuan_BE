package com.essay.TieuLuan_BE.service.messageService;

import com.essay.TieuLuan_BE.dto.DirectMessageDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.entity.User;

import java.util.List;

public interface MessageService {
    DirectMessageDto sendMessage(User sender, Long recipientId, String content, String imageUrl);
    //Get list of users that had a conversion with req_user before
    List<UserDto> listConversationPartners(User me);
    //Get conversion with specify user
    List<DirectMessageDto> getConversation(Long meId, Long otherId);
}
