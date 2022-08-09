package com.sdproject.membersystem.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping(path="api/member/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        //return a token
        return registrationService.register(request);
    }

    @PostMapping(path="confirm")
    public ReturnCheckedData confirm(@RequestBody ReturnCheckedData token){
        //return new ResponseEntity<>(registrationService.confirmToken(token), HttpStatus.OK);
//        String s = registrationService.confirmToken(token);
//        ReturnCheckedData rc=new ReturnCheckedData(s);
//        return rc;
        String returnS="";
        try{
            returnS=registrationService.confirmToken(token.getMessage());
        }catch (Exception e){
            returnS="token not exist or token already confirmed before.";
        }finally {
            return new ReturnCheckedData(returnS);
        }
    }
}
