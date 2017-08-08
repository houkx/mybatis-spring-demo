/**
 * 
 */
package org.apache.ibatis.custom;

/**
 * @author houkx
 *
 */
public interface DaoMethodToDataSrcKeyResolver {

	public Object resolveDataSourceKey(String daoMethod);
}
