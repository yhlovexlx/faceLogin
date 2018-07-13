package com.spring.web.action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.web.entity.User;
import com.spring.web.service.FaceService;
import com.spring.web.util.GetTon;

@Controller
public class UserControll {

	private static String accessToken;

	@Resource
	private FaceService faceService;

	@ResponseBody
	@RequestMapping("/facelogin.action")
	public String onListStudent(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// 获取前端页面传过来的参数
		String base = request.getParameter("base");
		System.out.println(base);
		try {
			User u = new User();
			u.setFace(base.getBytes());
			//把前端抓取到的图片保存到数据库
			this.faceService.save(u);
			List<User> users = this.faceService.selectAllUsers();
			String base64 = "";
			PrintWriter writer = response.getWriter();
			response.reset();

			for (User user : users) {
				base64 = new String(user.getFace());
				boolean result = getResult(base, base64);
				if (result) {
					request.getSession().setAttribute("user", user);
					// 把result转换成json格式字符串
					System.out.println(result);
					// 发送给客户端
					writer.print(result);
					writer.close();
					return null;
				} else {

					System.out.println(result);
					writer.print(result);
					writer.close();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/page/404.jsp";
		}

		return null;
	}

	/** 人脸识别 比对 */
	public boolean getResult(String imStr1, String imgStr2) {

		accessToken = GetTon.getToken();
		boolean flag = false;
		BufferedReader br = null;
		String result = "";
		// 定义请求地址
		String mathUrl = "https://aip.baidubce.com/rest/2.0/face/v3/match";
		try {
			//页面抓拍到的人脸
			List<JSONObject> images = new ArrayList<>();
			JSONObject image1 = new JSONObject();
			image1.put("image", imStr1);
			image1.put("image_type", "BASE64");
			image1.put("face_type", "LIVE");
			image1.put("quality_control", "LOW");
			image1.put("liveness_control", "NORMAL");
			
			//数据库中人脸
			JSONObject image2 = new JSONObject();
			image2.put("image", imgStr2);
			image2.put("image_type", "BASE64");
			image2.put("face_type", "LIVE");
			image2.put("quality_control", "LOW");
			image2.put("liveness_control", "NORMAL");
			images.add(image1);
			images.add(image2);
			// 调用百度云 【人脸对比】接口
			String genrearlURL = mathUrl + "?access_token=" + accessToken;
			// 创建请求对象
			URL url = new URL(genrearlURL);
			// 打开请求链接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置请求方法
			connection.setRequestMethod("POST");
			// 设置通用的请求属性
			connection.setRequestProperty("Content-Type",
					"application/json");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			// 获得请求输出流对象
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			out.writeBytes(images.toString());
			// 刷新流
			out.flush();
			// 关闭流
			out.close();
			// 建立实际链接
			connection.connect();
			// 读取URL的响应
			br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);

		// result ="{"error_msg":"Unsupported openapi method","error_code":3}"

		JSONObject fromObject = JSONObject.fromObject(result);

		JSONObject jsonArray = fromObject.getJSONObject("result");

		double resultList = jsonArray.getDouble("score");
		if (resultList >= 90) {
			flag = true;

		}
		return flag;
	}

	@Test
	public void test() {
		getResult(null, null);
		System.out.println();

	}

}
