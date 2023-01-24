package com.elsoft.assessment.jurlshortener.repository;

import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    @Query("SELECT u FROM UrlEntity u WHERE " +
            "(?2 is null or u.createdDate = ?2) or " +
            "(?3 is null or u.expiryDate = ?3) or " +
            "(LOWER(u.url) LIKE LOWER(CONCAT('%', ?1 ,'%') )) or " +
            "( DATE_FORMAT(u.createdDate, '%Y-%m-%d %T.%f') LIKE LOWER(CONCAT('%',?1,'%') )) or " +
            "(DATE_FORMAT(u.expiryDate, '%Y-%m-%d %T.%f') LIKE LOWER(CONCAT('%',?1,'%') )) " +
            "order by createdDate desc")
     List<UrlEntity> findAllByQuery(String search , LocalDateTime createdDate, LocalDateTime expDate);

     UrlEntity findByUrl( String url);

     boolean existsByUrl(String url );
}