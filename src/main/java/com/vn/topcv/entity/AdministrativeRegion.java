package com.vn.topcv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "administrative_regions")
public class AdministrativeRegion {

  @Id
  @Column(name = "id", nullable = false)
  private Integer id;

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Size(max = 255)
  @NotNull
  @Column(name = "name_en", nullable = false)
  private String nameEn;

  @Size(max = 255)
  @Column(name = "code_name")
  private String codeName;

  @Size(max = 255)
  @Column(name = "code_name_en")
  private String codeNameEn;

  @OneToMany(mappedBy = "administrativeRegion")
  private Set<Province> provinces = new LinkedHashSet<>();

}