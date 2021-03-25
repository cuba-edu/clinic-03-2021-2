package com.company.clinic.web.screens.consumable;

import com.haulmont.charts.gui.amcharts.model.Color;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.DataItem;
import com.haulmont.charts.gui.data.ListDataProvider;
import com.haulmont.charts.gui.data.MapDataItem;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.clinic.entity.Consumable;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@UiController("clinic_Consumable.browse")
@UiDescriptor("consumable-browse.xml")
@LookupComponent("consumablesTable")
@LoadDataBeforeShow
public class ConsumableBrowse extends StandardLookup<Consumable> {

    @Inject
    private ReportGuiManager reportGuiManager;
    @Inject
    private DataManager dataManager;
    @Inject
    private SerialChart consumablePricesChart;

    @Subscribe(id = "consumablesDc", target = Target.DATA_CONTAINER)
    public void onConsumablesDcCollectionChange(CollectionContainer.CollectionChangeEvent<Consumable> event) {

        List<DataItem> items = event.getSource().getItems().stream()
                .map(c -> MapDataItem.of("name", c.getTitle(),
                        "price", c.getPrice(),
                        "color", Color.ORANGE
                ))
                .collect(Collectors.toList());

        consumablePricesChart.setDataProvider(new ListDataProvider(items));
    }



    @Subscribe("runReport")
    public void onRunReportClick(Button.ClickEvent event) {

        Report report = dataManager.load(Report.class)
                .view(ReportService.MAIN_VIEW_NAME)
                .query("select r from report$Report r where r.code = :code")
                .parameter("code", "pricelist")
                .one();

        reportGuiManager.runReport(report, this);
    }
}