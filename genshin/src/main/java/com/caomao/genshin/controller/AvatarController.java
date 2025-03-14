package com.caomao.genshin.controller;

import com.caomao.genshin.service.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @GetMapping("/avatars/{id}")
    public String fetchAndStoreAvatarData(@PathVariable String id) {
        return avatarService.fetchAndStoreAvatarData(id);
    }

}
