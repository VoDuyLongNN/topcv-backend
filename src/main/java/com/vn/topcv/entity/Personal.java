package com.vn.topcv.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "topcv_personal")
public class Personal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "gender")
  private boolean gender;

  @Column(name = "birth_day")
  private LocalDate birthDay;

  @Column(name = "resume")
  private String resume;

  @Column(name = "education")
  private String education;

  @Column(name = "Skill")
  private String Skill;

  @Column(name = "address")
  private String address;

  @Column(name = "location")
  private String location;

  @Column(name = "phone")
  private String phone;

  @Column(name = "description")
  private String description;

  @Column(name = "create_date")
  private Timestamp createDate;

  @Column(name = "update_time")
  private Timestamp updateTime;

  @OneToMany(mappedBy = "personal", fetch = FetchType.EAGER)
  @JsonManagedReference
  List<PostPersonal> postPersonals;

}
