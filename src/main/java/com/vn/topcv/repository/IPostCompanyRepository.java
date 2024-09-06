package com.vn.topcv.repository;

import com.vn.topcv.entity.Company;
import com.vn.topcv.entity.PostCompany;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostCompanyRepository extends JpaRepository<PostCompany, Long> {

  List<PostCompany> findByCompany(Company company);
}