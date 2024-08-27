package com.vn.topcv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "administrative_units")
public class AdministrativeUnit {

  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 255)
  @Column(name = "full_name")
  private String fullName;

  @Size(max = 255)
  @Column(name = "full_name_en")
  private String fullNameEn;

  @Size(max = 255)
  @Column(name = "short_name")
  private String shortName;

  @Size(max = 255)
  @Column(name = "short_name_en")
  private String shortNameEn;

  @Size(max = 255)
  @Column(name = "code_name")
  private String codeName;

  @Size(max = 255)
  @Column(name = "code_name_en")
  private String codeNameEn;

  @OneToMany(mappedBy = "administrativeUnit")
  private Set<District> districts = new LinkedHashSet<>();

  @OneToMany(mappedBy = "administrativeUnit")
  private Set<Province> provinces = new LinkedHashSet<>();

  @OneToMany(mappedBy = "administrativeUnit")
  private Set<Ward> wards = new LinkedHashSet<>();

}