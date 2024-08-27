package com.vn.topcv.repository;

import com.vn.topcv.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<Company, Long> {

  @Query("SELECT c FROM Company c WHERE c.user.userId = :userId")
  Optional<Company> findByUserId(@Param("userId") Long userId);
}
