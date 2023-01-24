package com.elsoft.assessment.jurlshortener.repository;

import com.elsoft.assessment.jurlshortener.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long>, JpaSpecificationExecutor<UrlEntity> {
}