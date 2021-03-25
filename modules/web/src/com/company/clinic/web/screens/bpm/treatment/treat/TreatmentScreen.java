package com.company.clinic.web.screens.bpm.treatment.treat;

import com.haulmont.addon.bproc.web.processform.*;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("clinic_TreatmentScreen")
@UiDescriptor("treatment-screen.xml")
@LoadDataBeforeShow
@ProcessForm (
        outcomes = {
                @Outcome(id = "success"),
                @Outcome(id = "question",
                    outputVariables = {
                        @OutputVariable(name = "question", type = String.class)
                    }
                )
        }
)
public class TreatmentScreen extends Screen {

    @Inject
    @ProcessVariable(name = "description")
    private TextArea<String> description;

    @Inject
    @ProcessVariable(name = "question")
    private TextArea<String> question;

    @Inject
    @ProcessVariable(name = "visit")
    private LookupField visit;

    @Inject
    private ProcessFormContext processFormContext;

    @Subscribe("complete")
    public void onComplete(Action.ActionPerformedEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("success")
                .complete();
        closeWithDefaultAction();
    }

    @Subscribe("question")
    public void onQuestion(Action.ActionPerformedEvent event) {
        processFormContext.taskCompletion()
                .withOutcome("question")
                .addProcessVariable("question", question.getValue())
                .complete();
        closeWithDefaultAction();
    }



}