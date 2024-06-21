package com.hyo.hyoapp.hyoapp.surveyservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

//@Entity
//@Getter @Setter
//public class Survey {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long idx;
//    private String title;
//    private String description;
//    private LocalDateTime createdAt;
//
//    // 오류 해결
//    // 오류 : Survey 엔티티를 삭제하려고 할 때 관련된 SurveyQuestion 엔티티가 존재하여
//    // 데이터 무결성 제약 조건을 위반하는 문제를 나타남
//    // 해결 -> @OneToMany(mappedBy = "surveyId", cascade = CascadeType.REMOVE)
//    @OneToMany(mappedBy = "surveyId", cascade = CascadeType.REMOVE)
//    private List<SurveyQuestion> surveyQuestions;
//}

@Entity
@Getter @Setter
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String description;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "surveyId", cascade = CascadeType.REMOVE)
    private List<SurveyQuestion> surveyQuestions;
}

