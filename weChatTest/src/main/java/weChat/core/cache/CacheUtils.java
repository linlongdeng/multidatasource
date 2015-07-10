package weChat.core.cache;

public class CacheUtils {

	/**参数间隔**/
	public static final String seperator="@";
	
	/**
	 *根据参数生成key
	 * @author deng
	 * @param params
	 * @return
	 */
	public static  String hash(String prefix, Object...params){
		String key =prefix;
		for(Object param : params){
			key += param + seperator;
		}
		return key;
	}
}
