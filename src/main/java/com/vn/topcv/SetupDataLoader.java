package com.vn.topcv;

import com.vn.topcv.entity.Role;
import com.vn.topcv.entity.enums.ERole;
import com.vn.topcv.repository.IRoleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  @Autowired
  private IRoleRepository roleRepository;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
	if (alreadySetup) {
	  return;
	}

	createRoleIfNotExist(ERole.ROLE_SYSTEM);
	createRoleIfNotExist(ERole.ROLE_ADMIN);
	createRoleIfNotExist(ERole.ROLE_PERSONAL);
	createRoleIfNotExist(ERole.ROLE_COMPANY);
  }

  @Transactional
  Role createRoleIfNotExist(ERole eRole) {
	Optional<Role> findRole = roleRepository.findByRoleName(eRole);

	Role role = new Role();

	if (findRole.isEmpty()) {
	  role.setRoleName(eRole);
	  roleRepository.save(role);
	}
	return role;
  }
}
