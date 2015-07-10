package weChat.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import org.apache.http.util.CharArrayBuffer;

public class CommonUtils {
	/** UTF-8 **/
	public static final String UTF_8 = "utf-8";

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断任意对象是否为空
	 * 
	 * @param pObj
	 * @return
	 */
	public static boolean isEmpty(Object... pObj) {
		if (pObj != null) {
			for (int i = 0; i < pObj.length; i++) {
				// 一个有空，立即返回
				if (isEmpty(pObj[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取当前时间戳
	 * 
	 * @return
	 */
	public static Timestamp currentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 根据当前时间增加指定小时数
	 * 
	 * @param hour
	 * @return
	 */
	public static Timestamp getTimestampAddHour(int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 生成指定范围的随机数
	 * 
	 * @param maxNum
	 * @return
	 */
	public static int getRandomnum(int maxNum, int minNum) {
		Random random = new Random();
		return random.nextInt(minNum) % (maxNum - minNum + 1) + minNum;

	}

	/**
	 * 生成指定长度的字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 读取流中byte
	 * 
	 * @author deng
	 * @param inputStream
	 * @return
	 */
	public static byte[] readBytes(InputStream inputStream) {
		try {
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			return outSteam.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	 /**
	 * 读取流中内容，并转换成字符串
	 * @author deng
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String readString(InputStream inputStream) throws IOException{
		 try{
			  final Reader reader = new InputStreamReader(inputStream, UTF_8);
		      int capacity = 4096;
	          final CharArrayBuffer buffer = new CharArrayBuffer(capacity);
	          final char[] tmp = new char[1024];
	          int l;
	          while((l = reader.read(tmp)) != -1) {
	              buffer.append(tmp, 0, l);
	          }
	          return buffer.toString();
		 }finally{
			 inputStream.close();
		 }
	    
	 }
	
	
	/**
	 * 获取当前时间，单位为秒
	 * @author deng
	 * @return
	 */
	public static int getCurrentTime(){
		int time = (int) (System.currentTimeMillis() /1000);
		return time;
	}
}
