package com.litarary.profile.controller;

import com.litarary.profile.service.ProfileService;
import com.litarary.profile.service.dto.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;
    @GetMapping
    public MemberProfileDto findUserProfile(@RequestAttribute("memberId") Long memberId) {
        return profileService.findUserProfile(memberId);
    }
}
