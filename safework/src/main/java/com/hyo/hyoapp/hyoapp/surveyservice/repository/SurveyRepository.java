package com.hyo.hyoapp.hyoapp.surveyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hyo.hyoapp.hyoapp.surveyservice.model.Survey;


@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
