package com.vn.topcv.repository;

import com.vn.topcv.entity.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Long> {

  @Query("SELECT t FROM Token t WHERE t.user.userId = :id")
  Optional<Token> findByUserId(@Param("id") Long id);

  Optional<Token> findTokenByTokenString(String token);
}
