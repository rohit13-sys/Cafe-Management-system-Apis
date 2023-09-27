package com.CMS.com.CMS.repository;

import com.CMS.com.CMS.pojo.Role;
import com.CMS.com.CMS.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    public User findByUsername(String username);

    List<User> findAllByRolesRole(String role);
}
