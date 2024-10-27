package com.cpt.payments.service.impl;

import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.constant.PaymentService;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.pojo.CreatePaymentRes;
import com.cpt.payments.pojo.PaymentRequest;

@Service
public class PaymentServiceImpl implements PaymentService {

	Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public CreatePaymentRes createPayment(PaymentRequest paymentRequest) {
		
		if(!paymentRequest.getPayment().getProviderId().equals("PAYPAL")) {
			
			logger.info("Invalid payment request");
			
			throw new ValidationException(
				ErrorCodeEnum.INVALID_PROVIDER.getErrorCode(),
				ErrorCodeEnum.INVALID_PROVIDER.getErrorMessage(),
				HttpStatus.BAD_REQUEST
			);
		}
		
		logger.info("PaymentServiceImpl:: Valid payment request");
		logger.info("PaymentServiceImpl:: Creating payment for createPaymentRes: " + paymentRequest);
		
		CreatePaymentRes createPaymentRes = new CreatePaymentRes();
		createPaymentRes.setTxnRef("txn123456");
		createPaymentRes.setProviderRef("provdr6546542");
		createPaymentRes.setRedirectUrl("http://redirecturl.com");
		
		logger.info("PaymentServiceImpl:: returning response obj for createPaymentRes: " + paymentRequest);
		
		return createPaymentRes;
	}

}
