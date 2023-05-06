package com.litarary.profile.service;

import com.litarary.profile.service.dto.MemberProfileDto;

public interface ProfileService {
    MemberProfileDto findUserProfile(Long memberId);
}
