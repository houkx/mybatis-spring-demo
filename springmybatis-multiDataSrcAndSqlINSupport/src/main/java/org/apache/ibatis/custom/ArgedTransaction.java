/**
 * 
 */
package org.apache.ibatis.custom;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.transaction.Transaction;

/**
 * @author houkx
 *
 */
public interface ArgedTransaction extends Transaction {
	/**
	 * 通过DAO方法来决定返回哪个连接
	 * 
	 * @param daoMethod
	 * @return
	 * @throws SQLException
	 */
	Connection getConnection(String daoMethod) throws SQLException;
}
