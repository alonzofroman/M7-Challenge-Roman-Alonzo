package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.AlbumRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRecRepository extends JpaRepository<AlbumRec, Integer> {
}
