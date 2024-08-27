package com.vn.topcv.repository;

import com.vn.topcv.entity.Province;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, String> {
	Optional<Province> findByCode(String code);
}