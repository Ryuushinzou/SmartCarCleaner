package com.scc.app.service;

import com.scc.app.model.Supply;
import com.scc.app.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SupplyConsumer {

    @Autowired
    private WashingStationService washingStationService;

    @Autowired
    private SupplyService supplyService;

    @Scheduled(cron = "0 1 1 * * *")
    public void doScheduledWork() {

        //TODO
        /*
            1. get all supplies
            2. add resources to washing stations(update)
            3. update supplies to finished
         */

    }
}
