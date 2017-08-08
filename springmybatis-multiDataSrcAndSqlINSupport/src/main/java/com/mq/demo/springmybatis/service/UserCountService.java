/**
 * 
 */
package com.mq.demo.springmybatis.service;

/**
 * @author houkx
 *
 */
public interface UserCountService {
	int countUsers();

	int countAdmins();

	void addUser(String name);

	void addAdmin(String name);
}
