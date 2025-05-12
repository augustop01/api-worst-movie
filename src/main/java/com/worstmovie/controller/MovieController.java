package com.worstmovie.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worstmovie.dto.ProducerInterval;
import com.worstmovie.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/min-max-winner")
    public ResponseEntity<Map<String, List<ProducerInterval>>> getMinMaxIntervals() {
        return ResponseEntity.ok(movieService.getMinMaxIntervals());
    }
}
