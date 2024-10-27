package com.cpt.payments.service.interfaces;

public interface HMacSHA256 {

	String calculateHMAC(String data);
	
	boolean verifyHMAC(String data, String receivedHmac);
}
