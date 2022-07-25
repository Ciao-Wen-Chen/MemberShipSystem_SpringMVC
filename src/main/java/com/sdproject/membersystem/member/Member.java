package com.sdproject.membersystem.member;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
//@Table(name="member")
public class Member implements UserDetails {

    @SequenceGenerator(
            name="member_sequence",
            sequenceName="member_sequence",
            allocationSize=1)
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "member_sequence")
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    private Boolean locked=false;
    private Boolean enabled=false;

    public Member(String name, String email,
                  String lastName,String password,
                  MemberRole memberRole) {
        this.firstName = name;
        this.email = email;
        this.lastName = lastName;
        this.password = password;
        this.memberRole = memberRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(memberRole.name());
        return Collections.singletonList(authority);
    }
    public String getFirstName(){
        return firstName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
