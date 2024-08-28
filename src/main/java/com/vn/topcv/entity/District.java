package com.vn.topcv.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "districts")
public class District {

  @Id
  @Size(max = 20)
  @Column(name = "code", nullable = false, length = 20)
  private String code;

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Size(max = 255)
  @Column(name = "name_en")
  private String nameEn;

  @Size(max = 255)
  @Column(name = "full_name")
  private String fullName;

  @Size(max = 255)
  @Column(name = "full_name_en")
  private String fullNameEn;

  @Size(max = 255)
  @Column(name = "code_name")
  private String codeName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "province_code")
  @JsonBackReference
  private Province province;

  @OneToMany(mappedBy = "district")
  @JsonManagedReference
  private Set<PostCompany> postCompanies = new LinkedHashSet<>();

  @OneToMany(mappedBy = "district")
  @JsonManagedReference
  private Set<Ward> wards = new LinkedHashSet<>();

}