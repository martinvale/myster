package com.ibiscus.myster.web.admin.survey.items.choice;

import com.ibiscus.myster.model.survey.item.Choice;
import com.ibiscus.myster.web.admin.FormWindow;
import com.vaadin.data.Binder;
import com.vaadin.ui.UI;

public abstract class ChoiceFormWindow extends FormWindow<ChoiceDto> {

    private static final long serialVersionUID = 1L;

    public ChoiceFormWindow() {
        super("Datos del item", new ChoiceForm(
                new Binder<ChoiceDto>(ChoiceDto.class)));
    }

    public void bind(Choice choice) {
        getForm().setBean(new ChoiceDto(choice));
        UI.getCurrent().addWindow(this);
    }

    public void bindNew(long itemOptionId) {
        getForm().setBean(new ChoiceDto(itemOptionId));
        UI.getCurrent().addWindow(this);
    }

}
