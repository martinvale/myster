package com.ibiscus.myster.web.report;

import com.google.common.collect.Lists;
import com.ibiscus.myster.model.company.Country;
import com.ibiscus.myster.service.data.ReferenceDataService;
import com.ibiscus.myster.service.report.MonthInterval;
import com.ibiscus.myster.service.report.ReportCriteria;
import com.ibiscus.myster.service.report.ReportCriteriaBuilder;
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
import static com.ibiscus.myster.service.report.ReportCriteriaBuilder.newReportCriteriaBuilder;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReferenceDataService referenceDataService;

    @GetMapping("/")
    public String getGeneralView(Model model, Long surveyId, String code, String name, String phase) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Dashboard general view request by: {}", authentication);
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        List<SurveyDto> surveys = surveyService.findAll();
        model.addAttribute("surveys", surveys);
        model.addAttribute("phases", getPhases());
        model.addAttribute("countries", getCountries());
        surveys.stream().findFirst()
                .ifPresent(surveyDto -> addSummaryToResponse(model,
                        newReportCriteriaBuilder(surveyDto.getId().get()).build()));
        return "dashboard/general";
    }

    @GetMapping("/general")
    public String getSurveySummaryView(Model model, Long surveyId, String code, String name, String phase) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Dashboard general view request by: {}", authentication);
        List<SurveyDto> surveys = surveyService.findAll();
        model.addAttribute("surveys", surveys);
        model.addAttribute("phases", getPhases());
        model.addAttribute("selectedSurvey", surveyId);
        model.addAttribute("selectedPhase", phase);
        model.addAttribute("code", code);
        model.addAttribute("name", name);
        MonthInterval monthInterval = MonthInterval.parse(phase);
        ReportCriteriaBuilder criteriaBuilder = newReportCriteriaBuilder(surveyId)
                .withInterval(monthInterval);
        if (!isEmpty(code)) {
            criteriaBuilder.withCode(code);
        }
        if (!isEmpty(name)) {
            criteriaBuilder.withName(name);
        }
        addSummaryToResponse(model, criteriaBuilder.build());
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

    private void addSummaryToResponse(Model model, ReportCriteria criteria) {
        model.addAttribute("selectedSurvey", criteria.getSurveyId());
        model.addAttribute("selectedPhase", criteria.getMonthInterval());
        model.addAllAttributes(getPhaseSummary(criteria));
        MonthInterval previousMonthInterval = criteria.getMonthInterval().getPreviousMonthInterval();
        model.addAttribute("previousPhase", previousMonthInterval);
        model.addAllAttributes(getTopPerformers(criteria));
    }

    private Map<String, Object> getTopPerformers(ReportCriteria criteria) {
        Map<String, Object> topPerformersAttribute = newHashMap();
        topPerformersAttribute.put("topPerformers", reportService.getTopPerformers(criteria));
        return topPerformersAttribute;
    }

    private Map<String, Object> getPhaseSummary(ReportCriteria criteria) {
        Map<String, Object> attributes = newHashMap();
        attributes.put("currentPhase", criteria.getMonthInterval());
        List<Map<String, Object>> categorySummaryResult = reportService.getCategorySummary(criteria);
        attributes.put("categorySummary", categorySummaryResult);

        Long totalSurvey = reportService.getTotalScore(criteria.getSurveyId());
        Long totalPerCategory = categorySummaryResult.stream()
                .mapToLong(value -> ((BigDecimal) value.get("average")).longValue())
                .sum();
        attributes.put("generalScore", getGeneralScore(totalSurvey, totalPerCategory));
        return attributes;
    }

    private BigDecimal getGeneralScore(Long totalSurvey, Long totalPerCategory) {
        if (totalSurvey == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(totalPerCategory).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(totalSurvey), 2, BigDecimal.ROUND_HALF_UP);
    }

    private Iterable<Country> getCountries() {
        return referenceDataService.getCountries();
    }
}
