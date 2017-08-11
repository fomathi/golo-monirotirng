package com.paysafe.api.monitoring.domaine.service;


import com.paysafe.api.monitoring.domaine.service.resource.OverviewResources;

public interface IOverviewService {

    OverviewResources getOverview(String hostname);
}
