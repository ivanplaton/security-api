package com.mago.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminDashboardController {

    @PostMapping(value = "/login")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<String> login() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping(value = "/login2")
    @PreAuthorize("hasRole('customer')")
    public ResponseEntity<String> login2() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
