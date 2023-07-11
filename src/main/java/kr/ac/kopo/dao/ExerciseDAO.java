package kr.ac.kopo.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import kr.ac.kopo.vo.ExerciseVO;

public class ExerciseDAO {

	@Autowired
	private DataSource dataSource;
	
	public List<ExerciseVO> getExerciseData(String uid) {
		
		String sql = "SELECT * FROM EXERCISE WHERE USER_ID = ? ";
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource);
		List<ExerciseVO> exList = template.query(sql, new BeanPropertyRowMapper<>(ExerciseVO.class), uid);
		
		return exList;
	}
	
	public void insertExerciseData(ExerciseVO vo) {
		
		StringBuilder isql = new StringBuilder();
		isql.append("INSERT INTO EXERCISE (E_NO, USER_ID, TYPE, CNT, AVG_CNT, MAX_CNT) ");
		isql.append("VALUES ( ");
		isql.append("   	 (SELECT COALESCE(MAX(E_NO), 0) + 1 FROM EXERCISE), ");
		isql.append(" 		 ?, ");
		isql.append("    	 ?, ");
		isql.append("   	 ?, ");
		isql.append("  		 (SELECT AVG(CNT) FROM EXERCISE WHERE USER_ID = 'idid' AND TYPE = 'dumbbel'), ");
		isql.append("   	 (SELECT MAX(CNT) FROM EXERCISE WHERE USER_ID = 'idid' AND TYPE = 'dumbbel') ");
		isql.append(") ");

		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource);
		template.update(isql.toString(), vo.getUserId(), vo.getType(), vo.getCnt());
		
		StringBuilder nullByZeroSql = new StringBuilder();
		nullByZeroSql.append("UPDATE EXERCISE ");
		nullByZeroSql.append("SET AVG_CNT = 0 ");
		nullByZeroSql.append(",   MAX_CNT = 0 ");
		nullByZeroSql.append("WHERE AVG_CNT IS NULL ");
		
		template.update(nullByZeroSql.toString());
		
		StringBuilder usql = new StringBuilder();
		usql.append("UPDATE EXERCISE ");
		usql.append("SET AVG_CNT = ( ");
		usql.append("    SELECT AVG(CNT) ");
		usql.append("    FROM EXERCISE e2 ");
		usql.append("    WHERE e2.USER_ID = EXERCISE.USER_ID AND e2.TYPE = EXERCISE.TYPE ");
		usql.append("), ");
		usql.append("MAX_CNT = (");
		usql.append("    SELECT MAX(CNT) ");
		usql.append("    FROM EXERCISE e2 ");
		usql.append("    WHERE e2.USER_ID = EXERCISE.USER_ID AND e2.TYPE = EXERCISE.TYPE ");
		usql.append(") ");
		usql.append("WHERE E_NO = ( ");
		usql.append("    SELECT MAX(E_NO) ");
		usql.append("    FROM EXERCISE ");
		usql.append(") ");
		usql.append("  AND (USER_ID, TYPE) IN ( ");
		usql.append("    SELECT USER_ID, TYPE ");
		usql.append("    FROM EXERCISE ");
		usql.append("    GROUP BY USER_ID, TYPE ");
		usql.append("    HAVING COUNT(*) > 0 ");
		usql.append(") ");
		
		template.update(usql.toString());
		
	}
}
























