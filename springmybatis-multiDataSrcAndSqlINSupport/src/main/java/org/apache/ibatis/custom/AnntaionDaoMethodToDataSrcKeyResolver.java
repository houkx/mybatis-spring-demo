/**
 * 
 */
package org.apache.ibatis.custom;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author houkx
 *
 */
public class AnntaionDaoMethodToDataSrcKeyResolver implements DaoMethodToDataSrcKeyResolver {

	private ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
	private final Object NULL = new Object();
	private Map<String, String> dataSrcConfigMap;

	public AnntaionDaoMethodToDataSrcKeyResolver(Map<String, String> dataSrcConfigMap) {
		this.dataSrcConfigMap = dataSrcConfigMap;
	}

	@Override
	public Object resolveDataSourceKey(String daoMethod) {
		System.out.println("resolveDataSourceKey: daoMethod=" + daoMethod);
		Object val = cache.get(daoMethod);
		if (val == null) {
			int lastDot = daoMethod.lastIndexOf('.');
			String className = daoMethod.substring(0, lastDot);
			if (dataSrcConfigMap != null) {
				val = dataSrcConfigMap.get(daoMethod);
				if (val == null) {
					val = dataSrcConfigMap.get(className);
					if (val != null) {
						System.out.println("找到了class級別的dataSrc配置:" + val);
					}
				} else {
					System.out.println("找到了方法級別的dataSrc配置:" + val);
				}
			}
			if (val == null) {
				String methodName = daoMethod.substring(lastDot + 1);
				try {
					Class<?> clazz = Class.forName(className);
					for (Method m : clazz.getMethods()) {
						if (m.getName().equals(methodName)) {
							DataSrc dsrc = m.getAnnotation(DataSrc.class);
							if (dsrc != null) {
								val = dsrc.value();
								System.out.println("找到了註解的dataSrc配置:" + val);
							}
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			cache.putIfAbsent(daoMethod, val != null ? val : NULL);
		}
		return val == NULL ? null : val;
	}

}
