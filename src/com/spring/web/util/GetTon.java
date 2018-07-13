package com.spring.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;

public class GetTon {
	
	// 百度云接口文档：https://ai.baidu.com/docs#/Face-Match-V3/top

	public static String getToken() {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		// 获取token 地址
		String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
		// 官网获取的百度云应用的AK，请替换成自己的。
		String clientId = "XXXXXXXXX";
		// 官网获取的百度云应用的SK，请替换成自己的
		String clientSecret = "XXXXXXXXXXXXXXXXXX";
		String getAccessTokenUrl = authHost
		// 1.grant_type为固定参数
				+ "grant_type=client_credentials"
				+ "&client_id=" + clientId
				+ "&client_secret=" + clientSecret;
		try {
			// 创建url网络链接对象
			URL url = new URL(getAccessTokenUrl);
			// 打开链接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置请求方法
			connection.setRequestMethod("GET");
			// 进行实际链接
			connection.connect();
			// 读取URL的响应
			br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(sb.toString());
		String token = jsonObject.getString("access_token");
		return token;
	}

	public static void main(String[] args) {
		String tonken = getToken();
		System.out.println(tonken);
	}
}
