package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import vn.com.pharmacity.entity.User;

/**
 * Define user identity as a constant
 * @author Bac
 * @date 2025/5/20
 */
@Repository
public interface AdminAccountRepository extends DbRepository<User, Long> {

    List<User> selectAllAccount();

}
