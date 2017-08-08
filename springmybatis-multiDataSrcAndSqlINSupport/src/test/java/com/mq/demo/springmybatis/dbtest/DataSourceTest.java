/**
 * 
 */
package com.mq.demo.springmybatis.dbtest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mq.demo.springmybatis.dao.UserDao;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:beans.xml")
public class DataSourceTest extends AbstractJUnit4SpringContextTests {
	private @Resource UserDao userDao;

	@Test
	public void dataSrcRouting_test() {
		long t0 = System.nanoTime(), st = System.currentTimeMillis();
		System.out.println("countUsers = " + userDao.countAllUsers(0, 0, 1));
		System.out.println("countUsers = " + userDao.countAllUsers(0, 0, 1));
		long t1 = System.nanoTime(), ed = System.currentTimeMillis();
		System.out.printf("*** 耗时= %d(ns) , %d(ms)\n", t1 - t0, ed - st);
		// System.out.println("countAdmins = "+userDao.countAdmins());
	}

}
