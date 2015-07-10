package weChat.core.metatype;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weChat.core.utils.TypeCaseHelper;



/**
 * 数据传输对象(DateTransferObject)<br>
 * 建议在参数传递过程中尽量使用Dto来传递<br>
 * 
 * @author XiongChun
 * @since 2009-06-23
 * @see Dto
 * @see java.io.Serializable
 */
@SuppressWarnings({ "serial","rawtypes" })
public class BaseDto extends HashMap<String, Object> implements Dto, Serializable {
	
	public BaseDto(){}
	
	public BaseDto(Map<String, Object> map){
		super(map);
	}

	public BaseDto(String key, Object value){
		put(key, value);
	}
	
	public BaseDto(Boolean success){
		setSuccess(success);
	}
	
	public BaseDto(Boolean success, String msg){
		setSuccess(success);
		setMsg(msg);
	}

	/**
	 * 以BigDecimal类型返回键值
	 * 
	 * @param key
	 *            键名
	 * @return BigDecimal 键值
	 */
	public BigDecimal getAsBigDecimal(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "BigDecimal", null);
		if (obj != null)
			return (BigDecimal) obj;
		else
			return null;
	}

	/**
	 * 以Date类型返回键值
	 * 
	 * @param key
	 *            键名
	 * @return Date 键值
	 */
	public Date getAsDate(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Date", "yyyy-MM-dd");
		if (obj != null)
			return (Date) obj;
		else
			return null;
	}

	/**
	 * 以Integer类型返回键值
	 * 
	 * @param key
	 *            键名
	 * @return Integer 键值
	 */
	public Integer getAsInteger(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
		if (obj != null)
			return (Integer) obj;
		else
			return null;
	}

	/**
	 * 以Long类型返回键值
	 * 
	 * @param key
	 *            键名
	 * @return Long 键值
	 */
	public Long getAsLong(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Long", null);
		if (obj != null)
			return (Long) obj;
		else
			return null;
	}

	/**
	 * 以String类型返回键值
	 * 如果key值对应的对象为空，直接返回空，不再返回""， 修改：林龙灯 2015-05-19
	 * 
	 * 
	 * @param key
	 *            键名
	 * @return String 键值
	 */
	public String getAsString(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "String", null);
		if (obj != null)
			return (String) obj;
		else
			return "";
	}
	
	/**
	 * 以List类型返回键值
	 * 
	 * @param key
	 *            键名
	 * @return List 键值
	 */
	public List getAsList(String key){
		return (List)get(key);
	}

	/**
	 * 以Timestamp类型返回键值
	 * 
	 * @param key
	 *            键名
	 * @return Timestamp 键值
	 */
	public Timestamp getAsTimestamp(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Timestamp", "yyyy-MM-dd HH:mm:ss");
		if (obj != null)
			return (Timestamp) obj;
		else
			return null;
	}
	
	/**
	 * 以Boolean类型返回键值
	 * 
	 * @param key
	 *            键名
	 * @return Timestamp 键值
	 */
	public Boolean getAsBoolean(String key){
		Object obj = TypeCaseHelper.convert(get(key), "Boolean", null);
		if (obj != null)
			return (Boolean) obj;
		else
			return null;
	}

	/**
	 * 给Dto压入第一个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param pList
	 *            压入Dto的List对象
	 */
	public void setDefaultAList(List pList) {
		put("defaultAList", pList);
	}

	/**
	 * 给Dto压入第二个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param pList
	 *            压入Dto的List对象
	 */
	public void setDefaultBList(List pList) {
		put("defaultBList", pList);
	}

	/**
	 * 获取第一个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param pList
	 *            压入Dto的List对象
	 */
	public List getDefaultAList() {
		return (List) get("defaultAList");
	}

	/**
	 * 获取第二个默认List对象<br>
	 * 为了方便存取(省去根据Key来存取和类型转换的过程)
	 * 
	 * @param pList
	 *            压入Dto的List对象
	 */
	public List getDefaultBList() {
		return (List) get("defaultBList");
	}
	
    /**
     * 给Dto压入一个默认的Json格式字符串
     * @param jsonString
     */
	public void setDefaultJson(String jsonString){
    	put("defaultJsonString", jsonString);
    }
    
    /**
     * 获取默认的Json格式字符串
     * @return
     */
    public String getDefaultJson(){
    	return getAsString("defaultJsonString");
    }

	


	

	

	
	/**
	 * 设置交易状态
	 * 
	 * @param pSuccess
	 */
	public void setSuccess(Boolean pSuccess){
		put("success", pSuccess);
		if (pSuccess) {
			//put("bflag", "1");
		}else {
			//put("bflag", "0");
		}
		
	}
	
	/**
	 * 获取交易状态
	 * 
	 * @param pSuccess
	 */
	public Boolean getSuccess(){
		return getAsBoolean("success");
	}
	
	/**
	 * 设置交易提示信息
	 * 
	 * @param pSuccess
	 */
	public void setMsg(String pMsg){
		put("msg", pMsg);
	}
	
	/**
	 * 获取交易提示信息
	 * 
	 * @param pSuccess
	 */
	public String getMsg(){
		return getAsString("msg");
	}
	
	/**
	 * 打印DTO对象
	 * 
	 */
	public void println(){
		System.out.println(this);
	}

	

	@Override
	public String toXml(String pStyle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toJson(String pFormat) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Date getAsDate(String key, String format) {
		Object obj = TypeCaseHelper.convert(get(key), "Date",format);
		if (obj != null)
			return (Date) obj;
		else
			return null;
	}

	@Override
	public Byte getAsByte(String pStr) {
		//先转换成Integer
		Integer integerValue = getAsInteger(pStr);
		return integerValue != null ? integerValue.byteValue():null;
	}
	@Override
	 public Dto put(String key, Object value){
		super.put(key, value);
		return this;		 
	 }
}
