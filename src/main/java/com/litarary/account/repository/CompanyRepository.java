package com.litarary.account.repository;

import com.litarary.account.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    public Company findByName(String name);
}
