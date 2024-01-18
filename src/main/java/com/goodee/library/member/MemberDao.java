package com.goodee.library.member;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberDao.class);
	
//	@Autowired
//	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private SqlSession sqlSession;   // 클래스안에서만 쓰겠다!
	
	
//	// 아이디 중복 검사 - JdbcTemplate
//	public boolean isMemberCheck(String m_id) {
//		LOGGER.info("[MemberDao] isMemberCheck();");
//		String sql = "SELECT COUNT(*) FROM tbl_member WHERE m_id =?";
//		int result = jdbcTemplate.queryForObject(sql, Integer.class, m_id);
//		// 만약에 아이디가있으면
//		if(result > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	private final String namespace = "com.goodee.library.member.MemberDao.";   // . 중요   . 없으면 밑에 .을찍어줘야함
	
	// 아이디 중복 검사 - mybatis
	public boolean isMemberCheck(String m_id) {
		LOGGER.info("[MemberDao] isMemberCheck();");
		// 첫번째 매개변수는 mapper이름, 두번째 매개변수는 필요한 값
		int result =
				sqlSession.selectOne(namespace + "isMemberCheck", m_id); //결과를 단 하나만 조회해라 , mapper의아이디가 isMemberCheck다
		if(result > 0) return true;
		else return false; // else구문이 한줄이면 중괄호 생략~~
	}
	
	// 회원 정보 추가 - mybatis
	public int insertMember(MemberVo vo) {
		LOGGER.info("[MemberDao] insertMember();");
		vo.setM_pw(passwordEncoder.encode(vo.getM_pw()));
		int result = -1;
		result = sqlSession.insert(namespace + "insertMember",vo);
		return result;
	}
	
	
	
	
	
	
	
	// 회원 정보 추가 -jdbcTemplate
	//	public int insertMember(MemberVo vo) {
	//		LOGGER.info("[MemberDao] insertMember();");
	//		String sql = "INSERT INTO tbl_member(m_id, m_pw, m_name, m_gender,"
	//				+ " m_mail, m_phone, m_reg_date, m_mod_date)"
	//				+ "VALUES(?,?,?,?,?,?,NOW(),NOW())";
	//		List<String> args = new ArrayList<String>();
	//		args.add(vo.getM_id());
	//		args.add(passwordEncoder.encode(vo.getM_pw()));   //passwordEncoder 안에 encode라는 함수가 있음
	//		args.add(vo.getM_name());
	//		args.add(vo.getM_gender());
	//		args.add(vo.getM_mail());
	//		args.add(vo.getM_phone());
	//		
	//		int result = -1; 
	//		result = jdbcTemplate.update(sql, args.toArray());
	//		// 잘안됬으면 -1 , 잘됬으면 업데이트한 갯수만큼
	//		return result;
	//	}
	
	// 로그인 - 회원정보 조회 및 비밀번호 확인
	public MemberVo selectMember(MemberVo vo) {
		LOGGER.info("[MemberDao] selectMember();");
		MemberVo resultVo = new MemberVo(); // 비어있는 껍데기만 일단 만듦
		resultVo = sqlSession.selectOne(namespace + "selectMember", vo.getM_id());
		if(resultVo != null) {
			if(passwordEncoder.matches(vo.getM_pw(), resultVo.getM_pw()) == false) { //첫번재는 암호화x 두번재는 암호화
				resultVo = null;
			}
		}
		return resultVo;
		}
	
	
	
	
//	public MemberVo selectMember(MemberVo vo) { // 전달받은것은 id와 pw 뿐
//		LOGGER.info("[MemberDao] selectMember();");
//		String sql = "SELECT * FROM tbl_member WHERE m_id=?"; //vo에 있는 id를 ?에 넣어줄꺼
//		List<MemberVo> memberVos = new ArrayList<MemberVo>(); // jdbc는 배열형태로 반환해서 배열로만들어야핢
//		try {
//			// 쿼리실행의 결과를 list memberVos로 받을꺼다
//			memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() {
//				@Override
//				// rowNum이 3이면 memberVo에 1개씩 3번넣어줌
//				public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException{
//					MemberVo memberVo = new MemberVo();
//					memberVo.setM_no(rs.getInt("m_no"));
//					memberVo.setM_id(rs.getString("m_id"));
//					memberVo.setM_pw(rs.getString("m_pw"));
//					memberVo.setM_name(rs.getString("m_name"));
//					memberVo.setM_gender(rs.getString("m_gender"));
//					memberVo.setM_mail(rs.getString("m_mail"));
//					memberVo.setM_phone(rs.getString("m_phone"));
//					memberVo.setM_reg_date(rs.getString("m_reg_date"));
//					memberVo.setM_mod_date(rs.getString("m_mod_date"));
//					return memberVo;
//				}
//			}, vo.getM_id());
//			
//			if(passwordEncoder.matches(vo.getM_pw(), memberVos.get(0).getM_pw())==false){ // 첫번쨰암호화 되자않은것, 두번째는 암호화된것
//				memberVos.clear();
//			}
//			
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	
//		return memberVos.size() > 0 ? memberVos.get(0) : null;
//	}
//	
	
	public List<MemberVo> selectMemberList(){
		LOGGER.info("[MemberDao] selectMemberList();");
		List<MemberVo> resultList = new ArrayList<MemberVo>();
		resultList = sqlSession.selectList(namespace + "selectMemberList");
		return resultList;
	}
	
	
//	
//	public List<MemberVo> selectMemberList(){
//		LOGGER.info("[MemberDao] selectMemberList();");
//		String sql = "SELECT * FROM tbl_member";
//		List<MemberVo> memberVos = new ArrayList<MemberVo>();{
//			// 쿼리실행의 결과를 list memberVos로 받을꺼다
//			memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() {
//				@Override
//				// rowNum이 3이면 memberVo에 1개씩 3번넣어줌
//				public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException{
//					MemberVo memberVo = new MemberVo();
//					memberVo.setM_no(rs.getInt("m_no"));
//					memberVo.setM_id(rs.getString("m_id"));
//					memberVo.setM_pw(rs.getString("m_pw"));
//					memberVo.setM_name(rs.getString("m_name"));
//					memberVo.setM_gender(rs.getString("m_gender"));
//					memberVo.setM_mail(rs.getString("m_mail"));
//					memberVo.setM_phone(rs.getString("m_phone"));
//					memberVo.setM_reg_date(rs.getString("m_reg_date"));
//					memberVo.setM_mod_date(rs.getString("m_mod_date"));
//					return memberVo;
//				}
//			});
//			return memberVos;
//		}
//		
//		
	
	public int updateMember(MemberVo vo) {
		LOGGER.info("[MemberDao] updateMember();");
		int result = sqlSession.update(namespace + "updateMember", vo);
		return result;
	}
	
	// 회원 단일 정보 데이터베이스에서 조회(m_no 기준)
	public MemberVo selectMemberOne(int m_no) { //int m_no 기준으로 정보를가져와줘
		LOGGER.info("[MemberDao] selectMemberOne();");
		MemberVo resultVo = new MemberVo(); 
		resultVo = sqlSession.selectOne(namespace + "selectMemberOne", m_no);
		return resultVo;
	}
	
	// 아이디, 이름, 메일 기준 회원 조회
	public MemberVo selectMemberOne(MemberVo vo) {
		MemberVo memberVo =
				sqlSession.selectOne(namespace + "selectMemberForPassword",vo);
		return memberVo;
	}
	
}
