package vn.com.pharmacity.repository;

import java.util.Optional;

import org.springframework.data.repository.query.Param;

import vn.com.pharmacity.entity.User;

public interface UsersRepository extends DbRepository<User, Long> {
    Optional<User> findByUsername(@Param("userName") String userName);
}
