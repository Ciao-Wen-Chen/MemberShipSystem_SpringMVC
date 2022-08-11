package com.sdproject.membersystem.member;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMembers(){
        return ResponseEntity.ok().body(memberService.getMembers());
    }

    //When want to create resource into the server, use post
    @PostMapping("/member/updateRole")
    public ResponseEntity<?> updateUserRole(@RequestBody RoleToUserForm form){
        memberService.updateUserRole(form.getEmail(), form.getMemberRole());
        return ResponseEntity.ok().build();
    }
}

@Data
class RoleToUserForm{
    private String email;
    private MemberRole memberRole;
}
