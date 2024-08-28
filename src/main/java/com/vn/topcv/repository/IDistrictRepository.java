package com.vn.topcv.repository;

import com.vn.topcv.entity.District;
import com.vn.topcv.entity.Province;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDistrictRepository extends JpaRepository<District, String> {

  List<District> findByProvince(Province province);

  Optional<District> findByCode(String districtCode);
}