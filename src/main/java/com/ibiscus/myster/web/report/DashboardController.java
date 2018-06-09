package com.ibiscus.myster.web.report;

import com.google.common.collect.Lists;
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
    public String getGeneralView(Model model, Long surveyId, String phase) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Dashboard general view request by: {}", authentication);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        List<SurveyDto> surveys = surveyService.findAll();
        model.addAttribute("surveys", surveys);
        model.addAttribute("phases", getPhases());
        surveys.stream().findFirst()
                .ifPresent(surveyDto -> addSummaryToResponse(model, surveyDto, currentMonthInterval()));
        return "dashboard/general";
    }

    @GetMapping("/general")
    public String getSurveySummaryView(Model model, Long surveyId, String phase) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Dashboard general view request by: {}", authentication);
        List<SurveyDto> surveys = surveyService.findAll();
        model.addAttribute("surveys", surveys);
        model.addAttribute("phases", getPhases());
        SurveyDto surveyDto = surveyService.get(surveyId);
        MonthInterval monthInterval = MonthInterval.parse(phase);
        addSummaryToResponse(model, surveyDto, monthInterval);
        return "dashboard/general";
    }

    @GetMapping("/survey")
    public String getSurveyDetail(Model model, Long surveyId, String phase) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Dashboard survey {} detail view for phase {} request by: {}", surveyId, phase, authentication);
        List<SurveyDto> surveys = surveyService.findAll();
        model.addAttribute("surveys", surveys);
        model.addAttribute("phases", getPhases());
        MonthInterval monthInterval = MonthInterval.parse(phase);
        model.addAttribute("categoriesSummary", reportService.getAnswerSummary(surveyId, monthInterval));
        /*SurveyDto surveyDto = surveyService.get(surveyId);
        MonthInterval monthInterval = MonthInterval.parse(phase);
        addSummaryToResponse(model, surveyDto, monthInterval);*/
        return "dashboard/survey";
    }

    private List<String> getPhases() {
        return Lists.newArrayList("05-2018", "06-2018", "07-2018", "08-2018");
    }

    private void addSummaryToResponse(Model model, SurveyDto surveyDto, MonthInterval monthInterval) {
        model.addAttribute("selectedSurvey", surveyDto.getId().get());
        model.addAttribute("selectedPhase", monthInterval.toString());
        model.addAllAttributes(getPhaseSummary(surveyDto.getId().get(), monthInterval));
        MonthInterval previousMonthInterval = monthInterval.getPreviousMonthInterval();
        model.addAttribute("previousPhase", previousMonthInterval);
        model.addAllAttributes(getTopPerformers(surveyDto.getId().get(), monthInterval));
    }

    private Map<String, Object> getTopPerformers(long surveyId, MonthInterval monthInterval) {
        Map<String, Object> topPerformersAttribute = newHashMap();
        topPerformersAttribute.put("topPerformers", reportService.getTopPerformers(surveyId, monthInterval));
        return topPerformersAttribute;
    }

    private Map<String, Object> getPhaseSummary(long surveyId, MonthInterval monthInterval) {
        Map<String, Object> attributes = newHashMap();
        attributes.put("currentPhase", monthInterval);
        List<Map<String, Object>> categorySummaryResult = reportService.getCategorySummary(surveyId,
                monthInterval);
        attributes.put("categorySummary", categorySummaryResult);

        Integer totalSurvey = reportService.getTotalScore(surveyId);
        Integer totalPerCategory = categorySummaryResult.stream()
                .mapToInt(value -> ((BigDecimal) value.get("average")).intValue())
                .sum();
        attributes.put("generalScore", getGeneralScore(totalSurvey, totalPerCategory));
        return attributes;
    }

    private BigDecimal getGeneralScore(Integer totalSurvey, Integer totalPerCategory) {
        if (totalSurvey == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalPerCategory).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalSurvey), 2, BigDecimal.ROUND_HALF_UP);
    }
}
