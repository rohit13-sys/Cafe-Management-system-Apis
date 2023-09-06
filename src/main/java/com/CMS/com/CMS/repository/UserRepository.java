package com.CMS.com.CMS.repository;

import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmail(String username);

    List<User> findAllByRolesRole(String role);
}
