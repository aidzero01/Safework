package com.hyo.hyoapp.hyoapp.surveyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hyo.hyoapp.hyoapp.surveyservice.model.SurveyQuestion;

import java.util.List;

@Repository()
public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {
    List<SurveyQuestion> findBySurveyId(Long surveyId);
}
