package com.baomidou.kisso.jfinal;

import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.AuthToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.common.SSOProperties;
import com.jfinal.core.Controller;

/**
 * 
 * @author moshuai
 *
 */
public class LoginController extends Controller {
	/**
	 * 
	
	* <p>Title: LoginController.java</p>  
	
	* <p>Description: 子系统登录，重定向到sso认证</p>  
	
	
	* @author moshuai
	
	* @date 2019年8月11日
	 */
	public void index() {
		SSOToken token = SSOHelper.getToken(getRequest());
		if ( token == null) {
			/**
			 * 重定向至代理跨域地址页
			 */
			redirect("http://sso.test.com:8080/login?ReturnURL=http://my.web.com:8090/login/proxylogin");
			return;
		}else {
			setAttr("userId", token.getUid());//用于登录成功后名称相似
		}
		render("index.html");
	}
	
	/**
	 * 
	
	* <p>Title: LoginController.java</p>  
	
	* <p>Description:kisso验证属性配置 </p>  
	
	
	* @author moshuai
	
	* @date 2019年8月11日
	 */
	public void proxylogin() {
	    // 用户自定义配置获取 由于不确定性，kisso 提倡，用户自己定义配置。
         SSOProperties prop = SSOConfig.getSSOProperties();
         //业务系统私钥签名 authToken 自动设置临时会话 cookie 授权后自动销毁
         AuthToken at = SSOHelper.askCiphertext(getRequest(), getResponse(), prop.get("sso.defined.my_private_key"));
       //at.getUuid() 作为 key 设置 authToken 至分布式缓存中，然后 sso 系统二次验证
         
         //askurl 询问 sso 是否登录地址
         String askurl = prop.get("sso.defined.askurl");
       //  getRequest().setAttribute("askurl", askurl);
         setAttr("askurl", askurl);
         //askTxt 询问 token 密文
         String askData =  at.encryptAuthToken();
       //  getRequest().setAttribute("askData", askData);
         setAttr("askData", askData);
         //my 确定是否登录地址
         String okurl =  prop.get("sso.defined.oklogin");
        // getRequest().setAttribute("okurl", okurl);
         setAttr("okurl", okurl);
         render("proxylogin.jsp");
	}
	/**
	 * 
	
	* <p>Title: LoginController.java</p>  
	
	* <p>Description:回复秘文验证 </p>  
	
	
	* @author moshuai
	
	* @date 2019年8月11日
	 */
	public void oklogin() {
		 String returl = "http://my.web.com:8090/logout/timeout.html";
	        //回复密文是否存在 SSO 公钥验证回复密文是否正确 设置 MY 系统自己的 Cookie
	     String replyTxt = getRequest().getParameter("replyTxt");
	     if (replyTxt != null && !"".equals(replyTxt)) {
	            // 用户自定义配置获取 由于不确定性，kisso 提倡，用户自己定义配置。
	            SSOProperties prop = SSOConfig.getSSOProperties();
	        	AuthToken at = SSOHelper.ok(getRequest(), getResponse(), replyTxt, prop.get("sso.defined.my_public_key"),prop.get("sso.defined.sso_public_key"));
	            if (at != null) {
	                returl = "http://my.web.com:8090/login";
	                SSOToken st = new SSOToken();
	                st.setUid(at.getUid());
	                st.setTime(at.getTime());
	                
	                //设置 true 时添加 cookie 同时销毁当前 JSESSIONID 创建信任的 JSESSIONID
	                SSOHelper.setSSOCookie(getRequest(), getResponse(), st, true);
	            }
	        }
	     JSONObject json =  new JSONObject();  
	     json.put("returl", returl);
	     renderJson(json); 
	}
	public void syspage() {
		render("index.html");
	}
	public void logout() {
		try {
			SSOHelper.logout(getRequest(), getResponse());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		SSOHelper.clearLogin(getRequest(), getResponse());
		render("login.html");
	}
   

	
	
}
