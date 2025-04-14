//package com.essay.TieuLuan_BE.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "direct_messages")
//@Data
//public class DirectMessage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @ManyToOne
//    private User sender;
//
//    @ManyToOne
//    private User recipient;
//
//    private String content;
//
//    private LocalDateTime createdAt;
//
//    private boolean read;
//}
