package com.rcq.rcqauth.service;

import com.rcq.rcqauth.dto.loginUserDto;
import com.rcq.rcqauth.dto.signUpUserDto;
import com.rcq.rcqauth.entity.User;
import com.rcq.rcqauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  AuthService authService;

    @BeforeEach
    @Transactional
    public void beforeEach() {
        signUpUserDto signUpUserDto = new signUpUserDto();
        signUpUserDto.setUsermail("test@gmail.com");
        signUpUserDto.setPassword("1234");
        signUpUserDto.setNickname("testuser");
        authService.saveUser(signUpUserDto);
    }

    @Test
    @DisplayName("메일 중복체크")
    @Transactional
    void emailDuplicateCheck() {

        assertThat(authService.mailCheck("test@gmail.com")).isEqualTo(true);
        assertThat(authService.mailCheck("sdsd@naver.com")).isEqualTo(false);
    }
    @Test
    @DisplayName("닉네임 중복체크")
    @Transactional
    void nicknameDuplicateCheck() {
        assertThat(authService.nicknameCheck("testuser")).isEqualTo(true);
        assertThat(authService.nicknameCheck("테스트2")).isEqualTo(false);
    }
    @Test
    @DisplayName("로그인시 존재하는 계정 mail check 테스트")
    @Transactional
    void checkUserMail(){
        assertThat(authService.checkUserMail("test@gmail.com")).isNotNull();
        assertThat(authService.mailCheck("sdsd@naver.com")).isEqualTo(false);
    }

    @Test
    @DisplayName("로그인 password check 테스트")
    @Transactional
    void checkUserPassword(){

        User user=userRepository.findByusermail("test@gmail.com");

        assertThat(authService.checkUserPassword("1234",user.getPassword())).isEqualTo(true);
    }

    @Test
    @DisplayName("로그인 check 테스트")
    @Transactional
    void checkUserLogin(){
        loginUserDto loginuserdto=new loginUserDto();
        loginuserdto.setUsermail("test@gmail.com");
        loginuserdto.setPassword("1234");
        assertThat(authService.checkUserLogin(loginuserdto)).isEqualTo(0);
    }

}