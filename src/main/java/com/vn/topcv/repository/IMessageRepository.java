package com.vn.topcv.repository;

import com.vn.topcv.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {

  List<Message> findByConversationId(Long conversationId);

}
