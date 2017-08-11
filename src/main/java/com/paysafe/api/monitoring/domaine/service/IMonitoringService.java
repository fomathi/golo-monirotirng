package com.paysafe.api.monitoring.domaine.service;


import com.paysafe.api.monitoring.domaine.service.resource.MonitorResource;
import com.paysafe.api.monitoring.domaine.service.resource.ApiAccessibilityResource;
import com.paysafe.api.monitoring.domaine.service.resource.MonitorResponseResource;

public interface IMonitoringService {

    MonitorResponseResource startMonitoring(MonitorResource monitorResource);
    MonitorResponseResource stopMonitoring(String hostname);
    ApiAccessibilityResource getApiStatus();
}
