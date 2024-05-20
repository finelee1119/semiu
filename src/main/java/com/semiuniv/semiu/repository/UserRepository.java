package com.semiuniv.semiu.repository;

import com.semiuniv.semiu.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    @Query(value = "SELECT * FROM users where user_id = :userId", nativeQuery = true)
    Users findByUserId(@Param("userId")Integer id);
}
