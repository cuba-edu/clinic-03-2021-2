package com.company.clinic.web.screens.usedconsumables;

import com.company.clinic.entity.Consumable;
import com.company.clinic.service.ConsumablesService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.VBoxLayout;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.widgets.CubaVerticalActionsLayout;
import com.vaadin.ui.Slider;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@UiController("clinic_UsedConsumables")
@UiDescriptor("used-consumables.xml")
@LoadDataBeforeShow
public class UsedConsumables extends Screen {

    @Inject
    private ConsumablesService consumablesService;
    @Inject
    private Notifications notifications;
    @Inject
    private VBoxLayout sliderBox;

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        Slider slider = new Slider(0, 100);
        slider.addValueChangeListener(valueChangedEvent -> {
            notifications.create()
                    .withCaption(String.format("Slider value: %s", slider.getValue()))
                    .show();
        });
        sliderBox.unwrap(CubaVerticalActionsLayout.class).addComponent(slider);
    }


    @Install(to = "consumablesDl", target = Target.DATA_LOADER)
    private List<Consumable> consumablesDlLoadDelegate(LoadContext<Consumable> loadContext) {
        return consumablesService.getUsedConsumables();
    }

    @Install(target = Target.DATA_CONTEXT)
    private Set<Entity> commitDelegate(CommitContext commitContext) {
        return Collections.emptySet();
    }


}