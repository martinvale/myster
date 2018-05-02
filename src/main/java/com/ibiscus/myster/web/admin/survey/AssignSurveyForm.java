package com.ibiscus.myster.web.admin.survey;

import com.google.common.collect.Lists;
import com.ibiscus.myster.model.company.Location;
import com.ibiscus.myster.model.shopper.Shopper;
import com.ibiscus.myster.repository.shopper.ShopperRepository;
import com.ibiscus.myster.web.admin.Form;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import java.util.ArrayList;
import java.util.List;

public class AssignSurveyForm extends Form<SurveyAssignment> {

    protected AssignSurveyForm(Binder<SurveyAssignment> binder, String surveyName, ShopperRepository shopperRepository) {
        super(binder);

        Label surveyNameLabel = new Label("Cliente: " + surveyName);
        addComponent(surveyNameLabel);

        ComboBox<Shopper> shopperSelector = new ComboBox<>("Shopper");
        List<Shopper> shoppers = Lists.newArrayList(shopperRepository.findAll());
        shopperSelector.setDataProvider(new ListDataProvider<Shopper>(shoppers));
        shopperSelector.setItemCaptionGenerator(item -> item.toString());
        addComponent(shopperSelector);

        ComboBox<Location> pointOfSaleSelector = new ComboBox<>("Punto de venta");
        List<Location> pointOfSales = new ArrayList<>();
        pointOfSales.add(new Location(1, "Corrientes 345, Buenos Aires"));
        pointOfSaleSelector.setDataProvider(new ListDataProvider<Location>(pointOfSales));
        pointOfSaleSelector.setItemCaptionGenerator(item -> item.getAddress());
        addComponent(pointOfSaleSelector);

        binder.forField(shopperSelector).bind("shopper");
        binder.forField(pointOfSaleSelector).bind("location");
    }

}
