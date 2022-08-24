package com.sdproject.membersystem.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly=true)
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Member a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableMember(String email);

//    @Transactional
//    @Query("SELECT enabled e " +
//            "FROM member " +
//            "where email=?1")
//    int getIfEnable(String email);
}

