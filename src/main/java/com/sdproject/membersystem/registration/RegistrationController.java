package com.sdproject.membersystem.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(path="api/member/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){

        return registrationService.register(request);
    }

    @GetMapping(path="confirm")
    public String confirm(@RequestParam("token")String token){
        return registrationService.confirmToken(token);
    }
}
