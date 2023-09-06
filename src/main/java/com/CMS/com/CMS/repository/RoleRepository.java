package com.CMS.com.CMS.repository;

import com.CMS.com.CMS.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRole(String roleName);

    @Query(value = "select r from role r where role in (?1)",nativeQuery = true)
    List<Role> findByRoleIn(List<Role> roles);
}
