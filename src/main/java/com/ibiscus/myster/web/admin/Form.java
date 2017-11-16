package com.ibiscus.myster.web.admin;

import com.vaadin.data.Binder;
import com.vaadin.ui.FormLayout;

public abstract class Form<T> extends FormLayout {

    private static final long serialVersionUID = 1L;

    private final Binder<T> binder;

    protected Form(Binder<T> binder) {
        this.binder = binder;
    }

    public Binder<T> getBinder() {
        return binder;
    }

    public T getBean() {
        return binder.getBean();
    }

    public void setBean(T bean) {
        binder.setBean(bean);
    }
}
