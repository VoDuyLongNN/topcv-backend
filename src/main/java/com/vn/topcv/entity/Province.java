package com.vn.topcv.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "provinces")
public class Province {

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
  @NotNull
  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Size(max = 255)
  @Column(name = "full_name_en")
  private String fullNameEn;

  @Size(max = 255)
  @Column(name = "code_name")
  private String codeName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "administrative_region_id")
  private AdministrativeRegion administrativeRegion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "administrative_unit_id")
  private AdministrativeUnit administrativeUnit;

}