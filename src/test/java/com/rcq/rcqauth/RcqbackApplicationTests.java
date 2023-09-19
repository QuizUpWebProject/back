package com.rcq.rcqauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
class RcqbackApplicationTests {

	@Test
	@Transactional
	@DisplayName("회원가입 테스트")
	void contextLoads() {
	}

}
