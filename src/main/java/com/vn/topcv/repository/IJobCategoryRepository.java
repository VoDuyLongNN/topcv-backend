package com.vn.topcv.repository;

import com.vn.topcv.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobCategoryRepository extends JpaRepository<JobCategory, Long> {

}
