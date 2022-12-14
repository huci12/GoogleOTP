package com.otp.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.springframework.stereotype.Service;

@Service
public class GoogleOTPVertify {
	public boolean vertifyCode(String code) throws InvalidKeyException, NumberFormatException, NoSuchAlgorithmException {
		// GoogleOTP에 나온 6자리 숫자
		//String code = "245261";
		String secret = "qkqhdidurlsdkanrkqtdlskTjehehl";
		boolean checkCode = checkCode(secret, Long.parseLong(code));
		System.out.println(checkCode);
		return checkCode;
	}

	public boolean checkCode(String secret, long code) throws NoSuchAlgorithmException, InvalidKeyException {
		Base32 codec = new Base32();
		byte[] decodedKey = codec.decode(secret);
		long t = new Date().getTime() / 30000;
		// Window is used to check codes generated in the near past.
		// You can use this value to tune how far you're willing to go.
		int window = 3;
		for (int i = -window; i <= window; ++i) {
			long hash = verify_code(decodedKey, t + i);

			if (hash == code) {
				return true;
			}
		}
		return false;
	}

	public int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = new byte[8];
		long value = t;
		for (int i = 8; i-- > 0; value >>>= 8) {
			data[i] = (byte) value;
		}
		SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signKey);
		byte[] hash = mac.doFinal(data);
		int offset = hash[20 - 1] & 0xF;
		// We're using a long because Java hasn't got unsigned int.
		long truncatedHash = 0;
		for (int i = 0; i < 4; ++i) {
			truncatedHash <<= 8;
			// We are dealing with signed bytes:
			// we just keep the first byte.
			truncatedHash |= (hash[offset + i] & 0xFF);
		}
		truncatedHash &= 0x7FFFFFFF;
		truncatedHash %= 1000000;
		return (int) truncatedHash;
	}
}
