package com.bb.manager.web.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin", produces = "application/json")
public class AdminController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void ping() {
    }


}
