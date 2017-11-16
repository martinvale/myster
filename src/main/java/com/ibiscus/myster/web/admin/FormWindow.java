package com.ibiscus.myster.web.admin;

import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class FormWindow<T> extends Window {

    private static final long serialVersionUID = 1L;

    private final Form<T> form;

    @SuppressWarnings("serial")
    protected FormWindow(String title, Form<T> form) {
        super(title, new VerticalLayout());
        this.form = form;
        setWidth(600, Unit.PIXELS);
        setModal(true);
        center();
        VerticalLayout content = new VerticalLayout();
        content.addComponent(form);

        Button save = new Button("Guardar");
        save.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if (save()) {
                    close();
                }
            }

        });

        Button cancel = new Button("Cancelar");
        cancel.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                close();
            }

        });

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponent(save);
        buttonsLayout.addComponent(cancel);
        content.addComponent(buttonsLayout);
        setContent(content);
    }

    private boolean save() {
        return onSave(form.getBean());
    }

    protected Binder<T> getBinder() {
        return form.getBinder();
    }

    protected Form<T> getForm() {
        return form;
    }

    protected VerticalLayout getMainLayout() {
        return (VerticalLayout) getContent();
    }

    public abstract boolean onSave(T surveyDto);
}
