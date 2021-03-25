package com.company.clinic.web.screens.price.history;

import com.company.clinic.entity.PriceHistory;
import com.haulmont.charts.gui.components.charts.SerialChart;
import com.haulmont.charts.gui.data.ContainerDataProvider;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.gui.components.Timer;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import org.apache.commons.lang3.RandomUtils;

import javax.inject.Inject;
import java.math.BigDecimal;

@UiController("clinic_PriceHistoryScreen")
@UiDescriptor("price-history-screen.xml")
public class PriceHistoryScreen extends Screen {
    @Inject
    private SerialChart priceHistoryChart;

    @Inject
    private CollectionContainer<PriceHistory> priceHistoriesDc;
    @Inject
    private DataManager dataManager;
    @Inject
    private TimeSource timeSource;

    @Subscribe
    public void onInit(InitEvent event) {
        priceHistoryChart.setDataProvider(new ContainerDataProvider(priceHistoriesDc));
        onPriceHistoryTimerTimerAction(null);
    }

    @Subscribe("priceHistoryTimer")
    public void onPriceHistoryTimerTimerAction(Timer.TimerActionEvent event) {
        PriceHistory ph = dataManager.create(PriceHistory.class);
        ph.setTs(timeSource.currentTimestamp());
        ph.setPrice(BigDecimal.valueOf(RandomUtils.nextLong(10, 20)));

        priceHistoriesDc.getMutableItems().add(ph);
    }



}