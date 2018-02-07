package com.ibiscus.myster.web.shopper;

import com.ibiscus.myster.model.survey.SurveyTask;
import com.ibiscus.myster.model.survey.TaskDescription;
import com.ibiscus.myster.service.assignment.AssignmentDescriptor;
import com.ibiscus.myster.service.assignment.AssignmentService;
import com.ibiscus.myster.service.assignment.CompletedSurvey;
import com.ibiscus.myster.service.security.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shopper/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/")
    public String get(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserInfo principal = (UserInfo) authentication.getPrincipal();
        List<TaskDescription> tasks = assignmentService.findByUserId(principal.getUserId());
        model.addAttribute("tasks", tasks);
        return "assignment/list";
    }

    @GetMapping("/{assignmentId}")
    public String fill(@PathVariable long assignmentId, Model model) {
        SurveyTask surveyTask = assignmentService.getSurveyTask(assignmentId);
        model.addAttribute("survey", surveyTask);
        if (surveyTask.isComplete()) {
            return "completed";
        } else {
            return "fill";
        }
    }

    @PostMapping("/{assignmentId}")
    public String save(@PathVariable long assignmentId, @ModelAttribute CompletedSurvey completedSurvey, Model model) {
        assignmentService.save(assignmentId, completedSurvey);
        SurveyTask surveyTask = assignmentService.getSurveyTask(assignmentId);
        model.addAttribute("survey", surveyTask);
        if (surveyTask.isComplete()) {
            return "completed";
        } else {
            return "redirect:.";
        }
    }

}
