package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRecRepository extends JpaRepository<TrackRec, Integer> {
}
