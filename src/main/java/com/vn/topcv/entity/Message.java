package com.vn.topcv.entity;

import com.vn.topcv.entity.enums.ESenderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "topcv_message")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "conversation_id")
  private Conversation conversation;

  @Column(name = "sender_type")
  @Enumerated(EnumType.STRING)
  private ESenderType senderType;

  @Column(name = "sender_id")
  private Long senderId;

  @Column(name = "content")
  private String content;

  @Column(name = "create_date")
  private Timestamp createDate;
}
