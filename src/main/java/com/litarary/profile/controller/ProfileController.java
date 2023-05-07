package com.litarary.profile.controller;

import com.litarary.category.service.dto.PageBookInfo;
import com.litarary.profile.controller.dto.RegisterBookDto;
import com.litarary.profile.controller.dto.UpdateProfileDto;
import com.litarary.profile.controller.mapper.ProfileMapper;
import com.litarary.profile.domain.RentalBookPageInfo;
import com.litarary.profile.service.ProfileService;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
@Validated
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public MemberProfileDto findUserProfile(@RequestAttribute("memberId") Long memberId) {
        return profileService.findUserProfile(memberId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProfile(@RequestAttribute("memberId") Long memberId,
                              @Valid @RequestBody UpdateProfileDto.Request request) {
        UpdateProfile updateProfile = profileMapper.toUpdateProfile(request);
        profileService.updateProfile(memberId, updateProfile);
    }

    @GetMapping("/register/books")
    @ResponseStatus(HttpStatus.OK)
    public PageBookInfo registerBooks(@RequestAttribute("memberId") Long memberId,
                                      @Valid @ModelAttribute RegisterBookDto.Request request) {

        int page = request.getPage();
        int size = request.getSize();
        page -= 1;
        return profileService.registerBooks(memberId, PageRequest.of(page, size), request.getRegisterDate());
    }

    @DeleteMapping("/register/books/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRegisterBook(@RequestAttribute("memberId") Long memberId,
                                   @PathVariable Long bookId) {
        profileService.deleteRegisterBook(memberId, bookId);
    }

    @GetMapping("/rental/history/books")
    @ResponseStatus(HttpStatus.OK)
    public RentalBookPageInfo rentalHistoryBooks(@RequestAttribute("memberId") Long memberId,
                                                 @Valid @ModelAttribute RegisterBookDto.Request request) {
        final int page = request.getPage();
        final int size = request.getSize();

        return profileService.rentalBooksHistory(memberId, PageRequest.of(page - 1, size), request.getRegisterDate());
    }
}
