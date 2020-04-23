package com.challenge.parking.controllers;

import com.challenge.parking.services.ReportingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reporting")
public class ReportingController {

	private final ReportingService reportingService;

	public ReportingController(ReportingService reportingService) {

		this.reportingService = reportingService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generateReport(@RequestParam(value = "id", required = false) Long id) {

		return new ResponseEntity<>(reportingService.generateReport(id), HttpStatus.OK);
	}
}
