package com.litarary.profile.service;

import com.litarary.category.service.dto.PageBookInfo;
import com.litarary.profile.domain.RegisterDate;
import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import org.springframework.data.domain.PageRequest;

public interface ProfileService {
    MemberProfileDto findUserProfile(Long memberId);

    void updateProfile(Long memberId, UpdateProfile updateProfile);

    PageBookInfo registerBooks(Long memberId, PageRequest pageRequest, RegisterDate registerDate);
}
