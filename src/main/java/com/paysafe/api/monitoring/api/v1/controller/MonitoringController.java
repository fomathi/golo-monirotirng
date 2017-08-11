package com.paysafe.api.monitoring.api.v1.controller;

import com.paysafe.api.monitoring.domaine.service.exception.MonitorResourceExecption;
import com.paysafe.api.monitoring.domaine.service.exception.MonitoringException;
import com.paysafe.api.monitoring.domaine.service.resource.MonitorResource;
import com.paysafe.api.monitoring.domaine.service.IMonitoringService;
import com.paysafe.api.monitoring.domaine.service.resource.MonitorResponseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@RestController
@RequestMapping("v1/monitoring")
public class MonitoringController {

    @Autowired
    IMonitoringService monitoringService;

    @RequestMapping(value = "/start", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitorResponseResource startMonitoring(@RequestBody @Valid @NotNull MonitorResource monitorResource) {
        MonitorResponseResource response = monitoringService.startMonitoring(monitorResource);

        return response;
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitorResponseResource stopMonitoring(@RequestParam("server") String hostname) {

        MonitorResponseResource response = monitoringService.stopMonitoring(hostname);

        return response;
    }

    @ExceptionHandler(MonitorResourceExecption.class)
    public void handleMonitorResourceExecption(MonitorResourceExecption exception, HttpServletResponse response) throws IOException {
        response.sendError( HttpStatus.BAD_REQUEST.value(), exception.getMessage() );
    }

    @ExceptionHandler(MonitoringException.class)
    public void handleMonitorExecption(MonitoringException exception, HttpServletResponse response) throws IOException {
        response.sendError( HttpStatus.CONFLICT.value(), exception.getMessage() );
    }
}
