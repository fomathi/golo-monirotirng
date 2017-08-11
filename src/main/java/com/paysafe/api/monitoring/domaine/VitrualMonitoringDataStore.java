package com.paysafe.api.monitoring.domaine;


import java.util.HashMap;
import java.util.Map;

public class VitrualMonitoringDataStore {

    private static VitrualMonitoringDataStore vitrualMonitoringDataStoreInstance = null;

    private Map<String, HashMap<String,String>> data = new HashMap<>();

    private VitrualMonitoringDataStore() {

    }

    public static VitrualMonitoringDataStore getInstance() {
        if(vitrualMonitoringDataStoreInstance == null) {
            vitrualMonitoringDataStoreInstance = new VitrualMonitoringDataStore();
        }

        return vitrualMonitoringDataStoreInstance;
    }

    public Map<String, HashMap<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, HashMap<String, String>> data) {
        this.data = data;
    }
}
