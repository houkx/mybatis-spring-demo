/**
 * 
 */
package org.apache.ibatis.custom;

import static org.springframework.util.Assert.notNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author houkx
 *
 */
public class SpringManagedArgedTransaction implements ArgedTransaction {
	private static final Log LOGGER = LogFactory.getLog(SpringManagedTransaction.class);
	private final DataSourceGroup dataSourceGroup;
	private final DaoMethodToDataSrcKeyResolver daoMethodToDataSrcKeyResolver;

	public SpringManagedArgedTransaction(DataSourceGroup dataSourceGroup,
			DaoMethodToDataSrcKeyResolver daoMethodToDataSrcKeyResolver) {
		notNull(dataSourceGroup, "No DataSource specified");
		notNull(daoMethodToDataSrcKeyResolver, "No DaoMethodToDataSrcKeyResolver specified");
		this.daoMethodToDataSrcKeyResolver = daoMethodToDataSrcKeyResolver;
		this.dataSourceGroup = dataSourceGroup;
	}
	private DataSource dataSource;
	private Connection connection;

	private boolean isConnectionTransactional;

	private boolean autoCommit;

	@Override
	public Connection getConnection(String daoMethod) throws SQLException {
		Object lookupKey = daoMethodToDataSrcKeyResolver.resolveDataSourceKey(daoMethod);
		dataSource = dataSourceGroup.getDataSource(lookupKey);
		openConnection(dataSource); 
		return connection;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Connection getConnection() throws SQLException {
		throw new SQLException(new IllegalAccessException("use getConnection(\"daoMethod\")"));
//		if (this.connection == null) {
//			openConnection();
//		}
//		return this.connection;
	}

	/**
	 * Gets a connection from Spring transaction manager and discovers if this
	 * {@code Transaction} should manage connection or let it to Spring.
	 * <p>
	 * It also reads autocommit setting because when using Spring Transaction
	 * MyBatis thinks that autocommit is always false and will always call
	 * commit/rollback so we need to no-op that calls.
	 */
	private void openConnection(DataSource dataSource) throws SQLException {
		this.connection = DataSourceUtils.getConnection(dataSource);
		this.autoCommit = this.connection.getAutoCommit();
		this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.connection, dataSource);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("JDBC Connection [" + this.connection + "] will"
					+ (this.isConnectionTransactional ? " " : " not ") + "be managed by Spring");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commit() throws SQLException {
		if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Committing JDBC Connection [" + this.connection + "]");
			}
			this.connection.commit();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void rollback() throws SQLException {
		if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Rolling back JDBC Connection [" + this.connection + "]");
			}
			this.connection.rollback();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws SQLException {
		DataSourceUtils.releaseConnection(this.connection, dataSource);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getTimeout() throws SQLException {
		ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
		if (holder != null && holder.hasTimeout()) {
			return holder.getTimeToLiveInSeconds();
		}
		return null;
	}

}
