package com.vn.topcv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vn.topcv.entity.enums.EPostStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "topcv_post_company")
public class PostCompany {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "expired")
  private LocalDate expired;

  @Column(name = "details")
  private String details;

  @Column(name = "quantity")
  private InternalError quantity;

  @Column(name = "requirements")
  private String requirements;

  @Column(name = "benefit")
  private String benefit;

  @Column(name = "location")
  private String location;

  @Column(name = "work_time")
  private String workTime;

  @Column(name = "status")
  @Enumerated(value = EnumType.STRING)
  private EPostStatus status;

  @ManyToOne
  @JoinColumn(name = "salary_id", referencedColumnName = "id")
  @JsonBackReference
  private Salary salary;

  @ManyToOne
  @JoinColumn(name = "experience_id", referencedColumnName = "id")
  @JsonBackReference
  private Experience experience;

  @ManyToOne
  @JoinColumn(name = "job_category_id", referencedColumnName = "id")
  @JsonBackReference
  private JobCategory jobCategory;

  @ManyToOne()
  @JoinColumn(name = "job_sub_category_id", referencedColumnName = "id")
  @JsonBackReference
  private JobSubCategory jobSubCategory;

  @ManyToOne()
  @JoinColumn(name = "company_id", referencedColumnName = "company_id")
  @JsonBackReference
  private Company company;

  @ManyToOne()
  @JoinColumn(name = "province_code", referencedColumnName = "code")
  @JsonBackReference
  private Province province;

  @ManyToOne()
  @JoinColumn(name = "district_code", referencedColumnName = "code")
  @JsonBackReference
  private District district;

  @ManyToOne()
  @JoinColumn(name = "ward_code", referencedColumnName = "code")
  @JsonBackReference
  private Ward ward;

  @Column(name = "create_date")
  private Timestamp createDate;

  @Column(name = "update_time")
  private Timestamp updateTime;
}
