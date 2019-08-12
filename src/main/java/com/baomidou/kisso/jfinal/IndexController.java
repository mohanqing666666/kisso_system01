/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.kisso.jfinal;

import com.baomidou.kisso.MyToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.plugin.SSOJfinalInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * 首页
 * <p>
 * SSOJfinalInterceptor 登录权限拦截, 你也可以自己实现
 * </p>
 */
@Before(SSOJfinalInterceptor.class)
public class IndexController extends Controller {

	/**
	 * <p>
	 * SSOHelper.getToken(request)
	 * 
	 * 从 Cookie 解密 token 使用场景，拦截器
	 * </p>
	 * 
	 * <p>
	 * SSOHelper.attrToken(request)
	 * 
	 * 非拦截器使用减少二次解密
	 * </p>
	 */
	public void index() {
		/*
		 * 退出登录 SSOHelper.logout(request, response);
		 */
		/*
		 * <p>
		 * SSOHelper.getToken(request)
		 * 
		 * 从 Cookie 解密 token 使用场景，拦截器
		 * </p>
		 * 
		 * <p>
		 * SSOHelper.attrToken(request)
		 * 
		 * 非拦截器使用减少二次解密
		 * </p>
		 */
		MyToken token = SSOHelper.attrToken(getRequest());
		if ( token == null ) {
			redirect("http://sso.test.com:8080/login?ReturnURL=http%253A%252F%252Fsso.test.com%253A8080%252F");
		}else {
		    //从token中取出信息，进行相关信息初始化。
			System.err.println(" Long 用户ID :" + token.getId());
			System.err.println(" 登录用户ID : " + token.getUid());
			System.err.println(" 自定义属性测试 : " + token.getAbc());
		}
		System.err.println(" 当前注入运行模式是：" + SSOConfig.getInstance().getRunMode());
		render("index.html");
	}
	

}
