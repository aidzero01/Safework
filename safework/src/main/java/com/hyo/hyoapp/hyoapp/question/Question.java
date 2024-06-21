package com.hyo.hyoapp.hyoapp.question;

import java.time.LocalDateTime; 
import java.util.List;

import com.hyo.hyoapp.hyoapp.answer.Answer;
import com.hyo.hyoapp.hyoapp.user.SiteUser;

import jakarta.persistence.CascadeType; 
import jakarta.persistence.Column; 
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; 

import lombok.Getter; 
import lombok.Setter; 

@Getter 
@Setter 
@Entity 
public class Question { 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id; 

    @Column(length = 200) 
    private String subject; 

    @Column(columnDefinition = "TEXT") 
    private String content; 

    private LocalDateTime createDate; 

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) 
    private List<Answer> answerList; 

    @ManyToOne
    private SiteUser author;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer views;

    public Integer getViews() {
        return this.views != null ? this.views : 0; // 만약 view가 null이면 0을 반환하도록 수정
    }

    private LocalDateTime modifyDate;
}