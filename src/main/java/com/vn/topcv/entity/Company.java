package com.vn.topcv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
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
@Table(name = "topcv_company")
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_id")
  private Long companyId;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "avt")
  private String avt;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "industry")
  @JsonBackReference
  private JobCategory jobCategory;

  @Column(name = "location")
  private String location;

  @Column(name = "establishment")
  private LocalDate establishment;

  @Column(name = "website")
  private String website;

  @Column(name = "description")
  private String description;

  @Column(name = "create_date")
  private Timestamp createDate;

  @Column(name = "update_time")
  private Timestamp updateTime;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "topcv_company_save_post",
      joinColumns = @JoinColumn(name = "company_id"),
      inverseJoinColumns = @JoinColumn(name = "post_id")
  )
  private Set<PostPersonal> postPersonals = new HashSet<>();
}
