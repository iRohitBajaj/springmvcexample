package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshController {
    @Autowired
    private ContextRefresher refreshAppplicationContext;

    @Autowired
    private RefreshScope refreshScope;

    @GetMapping(path = "/refreshAll")
    public String refresh() {
        refreshScope.refreshAll();
        refreshAppplicationContext.refreshEnvironment();
        return "Refreshed";
    }
}
