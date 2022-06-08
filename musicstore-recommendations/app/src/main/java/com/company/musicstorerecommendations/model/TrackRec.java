package com.company.musicstorerecommendations.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "track_recommendation")
public class TrackRec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_recommendation_id")
    private Integer id;

    @NotNull
    @Column(name = "track_id")
    private Integer trackId;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    private boolean liked;

    public TrackRec(Integer id, Integer trackId, Integer userId, boolean liked) {
        this.id = id;
        this.trackId = trackId;
        this.userId = userId;
        this.liked = liked;
    }

    public TrackRec() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
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
        return "TrackRec{" +
                "id=" + id +
                ", trackId=" + trackId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
