/**
 * 
 */
package org.apache.ibatis.custom;

import javax.sql.DataSource;

/**
 * @author houkx
 *
 */
public interface DataSourceGroup extends DataSource {

	DataSource getDataSource(Object lookupKey);
}
