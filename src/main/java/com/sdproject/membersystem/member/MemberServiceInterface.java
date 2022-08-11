package com.sdproject.membersystem.member;

import java.util.List;

public interface MemberServiceInterface {

    Member getMember(String email);
    List<Member> getMembers();

}
