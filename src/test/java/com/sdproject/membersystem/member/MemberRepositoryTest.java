package com.sdproject.membersystem.member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ContextConfiguration
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository testRepository;

    @Test
    void checkIfEmailExist() {

        String email="2681496c@student.gla.ac.uk";
        String firstname ="testFirstname";
        String lastname = "testLastname";
        String password="testPassWord";
        Member member = new Member(firstname, email, lastname, password, MemberRole.MEMBER_ROLE);

        testRepository.save(member);
        Boolean testMember = testRepository.findByEmail(email).isPresent();;
        assertThat(testMember).isTrue();
    }
    @Test
    void checkIfEmailNotExist() {

        String email="2681496c@student.gla.ac.uk";
        String firstname ="testFirstname";
        String lastname = "testLastname";
        String password="testPassWord";
        Member member = new Member(firstname, email, lastname, password, MemberRole.MEMBER_ROLE);

        testRepository.save(member);
        Boolean testMember = testRepository.findByEmail(email=".tw").isPresent();;
        assertThat(testMember).isFalse();
    }

//    @Test
//    void checkIfEmailBeEnable() {
//
//        String email="2681496c@student.gla.ac.uk";
//        String firstname ="testFirstname";
//        String lastname = "testLastname";
//        String password="testPassWord";
//        Member member = new Member(firstname, email, lastname, password, MemberRole.MEMBER_ROLE);
//
//        testRepository.save(member);
//        testRepository.enableMember(email);
//        int ifEnable=testRepository.getIfEnable(email);
//        assertThat(ifEnable).isEqualTo(1);
//    }

//    @Test
//    void checkIfEmailNotBeEnable() {
//
//    }
}
