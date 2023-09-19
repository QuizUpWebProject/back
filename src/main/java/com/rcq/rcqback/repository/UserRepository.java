package com.rcq.rcqauth.repository;

import com.rcq.rcqauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByusermail(String usermail);
    User findBynickname(String nickname);
    boolean existsByusermail(String usermail);
    boolean existsBynickname(String nickname);

}
