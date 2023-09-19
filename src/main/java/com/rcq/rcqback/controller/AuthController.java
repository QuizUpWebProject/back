package com.rcq.rcqback.controller;

import com.rcq.rcqback.dto.auth.loginUserDto;
import com.rcq.rcqback.dto.auth.signUpUserDto;
import com.rcq.rcqback.service.auth.AuthService;
import com.rcq.rcqback.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {


    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/user/api/signup/mailcheck")
    public ResponseEntity<ApiResponse> emailDuplicateCheck(@RequestParam("mail") String mail){
        ApiResponse apiResponse=new ApiResponse();
        if(!authService.mailCheck(mail)){
            apiResponse.setSuccessResonse();
        }else{
            apiResponse.setFAILResonse("중복이 존재합니다.");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @GetMapping("/user/api/signup/nicknamecheck")
    public ResponseEntity<ApiResponse> nicknameDuplicateCheck(@RequestParam("nickname") String nickname){
        ApiResponse apiResponse=new ApiResponse();
        if(!authService.nicknameCheck(nickname)){
            apiResponse.setSuccessResonse();
        }else{
            apiResponse.setFAILResonse("중복이 존재합니다.");

        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping(value = "/user/api/signup" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> signUpUser(@RequestBody signUpUserDto signupuserdto) {

        ApiResponse apiResponse=new ApiResponse();
        authService.saveUser(signupuserdto);
        apiResponse.setSuccessResonse();
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }

    @PostMapping(value = "/user/api/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> loginUser(@RequestBody loginUserDto loginUserDto){
        ApiResponse apiResponse=new ApiResponse();
        int userLoginResult=authService.checkUserLogin(loginUserDto);
        if(userLoginResult==0){
            apiResponse.setSuccessResonse();
        }
        else if(userLoginResult==1){
            apiResponse.setFAILResonse("비밀번호가 틀립니다.");
        }
        else{
            apiResponse.setFAILResonse("존재하지 않는 ID");
        }
        return new ResponseEntity<>(apiResponse, apiResponse.getHttpStatus());
    }


}
