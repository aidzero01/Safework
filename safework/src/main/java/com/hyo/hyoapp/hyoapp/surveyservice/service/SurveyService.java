package com.hyo.hyoapp.hyoapp.surveyservice.service;

import com.hyo.hyoapp.hyoapp.surveyservice.model.SurveyAnswer;
import com.hyo.hyoapp.hyoapp.surveyservice.model.SurveyQuestion;
import com.hyo.hyoapp.hyoapp.surveyservice.model.Survey;
import com.hyo.hyoapp.hyoapp.surveyservice.repository.SurveyAnswerRepository;
import com.hyo.hyoapp.hyoapp.surveyservice.repository.SurveyQuestionRepository;
import com.hyo.hyoapp.hyoapp.surveyservice.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyQuestionRepository surveyQuestionRepository;

    @Autowired
    private SurveyAnswerRepository answerRepository;

    public Survey createSurvey(Survey survey) {
        survey.setCreatedAt(LocalDateTime.now());
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurveyById(Long surveyId) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        return survey.orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    public Survey updateSurvey(Survey survey) {
        Optional<Survey> existingSurvey = surveyRepository.findById(survey.getIdx());
        if (existingSurvey.isPresent()) {
            Survey updatedSurvey = existingSurvey.get();
            updatedSurvey.setTitle(survey.getTitle());
            updatedSurvey.setDescription(survey.getDescription());
            updatedSurvey.setCreatedAt(LocalDateTime.now());
            // 다른 필드도 필요에 따라 업데이트
            return surveyRepository.save(updatedSurvey);
        } else {
            throw new RuntimeException("Survey not found");
        }
    }

    public void deleteSurvey(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    public SurveyQuestion addQuestion(SurveyQuestion question) {
        return surveyQuestionRepository.save(question);
    }

    public SurveyQuestion addQuestionWithAnswers(SurveyQuestion question, List<String> radioOptions, List<String> checkboxOptions) {
        if (question.getAnswers() == null) {
            question.setAnswers(new ArrayList<>()); // 초기화
        }

        SurveyQuestion savedQuestion = surveyQuestionRepository.save(question);

        if ("RADIO".equals(question.getType()) && radioOptions != null) {
            for (String option : radioOptions) {
                if (!option.isEmpty()) {
                    SurveyAnswer answer = new SurveyAnswer();
                    answer.setQuestionId(savedQuestion.getIdx());
                    answer.setText(option);
                    savedQuestion.getAnswers().add(answer);
                    answerRepository.save(answer);
                }
            }
        } else if ("CHECKBOX".equals(question.getType()) && checkboxOptions != null) {
            for (String option : checkboxOptions) {
                if (!option.isEmpty()) {
                    SurveyAnswer answer = new SurveyAnswer();
                    answer.setQuestionId(savedQuestion.getIdx());
                    answer.setText(option);
                    savedQuestion.getAnswers().add(answer);
                    answerRepository.save(answer);
                }
            }
        }

        return savedQuestion;
    }

    public List<SurveyQuestion> getQuestionsBySurveyId(Long surveyId) {
        return surveyQuestionRepository.findBySurveyId(surveyId);
    }
}

    /*
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private SurveyQuestionRepository questionRepository;
    @Autowired
    private SurveyAnswerRepository answerRepository;

    public Survey createSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Survey getSurveyById(Long surveyId) {
        Optional<Survey> survey = surveyRepository.findById(surveyId);
        return survey.orElseThrow(() -> new RuntimeException("Survey not found"));
    }
    public Survey updateSurvey(Survey survey) {
        Optional<Survey> existingSurvey = surveyRepository.findById(survey.getIdx());
        if (existingSurvey.isPresent()) {
            Survey updatedSurvey = existingSurvey.get();
            updatedSurvey.setTitle(survey.getTitle());
            updatedSurvey.setDescription(survey.getDescription());
            // 다른 필드도 필요에 따라 업데이트
            return surveyRepository.save(updatedSurvey);
        } else {
            throw new RuntimeException("Survey not found");
        }
    }
    public void deleteSurvey(Long surveyId) {
        surveyRepository.deleteById(surveyId);
    }

    public SurveyQuestion addQuestion(SurveyQuestion question) {
        return questionRepository.save(question);
    }

    public List<SurveyQuestion> getQuestionsBySurveyId(Long surveyId) {
        return questionRepository.findBySurveyId(surveyId);
    }

    public List<SurveyQuestion> getAllQuestions() {
        return questionRepository.findAll();
    }

    public SurveyAnswer addAnswer(SurveyAnswer surveyAnswer) {
        return answerRepository.save(surveyAnswer);
    }

    public List<SurveyAnswer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }
     */

