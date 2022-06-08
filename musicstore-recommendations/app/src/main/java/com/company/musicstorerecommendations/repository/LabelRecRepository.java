package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.LabelRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRecRepository extends JpaRepository<LabelRec, Integer> {
}
