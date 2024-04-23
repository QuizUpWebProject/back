package com.rcq.rcqback.service.auth;

import com.rcq.rcqback.config.WebSecurityConfig;
import com.rcq.rcqback.dto.auth.loginUserDto;
import com.rcq.rcqback.dto.auth.signUpUserDto;
import com.rcq.rcqback.entity.User;
import com.rcq.rcqback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;
    public boolean mailCheck(String mail){
        return userRepository.existsByusermail(mail);
    }
    public boolean nicknameCheck(String nickname){
        return userRepository.existsBynickname(nickname);
    }

    @Transactional
    public User saveUser(signUpUserDto signupuserdto) {
        // 새로운 사용자 객체 생성
        User user = new User();
        user.setUsermail(signupuserdto.getUsermail());
        user.setPassword(webSecurityConfig.getPasswordEncoder().encode(signupuserdto.getPassword()));
        user.setNickname(signupuserdto.getNickname());
        // 사용자 저장

        return userRepository.save(user);
    }
    public boolean checkUserPassword(String rawPassword,String hashPassword){
        return webSecurityConfig.getPasswordEncoder().matches(rawPassword,hashPassword);
    }
    public User checkUserMail(String usermail){
        return userRepository.findByusermail(usermail);
    }
    public Long getUserId(String usermail){
        return userRepository.findByusermail(usermail).getId();
    }
    public int  checkUserLogin(loginUserDto loginuserdto){
        User user=checkUserMail(loginuserdto.getUsermail());
        if(user!=null){
            if(checkUserPassword(loginuserdto.getPassword(), user.getPassword())){
                return 0;
            }
            return 1;
        }else{
            return 2;
        }
    }

}
