package com.hyo.hyoapp.hyoapp.surveyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hyo.hyoapp.hyoapp.surveyservice.model.SurveyAnswer;

import java.util.List;

@Repository()
public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
    List<SurveyAnswer> findByQuestionId(Long questionId);
}
