package com.company.clinic.web.screens.bpm.treatment.start;

import com.company.clinic.entity.Visit;
import com.haulmont.addon.bproc.web.processform.OutputVariable;
import com.haulmont.addon.bproc.web.processform.ProcessForm;
import com.haulmont.addon.bproc.web.processform.ProcessFormContext;
import com.haulmont.addon.bproc.web.processform.ProcessVariable;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;

@UiController("clinic_StartTreatmentScreen")
@UiDescriptor("start-treatment-screen.xml")
@LoadDataBeforeShow
@ProcessForm (
        outputVariables = {
                @OutputVariable(name = "description", type = String.class),
                @OutputVariable(name = "nurse", type = User.class),
                @OutputVariable(name = "visit", type = Visit.class),
                @OutputVariable(name = "doctor", type = User.class)
        }
)
public class StartTreatmentScreen extends Screen {

    @Inject
    @ProcessVariable(name = "description")
    private TextArea<String> description;
    @Inject
    @ProcessVariable(name = "nurse")
    private LookupField<User> nurseLookup;
    @Inject
    @ProcessVariable(name = "visit")
    private LookupField<Visit> visitsLookup;

    @Inject
    private ProcessFormContext processFormContext;
    @Inject
    private UserSession userSession;

    @Subscribe("startTreatment")
    public void onStartTreatment(Action.ActionPerformedEvent event) {
        processFormContext.processStarting()
                .saveInjectedProcessVariables()
                .addProcessVariable("doctor", userSession.getUser())
                .start();
        closeWithDefaultAction();
    }


}