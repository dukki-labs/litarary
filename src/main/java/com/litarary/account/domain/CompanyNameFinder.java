package com.litarary.account.domain;

import org.springframework.stereotype.Component;

@Component
public class CompanyNameFinder {

    public String findCompanyName(String email) {
        String[] emailSplit = email.split("@");
        String[] domainSplit = emailSplit[1].split("\\.");
        return domainSplit[0];
    }
}
