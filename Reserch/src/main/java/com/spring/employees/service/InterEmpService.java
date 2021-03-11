package com.spring.employees.service;

import java.util.*;

public interface InterEmpService {

	List<String> deptIdList(); 
	// employees 테이블에서 근무중인 사원들의 부서번호 가져오기  

	List<Map<String, String>> empList(Map<String,Object> paraMap);
	// employees 테이블에서 조건에 만족하는 사원들을 가져오기 

	List<Map<String, String>> employeeCntByDeptname();
	// employees 테이블에서 부서명별 인원수 및 퍼센티지 가져오기 
	
	List<Map<String, String>> employeeCntByGender();
	// employees 테이블에서 성별 인원수 및 퍼센티지 가져오기 

	List<Map<String, String>> genderCntSpecialDeptname(Map<String,String> paraMap);
	// 특정 부서명에 근무하는 직원들의 성별 인원수 및 퍼센티지 가져오기
	
}
