package com.litarary.account.service;

import com.litarary.account.domain.CompanyNameFinder;
import com.litarary.account.domain.entity.Company;
import com.litarary.account.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompanyService {

    private final CompanyNameFinder companyNameFinder;
    private final CompanyRepository companyRepository;

    public Company findCompany(String email) {
        String companyName = companyNameFinder.findCompanyName(email);
        Company company = companyRepository.findByName(companyName);
        if (company == null) {
            return companyRepository.save(
                    Company.builder()
                            .name(companyName)
                            .build()
            );
        }
        return company;
    }
}
