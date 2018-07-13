package com.spring.web.dao;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.spring.web.entity.User;

public interface FaceDao {
	/** 查询人脸的信息 */
	public List<User> selectAllUsers();
	
	public void save(User user);

}
