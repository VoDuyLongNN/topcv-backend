package com.vn.topcv.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "topcv_job_category")
public class JobCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "type", unique = true, nullable = false)
  private String type;

  @OneToMany(mappedBy = "jobCategory", fetch = FetchType.EAGER)
  @JsonManagedReference
  List<JobSubCategory> jobSubCategories;

  @OneToMany(mappedBy = "jobCategory", fetch = FetchType.EAGER)
  @JsonManagedReference
  List<PostPersonal> postPersonals;

  @OneToMany(mappedBy = "jobCategory", fetch = FetchType.EAGER)
  @JsonManagedReference
  List<PostCompany> postCompanies;

  @OneToMany(mappedBy = "jobCategory", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<Company> companies = new LinkedHashSet<>();
}
