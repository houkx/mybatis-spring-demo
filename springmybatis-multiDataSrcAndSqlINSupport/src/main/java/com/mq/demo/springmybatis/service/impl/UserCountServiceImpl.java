/**
 * 
 */
package com.mq.demo.springmybatis.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mq.demo.springmybatis.dao.UserDao;
import com.mq.demo.springmybatis.service.UserCountService;

/**
 * @author houkx
 *
 */
@Service
public class UserCountServiceImpl implements UserCountService {
	@Resource
	private UserDao userDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mq.demo.springmybatis.service.UserCountService#counUsers()
	 */
	@Override
	public int countUsers() {
		return userDao.countUsers();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mq.demo.springmybatis.service.UserCountService#countAdmins()
	 */
	@Override
	public int countAdmins() {
		return userDao.countAdmins();
	}

	@Override
	public void addUser(String name) {
		userDao.add(name, 0);
	}

	@Override
	public void addAdmin(String name) {
		userDao.add(name, 1);
	}

}
