package com.spring.web.service;

import java.util.List;

import com.mysql.jdbc.Blob;
import com.spring.web.entity.User;

public interface FaceService {
	/** ��ѯ���е�������Ϣ */
	public List<User> selectAllUsers();
	
	public void save(User user);

}
