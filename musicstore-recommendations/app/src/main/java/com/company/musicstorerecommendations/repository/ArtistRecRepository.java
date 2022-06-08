package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.ArtistRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRecRepository extends JpaRepository<ArtistRec, Integer> {
}
