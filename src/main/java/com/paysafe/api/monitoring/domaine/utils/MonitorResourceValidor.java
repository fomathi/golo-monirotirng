package com.paysafe.api.monitoring.domaine.utils;


import com.paysafe.api.monitoring.domaine.service.exception.MonitorResourceExecption;
import com.paysafe.api.monitoring.domaine.service.resource.MonitorResource;

public class MonitorResourceValidor {

    public static void validate(MonitorResource monitorResource) {

        if("".equals(monitorResource.getHostname()) || monitorResource.getHostname() == null  ) {
          throw new MonitorResourceExecption("Hostname can't be null or empty");
        }

        else if ("".equals(monitorResource.getInterval()) || monitorResource.getInterval() == null) {
            throw new MonitorResourceExecption("Internall can't be null or empty");
        }

    }
}
