package com.baomidou.kisso.jfinal;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.Controller;

/**
 * 验证码
 */
public class VerifyCodeController extends Controller {

	/**
	 * 验证码
	 */
	public void index() {
		HttpServletResponse response = getResponse();
		try {
			String verifyCode = CaptchaUtil.outputImage(response.getOutputStream());
			System.out.println("验证码:" + verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
}
