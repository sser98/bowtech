package com.spring.research.model;

import java.util.List;

public class QuestionVO {
	
	
	
	private String surq_seq;    // 문제 seq 
	private String sur_seq;     // 마스터 seq 번호
	private String surq_title;  // 질문지 제목
	private String suri_title1; // 선택지 답안
	private String suri_title2; // 선택지 답안
	private String suri_title3; // 선택지 답안
	private String suri_title4; // 선택지 답안
	private String suri_title5; // 선택지 답안
	private List<QuestionVO> list; // input의 name값을 인덱스를 포함해서 넘겨주면 그것을 list로 받아옴. 하나의 VO안에 필드로 보기때문에....
	
	
	public List<QuestionVO> getList() {
		return list;
	}
	public void setList(List<QuestionVO> list) {
		this.list = list;
	}
	public String getSurq_seq() {
		return surq_seq;
	}
	public void setSurq_seq(String surq_seq) {
		this.surq_seq = surq_seq;
	}
	public String getSur_seq() {
		return sur_seq;
	}
	public void setSur_seq(String sur_seq) {
		this.sur_seq = sur_seq;
	}
	public String getSurq_title() {
		return surq_title;
	}
	public void setSurq_title(String surq_title) {
		this.surq_title = surq_title;
	}
	public String getSuri_title1() {
		return suri_title1;
	}
	public void setSuri_title1(String suri_title1) {
		this.suri_title1 = suri_title1;
	}
	public String getSuri_title2() {
		return suri_title2;
	}
	public void setSuri_title2(String suri_title2) {
		this.suri_title2 = suri_title2;
	}
	public String getSuri_title3() {
		return suri_title3;
	}
	public void setSuri_title3(String suri_title3) {
		this.suri_title3 = suri_title3;
	}
	public String getSuri_title4() {
		return suri_title4;
	}
	public void setSuri_title4(String suri_title4) {
		this.suri_title4 = suri_title4;
	}
	public String getSuri_title5() {
		return suri_title5;
	}
	public void setSuri_title5(String suri_title5) {
		this.suri_title5 = suri_title5;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
