package com.spring.research.model;

public class ResponseVO {
	
	
	private String surr_seq;   // 답변 seq
	private String sur_seq;    // 마스터 seq
	private String surq_seq;   // 질문 seq
	private String surq_title; // 질문 제목
	private String suri_num;   // 답변 번호
	private String description; // 답변 이유
	private String writer;      // 작성자
	private String reg_date;    // 작성일
	private String udt_date;    // 수정일
	
	
	public String getSurr_seq() {
		return surr_seq;
	}
	public void setSurr_seq(String surr_seq) {
		this.surr_seq = surr_seq;
	}
	public String getSur_seq() {
		return sur_seq;
	}
	public void setSur_seq(String sur_seq) {
		this.sur_seq = sur_seq;
	}
	public String getSurq_seq() {
		return surq_seq;
	}
	public void setSurq_seq(String surq_seq) {
		this.surq_seq = surq_seq;
	}
	public String getSurq_title() {
		return surq_title;
	}
	public void setSurq_title(String surq_title) {
		this.surq_title = surq_title;
	}
	public String getSuri_num() {
		return suri_num;
	}
	public void setSuri_num(String suri_num) {
		this.suri_num = suri_num;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getUdt_date() {
		return udt_date;
	}
	public void setUdt_date(String udt_date) {
		this.udt_date = udt_date;
	}
	
	
	
	
	
	
}
