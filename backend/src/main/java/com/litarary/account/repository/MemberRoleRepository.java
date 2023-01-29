package com.litarary.account.repository;

import com.litarary.account.domain.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
