package com.litarary.profile.controller;

import com.litarary.profile.controller.dto.UpdateProfileDto;
import com.litarary.profile.controller.mapper.ProfileMapper;
import com.litarary.profile.service.ProfileService;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    @GetMapping
    public MemberProfileDto findUserProfile(@RequestAttribute("memberId") Long memberId) {
        return profileService.findUserProfile(memberId);
    }

    @PostMapping
    public void updateProfile(@RequestAttribute("memberId") Long memberId,
                              @Valid @RequestBody UpdateProfileDto.Request request) {
        UpdateProfile updateProfile = profileMapper.toUpdateProfile(request);
        profileService.updateProfile(memberId, updateProfile);
    }
}
