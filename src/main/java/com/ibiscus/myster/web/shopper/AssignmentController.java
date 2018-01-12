package com.ibiscus.myster.web.shopper;

import com.ibiscus.myster.model.survey.SurveyDto;
import com.ibiscus.myster.service.assignment.AssignmentService;
import com.ibiscus.myster.service.assignment.CompletedSurvey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/{assignmentId}")
    public String fill(@PathVariable long assignmentId, Model model) {
        SurveyDto surveyDto = assignmentService.getSurvey(assignmentId);
        model.addAttribute("survey", surveyDto);
        if (surveyDto.isComplete()) {
            return "completed";
        } else {
            return "fill";
        }
    }

    @PostMapping("/{assignmentId}")
    public String save(@PathVariable long assignmentId, @ModelAttribute CompletedSurvey completedSurvey, Model model) {
        assignmentService.save(completedSurvey);
        SurveyDto surveyDto = assignmentService.getSurvey(assignmentId);
        model.addAttribute("survey", surveyDto);
        if (surveyDto.isComplete()) {
            return "completed";
        } else {
            return "fill";
        }
    }

}
