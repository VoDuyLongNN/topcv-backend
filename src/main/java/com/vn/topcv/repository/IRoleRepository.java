package com.vn.topcv.repository;

import com.vn.topcv.entity.Role;
import com.vn.topcv.entity.enums.ERole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(ERole eRole);
}
