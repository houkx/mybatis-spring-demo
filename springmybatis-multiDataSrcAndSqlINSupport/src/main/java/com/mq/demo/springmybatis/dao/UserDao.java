/**
 * 
 */
package com.mq.demo.springmybatis.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.custom.DataSrc;
import org.springframework.stereotype.Repository;

/**
 * @author houkx
 *
 */
@Repository
public interface UserDao {
	@Select("select count(1) from users where id>=#{minid} AND role in #{roles}")
	int countAllUsers(@Param("minid")int minid,@Param("roles")int...roles);
	
	@DataSrc("master")
	@Select("select count(1) from users where role=0")
	int countUsers();

	@DataSrc("slave")
	@Select("select count(1) from users where role=1")
	int countAdmins();

	@Insert("insert into users(name,role)values (#{name},#{role})")
	void add(String name, int role);
}
