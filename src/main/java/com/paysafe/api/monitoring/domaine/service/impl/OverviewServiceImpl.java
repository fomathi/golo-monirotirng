package com.paysafe.api.monitoring.domaine.service.impl;

import com.paysafe.api.monitoring.domaine.VitrualMonitoringDataStore;
import com.paysafe.api.monitoring.domaine.service.IOverviewService;
import com.paysafe.api.monitoring.domaine.service.exception.OverviewServiceException;
import com.paysafe.api.monitoring.domaine.service.resource.OverviewResource;
import com.paysafe.api.monitoring.domaine.service.resource.OverviewResources;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OverviewServiceImpl implements IOverviewService {
    @Override
    public OverviewResources getOverview(String hostname) {

        VitrualMonitoringDataStore vitrualMonitoringDataStore = VitrualMonitoringDataStore.getInstance();
        HashMap<String,String> map = vitrualMonitoringDataStore.getData().get(hostname);

        OverviewResources overviewResources = new OverviewResources();

        if(map == null) {
            throw new OverviewServiceException("There is any monitoring process running for server : "+hostname + ". You should start the monitoring for it.");
        }
        else {

            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            List<OverviewResource> overviewResourceList = new ArrayList<>();
            while(entries.hasNext())
            {
                Map.Entry<String, String> entry = entries.next();
                OverviewResource overviewResource = new OverviewResource();
                overviewResource.setDate(entry.getKey());
                overviewResource.setStatus(entry.getValue());

                overviewResourceList.add(overviewResource);
            }
            overviewResources.setOverview(overviewResourceList);
        }

        return overviewResources;
    }
}
