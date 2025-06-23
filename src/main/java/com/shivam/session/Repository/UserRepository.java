package com.shivam.session.Repository;

import com.shivam.session.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsernameAndPassword(String username, String password);

    Optional<User> findByLoginName(String loginName);
}
