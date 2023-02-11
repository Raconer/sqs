package com.project.sqs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: kimdongho
 * Date: 2023/02/09
 * DESC :
 */
@RestController
public class MainController {

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
