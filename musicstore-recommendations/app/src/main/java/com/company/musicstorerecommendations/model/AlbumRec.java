package com.company.musicstorerecommendations.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "album_recommendation")
public class AlbumRec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_recommendation_id")
    private Integer id;

    @NotNull
    @Column(name = "album_id")
    private Integer albumId;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    private boolean liked;

    public AlbumRec() {
    }

    public AlbumRec(Integer id, Integer albumId, Integer userId, boolean liked) {
        this.id = id;
        this.albumId = albumId;
        this.userId = userId;
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
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
        return "AlbumRec{" +
                "id=" + id +
                ", albumId=" + albumId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
