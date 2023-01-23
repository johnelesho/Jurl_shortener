package com.elsoft.assessment.jurlshortener.repository;

import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlEntityRepository extends JpaRepository<UrlEntity, Long> {
}