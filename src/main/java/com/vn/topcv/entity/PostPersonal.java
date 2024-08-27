package com.vn.topcv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vn.topcv.entity.enums.EPostStatus;
import com.vn.topcv.entity.enums.EWorkType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
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
@Table(name = "topcv_post_personal")
public class PostPersonal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "work_type")
  @Enumerated(value = EnumType.STRING)
  private EWorkType workType;

  @Lob
  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "status")
  @Enumerated(value = EnumType.STRING)
  private EPostStatus status;

  @ManyToOne
  @JoinColumn(name = "job_category_id", referencedColumnName = "id")
  @JsonBackReference
  private JobCategory jobCategory;

  @ManyToOne()
  @JoinColumn(name = "job_sub_category_id", referencedColumnName = "id")
  @JsonBackReference
  private JobSubCategory jobSubCategory;

  @ManyToOne()
  @JoinColumn(name = "personal_id", referencedColumnName = "id")
  @JsonBackReference
  private Personal personal;

  @ManyToOne()
  @JoinColumn(name = "province_code", referencedColumnName = "code")
  @JsonBackReference
  private Province province;

  @Column(name = "create_date")
  private Timestamp createDate;

  @Column(name = "update_time")
  private Timestamp updateTime;
}
