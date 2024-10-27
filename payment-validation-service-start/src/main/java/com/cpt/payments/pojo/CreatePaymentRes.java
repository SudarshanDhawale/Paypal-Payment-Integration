package com.cpt.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRes {
	
	private String txnRef;
	private String providerRef;
	private String redirectUrl;

}
