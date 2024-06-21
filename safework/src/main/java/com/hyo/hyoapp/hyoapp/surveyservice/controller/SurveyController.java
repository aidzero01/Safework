package com.hyo.hyoapp.hyoapp.surveyservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.hyo.hyoapp.hyoapp.surveyservice.model.SurveyQuestion;
import com.hyo.hyoapp.hyoapp.surveyservice.model.Survey;
import com.hyo.hyoapp.hyoapp.surveyservice.service.SurveyService;

import java.util.List;

@Controller
@RequestMapping("/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @GetMapping
    public String getAllSurveys(Model model, Authentication authentication) {
        List<Survey> surveys = surveyService.getAllSurveys();
        model.addAttribute("surveys", surveys);
        model.addAttribute("username", authentication.getName());
        return "survey-list";
    }

    @GetMapping("/create")
    public String showCreateSurveyForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "survey-create";
    }

    @PostMapping("/create")
    public String createSurvey(@ModelAttribute Survey survey) {
        surveyService.createSurvey(survey);
        return "redirect:/surveys";
    }

    @GetMapping("/{surveyId}/edit")
    public String showEditSurveyForm(@PathVariable Long surveyId, Model model) {
        Survey survey = surveyService.getSurveyById(surveyId);
        model.addAttribute("survey", survey);
        return "survey-edit";
    }

    @PostMapping("/{surveyId}/edit")
    public String updateSurvey(@PathVariable Long surveyId, @ModelAttribute Survey survey) {
        survey.setIdx(surveyId);
        surveyService.updateSurvey(survey);
        return "redirect:/surveys";
    }

    @PostMapping("/{surveyId}/delete")
    public String deleteSurvey(@PathVariable Long surveyId) {
        surveyService.deleteSurvey(surveyId);
        return "redirect:/surveys";
    }

    @GetMapping("/{surveyId}/questions")
    public String showQuestions(@PathVariable Long surveyId, Model model) {
        List<SurveyQuestion> questions = surveyService.getQuestionsBySurveyId(surveyId);
        model.addAttribute("questions", questions);
        model.addAttribute("surveyId", surveyId);
        return "survey-question-list";
    }

    @GetMapping("/{surveyId}/questions/add")
    public String showAddQuestionForm(@PathVariable Long surveyId, Model model) {
        model.addAttribute("question", new SurveyQuestion());
        model.addAttribute("surveyId", surveyId);
        return "survey-add-question";
    }

    @PostMapping("/{surveyId}/questions/add")
    public String addQuestion(@PathVariable Long surveyId, @ModelAttribute SurveyQuestion question,
                              @RequestParam(required = false) List<String> radioOption,
                              @RequestParam(required = false) List<String> checkboxOption) {

        question.setSurveyId(surveyId);
        surveyService.addQuestionWithAnswers(question, radioOption, checkboxOption);
        return "redirect:/surveys/" + surveyId + "/questions";
    }

}

/*
    @Autowired
    private SurveyService surveyService;

    @PostMapping("/create")
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        return ResponseEntity.ok(surveyService.createSurvey(survey));
    }

    @GetMapping
    public ResponseEntity<List<Survey>> getAllSurveys() {
        return ResponseEntity.ok(surveyService.getAllSurveys());
    }

    @PostMapping("/{surveyId}/edit")
    public String updateSurvey(@PathVariable Long surveyId, @ModelAttribute Survey survey) {
        survey.setIdx(surveyId);
        surveyService.updateSurvey(survey);
        return "redirect:/surveys";
    }

    @GetMapping("/{surveyId}/edit")
    public String showEditSurveyForm(@PathVariable Long surveyId, Model model) {
        Survey survey = surveyService.getSurveyById(surveyId);
        model.addAttribute("survey", survey);
        return "edit-survey";
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long surveyId) {
        surveyService.deleteSurvey(surveyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{surveyId}/questions")
    public ResponseEntity<SurveyQuestion> addQuestion(@PathVariable Long surveyId, @RequestBody SurveyQuestion question) {
        question.setSurveyId(surveyId);
        return ResponseEntity.ok(surveyService.addQuestion(question));
    }

    @GetMapping("/{surveyId}/questions")
    public ResponseEntity<List<SurveyQuestion>> getQuestions(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyService.getQuestionsBySurveyId(surveyId));
    }

    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<SurveyAnswer> addAnswer(@PathVariable Long questionId, @RequestBody SurveyAnswer surveyAnswer) {
        surveyAnswer.setQuestionId(questionId);
        return ResponseEntity.ok(surveyService.addAnswer(surveyAnswer));
    }

    @GetMapping("/questions/{questionId}/answers")
    public ResponseEntity<List<SurveyAnswer>> getAnswers(@PathVariable Long questionId) {
        return ResponseEntity.ok(surveyService.getAnswersByQuestionId(questionId));
    }
    */


