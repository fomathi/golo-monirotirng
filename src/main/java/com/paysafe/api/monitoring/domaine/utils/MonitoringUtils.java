package com.paysafe.api.monitoring.domaine.utils;


import com.paysafe.api.monitoring.domaine.VitrualMonitoringDataStore;
import com.paysafe.api.monitoring.domaine.service.exception.MonitoringException;
import com.paysafe.api.monitoring.domaine.service.resource.ApiAccessibilityResource;
import com.paysafe.api.monitoring.domaine.service.resource.MonitorResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MonitoringUtils {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringUtils.class);

    public static void startMonitoring(MonitorResource monitorResource, VitrualMonitoringDataStore vitrualMonitoringDataStore) {

        RestTemplate restTemplate = new RestTemplate();
        Timer timer = new Timer("Timer");
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                LOG.info("Monitoring performed on " + new Date());
                monitoring(monitorResource, vitrualMonitoringDataStore, restTemplate, timer);
            }
        };

        long delay  = Long.valueOf(monitorResource.getInterval());
        timer.scheduleAtFixedRate(repeatedTask, delay, delay);

    }

    private static void monitoring(MonitorResource monitorResource, VitrualMonitoringDataStore vitrualMonitoringDataStore,RestTemplate restTemplate, Timer timer) {
        try {
            String hostname = monitorResource.getHostname();
            ResponseEntity<ApiAccessibilityResource> response =  restTemplate.getForEntity(hostname, ApiAccessibilityResource.class);
            if(response!=null && response.getStatusCode().equals(HttpStatus.OK)) {
                HashMap<String,String> map = vitrualMonitoringDataStore.getData().get(hostname);
                if(map!=null) {
                    map.put(new Date().toString(), response.getBody().getStatus());
                } else {
                    timer.cancel();
                    timer.purge();
                    LOG.info(hostname +" Monitoring will be stopped : " + new Date());
                }
            }

        } catch (MonitoringException e) {
            throw new MonitoringException(e.getMessage());
        }
    }
}
