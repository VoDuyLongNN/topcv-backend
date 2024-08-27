package com.vn.topcv.repository;

import com.vn.topcv.entity.Conversation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConversationRepository extends JpaRepository<Conversation, Long> {
  Conversation findByCompany_CompanyIdAndPersonalIdAndPostId(Long companyId, Long personalId, Long postId);
}
