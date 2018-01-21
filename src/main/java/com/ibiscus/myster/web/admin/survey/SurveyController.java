package com.ibiscus.myster.web.admin.survey;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.service.survey.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/survey")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @GetMapping("/")
    public String findAll(Model model) {
        List<SurveyDto> surveys = surveyService.findAll();
        model.addAttribute("surveys", surveys);
        return "survey/list";
    }

    @GetMapping("/{surveyId}")
    public String get(@PathVariable long surveyId, Model model) {
        SurveyDto survey = surveyService.get(surveyId);
        model.addAttribute("survey", survey);
        return "survey/detail";
    }

}
