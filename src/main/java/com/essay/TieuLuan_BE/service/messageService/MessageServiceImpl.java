package com.essay.TieuLuan_BE.service.messageService;

import com.essay.TieuLuan_BE.dto.DirectMessageDto;
import com.essay.TieuLuan_BE.dto.mapper.DirectMessageDtoMapper;
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
    public DirectMessageDto sendMessage(Long senderId, Long recipientId, String content, String imageUrl) {
        User sender = userRepo.findById(senderId).orElseThrow();
        User recipient = userRepo.findById(recipientId).orElseThrow();

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
    public List<Long> listConversationPartners(User me) {
        List<DirectMessage> all = dmRepo.findBySenderOrRecipientOrderByCreatedAtDesc(me, me);
        LinkedHashSet<Long> partners = new LinkedHashSet<>(); //use linkedHashSet to ensure that all partner in order and unique
        for (DirectMessage m : all) {
            Long other = m.getSender().equals(me)
                    ? m.getRecipient().getId()
                    : m.getSender().getId();
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