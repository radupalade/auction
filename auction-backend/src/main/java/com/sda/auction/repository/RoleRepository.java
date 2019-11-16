package com.sda.auction.repository;

import com.sda.auction.model.Role;
import com.sda.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRoleName(String roleName);
}
