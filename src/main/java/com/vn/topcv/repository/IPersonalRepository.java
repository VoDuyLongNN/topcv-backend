package com.vn.topcv.repository;

import com.vn.topcv.entity.Personal;
import com.vn.topcv.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonalRepository extends JpaRepository<Personal, Long> {

  @Query("SELECT p FROM Personal p WHERE p.user.userId = :userId")
  Optional<Personal> findByUserId(@Param("userId") Long userId);

}
