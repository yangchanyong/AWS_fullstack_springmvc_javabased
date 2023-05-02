package com.chanyongyang.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CrawlMapper {
	@Insert("insert into t_cgv values (#{midx},#{title}, #{info} ,#{href} ,#{date} ,#{thumb} ,#{thumb_alt} ,#{age})")
	void insert(Map<String, Object> map);
	
	@Select("select * from t_cgv")
	List<Map<String, Object>> getList();
	
	@Select("select * from t_cgv where pidx = #{pidx}")
	Map<String, Object> select(String pidx);
	
	@Insert("insert into t_person(pidx, name, href) values(#{pidx}, #{name}, #{href}) ON DUPLICATE KEY UPDATE name = name")
	void insertPerson(Map<String, String> map);
	
	@Insert("insert into t_actor(pidx, midx) values(#{pidx}, #{midx})")
	void insertActor(Map<String, String> map);
	
	@Insert("insert into t_director(pidx, midx) values(#{pidx}, #{midx})")
	void insertDirector(Map<String, String> map);
	
	@Insert("insert into t_cgv_attach(odr, midx) values(#{odr}, #{midx})")
	void insertAttach(Map<String, String> map);

	@Update("update t_cgv set info = #{info}, engtitle = #{engtitle}, genre = #{genre}, runningtime = #{runningtime}, nation = #{nation} where midx = #{midx}")
	void updateCGV(Map<String, String> m2);
}
