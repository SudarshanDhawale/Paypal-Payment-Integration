package com.cpt.payments.constant;

import com.cpt.payments.pojo.CreatePaymentRes;
import com.cpt.payments.pojo.PaymentRequest;

public interface PaymentService {
	public CreatePaymentRes createPayment(PaymentRequest paymentRequest);
}
