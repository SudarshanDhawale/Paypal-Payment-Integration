package com.cpt.payments.controller;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.constant.PaymentService;
import com.cpt.payments.pojo.CreatePaymentRes;
import com.cpt.payments.pojo.PaymentRequest;
import com.google.gson.Gson;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {
	
	Logger logger = Logger.getLogger(getClass().getName());

	private Gson gson;
	
	private PaymentService service;
	
	public PaymentController(Gson gson, PaymentService service) {
		
		this.service = service;
        this.gson = gson;
    }
	
	@PostMapping
	public ResponseEntity<CreatePaymentRes> createPayment(@RequestBody PaymentRequest paymentRequest) {
		
		logger.info("PaymentController::  payment created Payment REq: " + paymentRequest);
		 
		String paymentReqAsJson = gson.toJson(paymentRequest);
		logger.info("PaymentController:: paymentReqAsJson: " + paymentReqAsJson);
		
		CreatePaymentRes response = service.createPayment(paymentRequest);
		
		logger.info("PaymentController::  CreatePaymentReq: " + response);
		
		ResponseEntity<CreatePaymentRes> responseEntity = new ResponseEntity<>(response, HttpStatus.CREATED);
		
        return responseEntity;
    }
	
	@PostMapping("/{txnRef}/capture")
	public String capturePayment(@PathVariable String txnRef) {
		
		logger.info("PaymentController:: TxnRef: " + txnRef);
		return "capturePayment :" + txnRef;	
	}
	
	@GetMapping("/{txnRef}")
	public String getPayment(@PathVariable String txnRef) {
		
		logger.info("PaymentController:: TxnRef: " + txnRef);
		return "getPayment :" + txnRef;	
	}
}



