package com.sdproject.membersystem.member;

import com.sdproject.membersystem.registration.token.ConfirmationToken;
import com.sdproject.membersystem.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG=
            "User with %s%s not found";
    @Autowired
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override //able us to find the user by userName
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
            .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, "email" ,email)));
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
}
