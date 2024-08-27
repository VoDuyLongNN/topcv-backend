package com.vn.topcv.repository;

import com.vn.topcv.entity.Personal;
import com.vn.topcv.entity.PostPersonal;
import com.vn.topcv.entity.enums.EPostStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostPersonalRepository extends JpaRepository<PostPersonal, Long>,
    JpaSpecificationExecutor<PostPersonal> {
  Page<PostPersonal> findAllByPersonal(Personal personal, Pageable pageable);

  List<PostPersonal> getPostPersonalByStatus(EPostStatus status);

  @Query("SELECT COUNT(c) FROM Company c JOIN c.postPersonals p WHERE p.id = :postId")
  Long countCompanySavedPost(@Param("postId") Long postId);
}
