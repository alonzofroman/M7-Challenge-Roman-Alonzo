package com.company.musicstorerecommendations.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "artist_recommendation")
public class ArtistRec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_recommendation_id")
    private Integer Id;

    @NotNull
    @Column(name = "artist_id")
    private Integer artistId;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    private boolean liked;

    public ArtistRec() {
    }

    public ArtistRec(Integer id, Integer artistId, Integer userId, boolean liked) {
        Id = id;
        this.artistId = artistId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public String toString() {
        return "ArtistRec{" +
                "Id=" + Id +
                ", artistId=" + artistId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
