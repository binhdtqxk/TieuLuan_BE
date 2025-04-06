package com.essay.TieuLuan_BE.controller;

import com.essay.TieuLuan_BE.service.TwitService;
import com.essay.TieuLuan_BE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/twits")
public class TwitController {
    @Autowired
    private TwitService twitService;

    @Autowired
    private UserService userService;

}
