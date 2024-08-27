package com.vn.topcv.repository;

import com.vn.topcv.entity.AdministrativeRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdministrativeRegionRepository extends
	JpaRepository<AdministrativeRegion, Integer> {

}