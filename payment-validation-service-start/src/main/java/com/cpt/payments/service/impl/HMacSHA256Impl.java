package com.cpt.payments.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.cpt.payments.service.interfaces.HMacSHA256;

@Service
public class HMacSHA256Impl implements HMacSHA256 {
	
	Logger logger = Logger.getLogger(getClass().getName());
	// HMAC-SHA-256 algorithm name
	private static final String HMAC_SHA_256 = "HmacSHA256";

	@Override
	public String calculateHMAC(String jsonInput) {
		
            String secretKey = "your-secret-key";

            try { 	
            	
				SecretKeySpec secretKeySpec = new SecretKeySpec(
            			secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA_256);

                // Get an HmacSHA256 Mac instance and initialize it with the key
                Mac mac = Mac.getInstance(HMAC_SHA_256);
                mac.init(secretKeySpec);

                // Compute the HMAC of the data
                byte[] hmacSha256 = mac.doFinal(jsonInput.getBytes(StandardCharsets.UTF_8));

                // Return the result as a Base64 encoded string
                String signature = Base64.getEncoder().encodeToString(hmacSha256);
                
                logger.info("HMAC-SHA256 : " + signature);
                
				return signature;
                
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
            
            logger.info("HMAC-SHA256 Signature null");
            
            return null;
	}

	@Override
	public boolean verifyHMAC(String data, String receivedHmac) {
		try {
            // Calculate the HMAC for the given data using the secret key
            String calculatedHmac = calculateHMAC(data);
            logger.info("HMacSHA256Impl:: data: " + data);
            logger.info("HMacSHA256Impl:: \n calculatedHmac : " + calculatedHmac + "\n   receivedHmac : " + receivedHmac);
            
            // Compare the calculated HMAC with the received HMAC
            boolean isValid = calculatedHmac.equals(receivedHmac);
        	if(isValid) {
        		logger.info("HMacSHA256Impl:: Hmac is valid " + data);
        		return isValid;
        	} else {
        		logger.info("HMacSHA256Impl:: Hmac is Invalid " + data);
        		return false;
        	}

        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}

}
