package com.litarary.profile.service.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberProfileDto {

    private String nickName;
    private String email;
    private List<CategoryInfo> categoryInfoList;
}
