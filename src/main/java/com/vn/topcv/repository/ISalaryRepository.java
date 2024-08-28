package com.vn.topcv.repository;

import com.vn.topcv.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISalaryRepository extends JpaRepository<Salary, Integer> {

}