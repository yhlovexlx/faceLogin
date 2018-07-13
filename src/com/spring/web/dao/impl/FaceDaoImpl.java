package com.spring.web.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.spring.web.dao.FaceDao;
import com.spring.web.entity.User;
import com.spring.web.service.FaceService;

@Repository
public class FaceDaoImpl implements FaceDao {

	@Resource
	private SqlSessionFactory sqlSessionFactory;

	public List<User> selectAllUsers() {
		SqlSession sqlSession = sqlSessionFactory.openSession(); // ����SqlSession
		List<User> user = null;
		user = sqlSession.selectList(User.class.getName() + ".selectAllUsers");

		for (User users : user) {
			String t = new String(users.getFace());//
			System.out.println(t);
		}

		return user;

	}

	/** ���Բ�ѯȫ����ѧ�� */
	@Test
	public void testSelectAll() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		FaceService dao = (FaceService) ac.getBean("faceServiceImpl");

		dao.selectAllUsers();
	}

	/** ���Բ�ѯȫ����ѧ�� */
	@Test
	public void testSelectAlls() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		FaceService dao = (FaceService) ac.getBean("faceServiceImpl");
		List<User> users = dao.selectAllUsers();
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = sqlSessionFactory.openSession(); // ����SqlSession
		sqlSession.insert(User.class.getName() + ".save",user);
	}
	
	

}
