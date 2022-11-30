package com.otp.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Service;

@Service
public class GoogleOTPGenerator {

	public String sendUserOtp() throws InvalidKeyException, NumberFormatException, NoSuchAlgorithmException {
		// user와 host는 GoogleOTP에서 다른 암호와 구분하기 위한 값으로 아무 값이나 넣어도 상관없다.
		// user@host 형식으로 보이게 된다.
		String user = "huci12";
		String host = "gmail";
		String secret = "qkqhdidurlsdkanrkqtdlskTjehehl";
		String qrBarcodeURL = getQRBarcodeURL(user, host, secret);
		// GoogleOTP 앱에 등록할 QRCode
		System.out.println(qrBarcodeURL);

		return qrBarcodeURL;

	}

	public String getQRBarcodeURL(String user, String host, String secret) {
		String format = "https://www.google.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=otpauth://totp/%s@%s%%3Fsecret%%3D%s";
		return String.format(format, user, host, secret);
	}

}
