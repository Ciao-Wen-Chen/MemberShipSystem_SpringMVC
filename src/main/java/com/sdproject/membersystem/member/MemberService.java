package com.sdproject.membersystem.member;

import com.sdproject.membersystem.registration.token.ConfirmationToken;
import com.sdproject.membersystem.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MemberService implements UserDetailsService, MemberServiceInterface{

    private final static String USER_NOT_FOUND_MSG=
            "User with %s%s not found";
    @Autowired
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override //able us to find the user by userName
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return memberRepository.findByEmail(email)
//            .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, "email" ,email)));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, "email" ,email)));
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(member.getMemberRole().toString()));
        return new org.springframework.security.core.userdetails.User(member.getEmail(),member.getPassword(),authorities);

    }

    //confirm link
    public String signUpUser(Member member){
        boolean userExists=memberRepository
                .findByEmail(member.getEmail())
                .isPresent();
        if(userExists){
            //TODO: if the attributes are the same and
            //TODO: if email not confirmed, send confirmation email
            throw new IllegalStateException("email are already taken");
        }

        String encodedPassword=bCryptPasswordEncoder
                .encode(member.getPassword());

        member.setPassword(encodedPassword);
        memberRepository.save(member);

        String token= UUID.randomUUID().toString();
        ConfirmationToken confirmationToken=new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            member
        );
        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        //TODO: send email
        return token;
    }

    public int enableMember(String email) {
        return memberRepository.enableMember(email);
    }

    public Member getMember(String email){
        log.info("Fetching user {}", email);
        return memberRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, "email" ,email)));
    }
    public List<Member> getMembers(){
        log.info("Fetching users");
        return memberRepository.findAll();
    }
    public void updateUserRole(String email, MemberRole memberRole){
        log.info("updating role {} to user {}", memberRole, email);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, "email" ,email)));
        member.setMemberRole(memberRole);
    }
}
