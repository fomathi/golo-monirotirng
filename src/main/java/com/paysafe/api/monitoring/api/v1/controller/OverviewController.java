package com.paysafe.api.monitoring.api.v1.controller;

import com.paysafe.api.monitoring.domaine.service.IOverviewService;
import com.paysafe.api.monitoring.domaine.service.exception.OverviewServiceException;
import com.paysafe.api.monitoring.domaine.service.resource.OverviewResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("v1/overview")
public class OverviewController {

    @Autowired
    IOverviewService overviewService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OverviewResources overview(@RequestParam("server") String hostname) {

        return overviewService.getOverview(hostname);
    }

    @ExceptionHandler(OverviewServiceException.class)
    public void handleOverviewServiceException(OverviewServiceException exception, HttpServletResponse response) throws IOException {
        response.sendError( HttpStatus.CONFLICT.value(), exception.getMessage() );
    }
}
