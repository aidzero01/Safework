package com.hyo.hyoapp.hyoapp.surveyservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Getter @Setter
//public class SurveyQuestion {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long idx;
//    private Long surveyId;
//    private String type;
//    private String text;
//}
@Entity
@Getter @Setter
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long surveyId;
    private String type;
    private String text;

    @OneToMany(mappedBy = "questionId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyAnswer> answers = new ArrayList<>(); // 초기화
}
