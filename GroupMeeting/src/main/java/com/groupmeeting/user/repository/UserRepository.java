package com.groupmeeting.user.repository;

import com.groupmeeting.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select count(u.nickname) = 1 from User u where u.nickname = :nickname")
    Boolean existsByNickname(@Param("nickname") String nickname);

}
