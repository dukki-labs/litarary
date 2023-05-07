package com.litarary.profile.controller;

import com.litarary.category.service.dto.PageBookInfo;
import com.litarary.profile.controller.dto.RegisterBookDto;
import com.litarary.profile.controller.dto.UpdateProfileDto;
import com.litarary.profile.controller.mapper.ProfileMapper;
import com.litarary.profile.domain.RegisterDate;
import com.litarary.profile.service.ProfileService;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@Validated
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

    @GetMapping("/register/books")
    public PageBookInfo registerBooks(@RequestAttribute("memberId") Long memberId,
                                      @Valid @ModelAttribute RegisterBookDto.Request request) {

        int page = request.getPage();
        int size = request.getSize();
        page -= 1;
        return profileService.registerBooks(memberId, PageRequest.of(page, size), request.getRegisterDate());
    }
}
