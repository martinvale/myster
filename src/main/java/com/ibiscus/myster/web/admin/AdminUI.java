package com.ibiscus.myster.web.admin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.EnableVaadinNavigation;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.UI;

@SpringUI(path = "")
@SpringViewDisplay
@Theme("myster")
public class AdminUI extends UI {

    private static final long serialVersionUID = 1L;

    @EnableVaadin
    @EnableVaadinNavigation
    public static class MyConfiguration {
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Myster");
    }

}
