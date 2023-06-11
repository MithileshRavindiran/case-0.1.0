package com.afkl.travel.exercise.controller;

import io.micrometer.core.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/dummy")
public class DummyController {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLocationByTypeAndCode() {
        return ResponseEntity.ok("Hello World");
    }
}
