package com.ibiscus.myster.web.report;

import com.ibiscus.myster.model.survey.Survey;
import com.ibiscus.myster.model.survey.TaskDescription;
import com.ibiscus.myster.service.report.MonthInterval;
import com.ibiscus.myster.service.report.ReportService;
import com.ibiscus.myster.service.survey.SurveyService;
import com.ibiscus.myster.web.admin.survey.SurveyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.ibiscus.myster.service.report.MonthInterval.currentMonthInterval;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/")
    public String getGeneralView(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Dashboard general view request by: {}", authentication);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        List<SurveyDto> surveys = surveyService.findAll();
        model.addAttribute("surveys", surveys);
        surveys.stream().findFirst()
                .ifPresent(surveyDto -> addSummaryToResponse(model, surveyDto));
        return "dashboard/general";
    }

    private void addSummaryToResponse(Model model, SurveyDto surveyDto) {
        model.addAttribute("selectedSurvey", surveyDto);
        model.addAllAttributes(getCurrentPhaseSummary(surveyDto.getId().get()));
        MonthInterval previousMonthInterval = currentMonthInterval().getPreviousMonthInterval();
        model.addAttribute("previousPhase", previousMonthInterval);
        model.addAllAttributes(getTopPerformers(surveyDto.getId().get()));
    }

    private Map<String, Object> getTopPerformers(long surveyId) {
        Map<String, Object> topPerformersAttribute = newHashMap();
        MonthInterval currentMonthInterval = currentMonthInterval();
        topPerformersAttribute.put("topPerformers", reportService.getTopPerformers(surveyId, currentMonthInterval));
        return topPerformersAttribute;
    }

    private Map<String, Object> getCurrentPhaseSummary(long surveyId) {
        Map<String, Object> attributes = newHashMap();
        MonthInterval currentMonthInterval = currentMonthInterval();
        attributes.put("currentPhase", currentMonthInterval);
        List<Map<String, Object>> categorySummaryResult = reportService.getCategorySummary(surveyId,
                currentMonthInterval);
        attributes.put("categorySummary", categorySummaryResult);

        Integer totalSurvey = reportService.getTotalScore(surveyId);
        Integer totalPerCategory = categorySummaryResult.stream()
                .mapToInt(value -> ((BigDecimal) value.get("average")).intValue())
                .sum();
        attributes.put("generalScore", BigDecimal.valueOf(totalPerCategory).multiply(BigDecimal.valueOf(100))
            .divide(BigDecimal.valueOf(totalSurvey), 2, BigDecimal.ROUND_HALF_UP));
        return attributes;
    }

}
