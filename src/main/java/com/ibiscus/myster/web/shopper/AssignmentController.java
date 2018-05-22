package com.ibiscus.myster.web.shopper;

import com.ibiscus.myster.model.survey.SurveyTask;
import com.ibiscus.myster.model.survey.TaskDescription;
import com.ibiscus.myster.service.assignment.AssignmentService;
import com.ibiscus.myster.service.assignment.CompletedSurvey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopper/assignments")
public class AssignmentController {

    private final Logger logger = LoggerFactory.getLogger(AssignmentController.class);

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/")
    public String get(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Current authentication on context: {}", authentication);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        List<TaskDescription> tasks = assignmentService.findByUsername(principal.getUsername());
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
        return "redirect:.";
    }

    @PutMapping("/{assignmentId}/sent")
    @ResponseBody
    public Map<String, Boolean> send(@PathVariable long assignmentId) {
        assignmentService.close(assignmentId);
        return Collections.singletonMap("success", true);
    }
}
