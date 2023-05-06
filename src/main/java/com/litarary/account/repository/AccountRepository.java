package com.litarary.account.repository;

import com.litarary.account.domain.UseYn;
import com.litarary.account.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmailAndUseYn(String email, UseYn useYn);

    boolean existsByEmailAndUseYn(String email, UseYn useYn);

    Member findByEmail(String email);

    boolean existsByNickName(String nickname);
}
