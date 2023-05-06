package com.litarary.profile.controller.mapper;

import com.litarary.profile.controller.dto.UpdateProfileDto;
import com.litarary.profile.service.dto.UpdateProfile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public UpdateProfile toUpdateProfile(UpdateProfileDto.Request request) {
        return UpdateProfile.builder()
                .nickName(request.getNickName())
                .password(request.getPassword())
                .bookCategoryList(request.getBookCategoryList())
                .build();
    }
}
