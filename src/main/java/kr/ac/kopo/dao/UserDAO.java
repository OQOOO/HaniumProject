package kr.ac.kopo.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.ac.kopo.vo.UserVO;

public class UserDAO {
	
	@Autowired
	private DataSource dataSource;
	
	public UserVO userCheck(UserVO vo) {
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT * ");
	    sql.append("FROM HC_USER ");
	    sql.append("WHERE USER_ID = ? AND PASSWORD = ? ");

	    JdbcTemplate template = new JdbcTemplate();
	    template.setDataSource(dataSource);

	    List<UserVO> resultList = template.query(sql.toString(), new BeanPropertyRowMapper<>(UserVO.class), vo.getUserId(), vo.getPassword());
	    return resultList.isEmpty() ? null : resultList.get(0);
	}
}
