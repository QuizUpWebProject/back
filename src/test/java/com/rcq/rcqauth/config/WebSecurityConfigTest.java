package com.rcq.rcqauth.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WebSecurityConfigTest {
    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void encodeTest() {
        // given
        String rawPW = "1234";

        // when
        String encodePW = webSecurityConfig.getPasswordEncoder().encode(rawPW);

        // then
        assertThat(rawPW).isNotEqualTo(encodePW);
    }



}