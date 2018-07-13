package com.spring.web.entity;


public class User {
	private int id;
	private byte[] face;// 列值 clob类型
	public User() {

	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}




	public byte[] getFace() {
		return face;
	}




	public void setFace(byte[] face) {
		this.face = face;
	}

	

}
