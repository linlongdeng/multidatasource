package weChat.web;

import java.sql.Date;
import java.sql.Timestamp;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;

public class CacheTest {
	private String ip = "http://192.168.82.119:3003";
	@Test
	public void test() throws Exception{
		String actionPath ="/cache/test";
		Dto  param = new BaseDto();
		BaseDto resp = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(resp);
	}
	@Test
	public void testAccessToken() throws Exception{
		String actionPath ="/cache/accesstoken";
		Dto  param = new BaseDto();
		BaseDto resp = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(resp);
	}
	@Test
	public void testCacheEvict() throws Exception{
		String actionPath ="/cache/cacheEvict";
		Dto  param = new BaseDto();
		BaseDto resp = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(resp);
	}
	
	@Test
	public void testTimeStamp(){
		long timestamp = 1658305512 - System.currentTimeMillis();
		System.out.println(timestamp);
		
	}
	@Test
	public void testjdbc() throws Exception{
		String actionPath ="/cache/testJdbc";
		Dto  param = new BaseDto();
		BaseDto resp = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(resp);
	}
	
	@Test
	public void testsave() throws Exception{
		String actionPath ="/cache/save";
		Dto  param = new BaseDto();
		BaseDto resp = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(resp);
	}
}
