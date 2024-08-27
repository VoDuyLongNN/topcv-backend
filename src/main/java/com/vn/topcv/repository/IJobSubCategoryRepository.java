package com.vn.topcv.repository;

import com.vn.topcv.entity.JobCategory;
import com.vn.topcv.entity.JobSubCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobSubCategoryRepository extends JpaRepository<JobSubCategory, Long> {
	List<JobSubCategory> findAllByJobCategory(JobCategory jobCategory);
}
