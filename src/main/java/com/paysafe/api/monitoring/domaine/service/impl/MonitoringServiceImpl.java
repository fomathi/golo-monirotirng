package com.paysafe.api.monitoring.domaine.service.impl;

import com.paysafe.api.monitoring.domaine.VitrualMonitoringDataStore;
import com.paysafe.api.monitoring.domaine.service.IMonitoringService;
import com.paysafe.api.monitoring.domaine.service.exception.MonitoringException;
import com.paysafe.api.monitoring.domaine.service.resource.ApiAccessibilityResource;
import com.paysafe.api.monitoring.domaine.service.resource.MonitorResource;
import com.paysafe.api.monitoring.domaine.service.resource.MonitorResponseResource;
import com.paysafe.api.monitoring.domaine.utils.MonitorResourceValidor;
import com.paysafe.api.monitoring.domaine.utils.MonitoringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Service
public class MonitoringServiceImpl implements IMonitoringService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    @Override
    public MonitorResponseResource startMonitoring(MonitorResource monitorResource) {
        MonitorResourceValidor.validate(monitorResource);
        VitrualMonitoringDataStore vitrualMonitoringDataStore = VitrualMonitoringDataStore.getInstance();

        String hostname = monitorResource.getHostname();

        if(vitrualMonitoringDataStore.getData().containsKey(hostname)) {
            throw new MonitoringException("There is already a monitoring process on the api "+hostname);
        }
        else {
            Map<String, HashMap<String,String>> data = new HashMap<>();
            data.put(hostname,new HashMap<>());

            vitrualMonitoringDataStore.setData(data);

            MonitoringUtils.startMonitoring(monitorResource, vitrualMonitoringDataStore);
        }

        MonitorResponseResource response = new MonitorResponseResource();
        response.setMessage(monitorResource.getHostname() + " monitoring started");

        return response;
    }

    @Override
    public MonitorResponseResource stopMonitoring(String hostname) {

        VitrualMonitoringDataStore vitrualMonitoringDataStore = VitrualMonitoringDataStore.getInstance();

        Iterator<Map.Entry<String, HashMap<String,String>>> entries = vitrualMonitoringDataStore.getData().entrySet().iterator();
        while(entries.hasNext())
        {
            Map.Entry<String, HashMap<String,String>> entry = entries.next();
            if (entry.getKey().equals(hostname)) {
                LOG.warn("Removind "+hostname+ " from entries");
                entries.remove();
            }
        }

        MonitorResponseResource response = new MonitorResponseResource();
        response.setMessage(hostname + " monitoring stopped");

        return response;
    }

    @Override
    public ApiAccessibilityResource getApiStatus() {
        return null;
    }


}
