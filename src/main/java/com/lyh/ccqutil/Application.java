package com.lyh.ccqutil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lyh.ccqutil.model.Response;
import com.lyh.ccqutil.model.UserInfo;

@RestController
@EnableAutoConfiguration
public class Application {

	public static Map<String, String> userMap = new HashMap<String, String>();

	static {
		userMap.put("朕的生日", "0116");
		userMap.put("朕最爱的水果", "xigua");
		userMap.put("朕最爱的游戏", "dota");
	}

	/**
	 * 用户登录
	 * 
	 * @param luser
	 *            用户登录信息
	 * @param request
	 * @return 用户信息
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Response<Boolean> userLogin(@RequestBody UserInfo user, HttpServletRequest request) {
		if (user == null) {
			return new Response<Boolean>(false);
		}
		if (userMap.containsKey(user.getUsername()) && userMap.get(user.getUsername()).equals(user.getPassword())) {
			return new Response<Boolean>(true);
		}
		return new Response<Boolean>(false);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
