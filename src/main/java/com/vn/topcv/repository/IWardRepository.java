package com.vn.topcv.repository;

import com.vn.topcv.entity.District;
import com.vn.topcv.entity.Ward;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWardRepository extends JpaRepository<Ward, String> {

  List<Ward> findByDistrict(District district);

  Optional<Ward> findByCode(String code);
}
