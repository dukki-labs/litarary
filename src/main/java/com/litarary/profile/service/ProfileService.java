package com.litarary.profile.service;

import com.litarary.profile.service.dto.MemberProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;

public interface ProfileService {
    MemberProfileDto findUserProfile(Long memberId);

    void updateProfile(Long memberId, UpdateProfile updateProfile);
}
