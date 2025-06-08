package com.essay.TieuLuan_BE.service.messageService;

import com.essay.TieuLuan_BE.dto.DirectMessageDto;
import com.essay.TieuLuan_BE.dto.UserDto;
import com.essay.TieuLuan_BE.dto.mapper.DirectMessageDtoMapper;
import com.essay.TieuLuan_BE.dto.mapper.UserDtoMapper;
import com.essay.TieuLuan_BE.entity.DirectMessage;
import com.essay.TieuLuan_BE.entity.User;
import com.essay.TieuLuan_BE.repository.DirectMessageRepository;
import com.essay.TieuLuan_BE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private static final String TOPIC = "message";

    @Autowired private DirectMessageRepository dmRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private KafkaTemplate<String, DirectMessageDto> kafkaTemplate;

    @Override
    public DirectMessageDto sendMessage(User sender, Long recipientId, String content, String imageUrl) {
        User recipient = userRepo.findById(recipientId).orElseThrow(() -> new RuntimeException("recipient not found"));

        DirectMessage dm = new DirectMessage();
        dm.setSender(sender);
        dm.setRecipient(recipient);
        dm.setContent(content);
        dm.setImageUrl(imageUrl);
        dm.setCreatedAt(LocalDateTime.now());
        DirectMessage saved = dmRepo.save(dm);

        DirectMessageDto dto = DirectMessageDtoMapper.toDto(saved);
        kafkaTemplate.send(TOPIC, dto);
        return dto;
    }

    @Override
    public List<UserDto> listConversationPartners(User me) {
        List<DirectMessage> all = dmRepo.findBySenderOrRecipientOrderByCreatedAtDesc(me, me);
        LinkedHashSet<UserDto> partners = new LinkedHashSet<>(); //use linkedHashSet to ensure that all partner in order and unique
        for (DirectMessage m : all) {
            UserDto other = m.getSender().equals(me)
                    ? UserDtoMapper.toUserSummaryDto(m.getRecipient())
                    : UserDtoMapper.toUserSummaryDto(m.getSender());
            partners.add(other);
        }
        return new ArrayList<>(partners);
    }

    @Override
    public List<DirectMessageDto> getConversation(Long meId, Long otherId) {
        List<DirectMessage> conv = dmRepo.findConversation(meId, otherId);
        return conv.stream()
                .map(DirectMessageDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}