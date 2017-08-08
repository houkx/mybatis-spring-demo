/**
 * 
 */
package org.apache.ibatis.custom;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

/**
 * @author houkx
 *
 */
public class SpringManagedArgedTransactionFactory extends SpringManagedTransactionFactory {
	private DaoMethodToDataSrcKeyResolver daoMethodToDataSrcKeyResolver;
	private Map<String, String> dataSourceConfigs;

	public void setDaoMethodToDataSrcKeyResolver(DaoMethodToDataSrcKeyResolver daoMethodToDataSrcKeyResolver) {
		this.daoMethodToDataSrcKeyResolver = daoMethodToDataSrcKeyResolver;
	}

	public void setDataSourceConfigs(Map<String, String> dataSourceConfigs) {
		this.dataSourceConfigs = dataSourceConfigs;
	}

	@Override
	public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
		if (dataSource instanceof DataSourceGroup) {
			System.err.println("dataSourceConfigs=" + dataSourceConfigs);
			if (daoMethodToDataSrcKeyResolver == null) {
				daoMethodToDataSrcKeyResolver = new AnntaionDaoMethodToDataSrcKeyResolver(dataSourceConfigs);
			}
			return new SpringManagedArgedTransaction((DataSourceGroup) dataSource, daoMethodToDataSrcKeyResolver);
		}
		return super.newTransaction(dataSource, level, autoCommit);
	}
}
