package com.company.musicstorerecommendations.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "label_recommendation")
public class LabelRec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "label_recommendation_id")
    private Integer id;

    @NotNull
    @Column(name = "label_id")
    private Integer labelId;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    private boolean liked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
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

    public LabelRec(Integer id, Integer labelId, Integer userId, boolean liked) {
        this.id = id;
        this.labelId = labelId;
        this.userId = userId;
        this.liked = liked;
    }

    public LabelRec() {
    }

    @Override
    public String toString() {
        return "LabelRec{" +
                "id=" + id +
                ", labelId=" + labelId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
