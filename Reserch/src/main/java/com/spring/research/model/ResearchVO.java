package com.spring.research.model;

public class ResearchVO {
	
	
	private String sur_seq;         // 마스터 seq
	private String sur_title;       // 설문 제목
	private String que_cnt;         // 문항수
	private String sur_sat_date;    // 시작날짜
	private String sur_end_date;    // 종료날짜
	private String viewcnt;         // 조회수
	private String writer;          // 작성자
	private String reg_date;        // 등록일
	private String udt_name;        // 수정자
	private String udt_date;        // 수정날짜
	
	public String getSur_seq() {
		return sur_seq;
	}
	public void setSur_seq(String sur_seq) {
		this.sur_seq = sur_seq;
	}
	public String getSur_title() {
		return sur_title;
	}
	public void setSur_title(String sur_title) {
		this.sur_title = sur_title;
	}
	public String getQue_cnt() {
		return que_cnt;
	}
	public void setQue_cnt(String que_cnt) {
		this.que_cnt = que_cnt;
	}
	public String getSur_sat_date() {
		return sur_sat_date;
	}
	public void setSur_sat_date(String sur_sat_date) {
		this.sur_sat_date = sur_sat_date;
	}
	public String getSur_end_date() {
		return sur_end_date;
	}
	public void setSur_end_date(String sur_end_date) {
		this.sur_end_date = sur_end_date;
	}
	public String getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(String viewcnt) {
		this.viewcnt = viewcnt;
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
	public String getUdt_name() {
		return udt_name;
	}
	public void setUdt_name(String udt_name) {
		this.udt_name = udt_name;
	}
	public String getUdt_date() {
		return udt_date;
	}
	public void setUdt_date(String udt_date) {
		this.udt_date = udt_date;
	}
	

	
	
	
	
}
