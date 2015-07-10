package weChat.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.core.metatype.Dto;
import weChat.core.utils.HttpClientUtils;
import weChat.parameter.km.KDynamicReqParam;

public class KmControllerTest {
	private String ip = "http://127.0.0.1:8080";
	private String accessToken ="elubfexlgjlb9k6ad116ad3fu9ku0f";
	
	
	/**kmid 获取会员信息
	 * @throws Exception
	 */
	@Test
	public void testMemberInfoByKmID() throws Exception{
		Dto  param = new BaseDto();
		String actionPath = "/Km/memberInfoByKmID";
		param.put("access_token",accessToken).put("kmid", "000000022");
		HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		
	}
	
	/**获取参数信息
	 * @throws Exception
	 */
	@Test
	public void getparames() throws Exception{
		Dto  param = new BaseDto();
		String actionPath = "/Km/getParamer";
		param.put("access_token", "mn1dcxfl1o63mcim5tt6lcwc7adl55");
		param.put("companyid",2);
		ArrayList<String> paramesNameList = new ArrayList<String>();
		paramesNameList.add("iscustomer");
		paramesNameList.add("userbindvalidatetype");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("parametername", "iscustomer111");
		Map<String,String> map1 = new HashMap<String, String>();
		map1.put("parametername", "userbindvalidatetype");
		ArrayList<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		mapList.add(map);
		mapList.add(map1);
		param.put("data", mapList);
		HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
	}
	
	
	/**参数更新
	 * @throws Exception
	 */
	@Test
	public void updateparames() throws Exception{
		Dto  param = new BaseDto();
		String actionPath = ip + "/Km/updateParamer";
		param.put("access_token", "vo4rj93um9fl5rhad1ryyv5oqvitdf");
		param.put("companyid",2);
		
		ArrayList<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		
		Map<String,String> mapEntity = new HashMap<String, String>();
		mapEntity.put("parametername", "iscustomer");
		mapEntity.put("parametervalue", "0");
		Map<String,String> mapEntity1 = new HashMap<String, String>();
		mapEntity1.put("parametername", "memberqrcodevalidtime");
		mapEntity1.put("parametervalue", "30");
		
		mapList.add(mapEntity);
		mapList.add(mapEntity1);
		
		param.put("data", mapList);
		HttpClientUtils.post(actionPath, param,
				BaseDto.class);
	}
	
	
	/**商家密码更新
	 * @throws Exception
	 */
	@Test
	public void updateCompanyPSWD() throws Exception{
		Dto  param = new BaseDto();
		String actionPath = ip + "/Km/updateCompanyPsw";
		param.put("access_token", accessToken);
		param.put("companyid",1199);
		param.put("newcompanypsw", "25d55ad283aa400af464c76d713c07adTT");//25d55ad283aa400af464c76d713c07adTT
		HttpClientUtils.post(actionPath, param,BaseDto.class);
	}
	
	/**在线申请会员卡 	待测试
	 * @throws Exception
	 */
	@Test
	public void applyForMember() throws Exception{
		Dto param = new BaseDto();
		String actionPath = ip + "/Km/applyForMember";
		param.put("access_token", accessToken);
		param.put("companyid",1197);
		param.put("customerid",99999);
		param.put("gradeid",1);
		param.put("membername","接口测试");
		param.put("sex","灯");
		param.put("papertype","");
		param.put("papernumber","");
		param.put("mobile", "01234567890");
		param.put("birthday", "2005-01-01");
		param.put("address", "");
		param.put("email", "");
		HttpClientUtils.post(actionPath, param, BaseDto.class);
	}
	
	
	/**K米APP获取在线申请会员等级
	 * @throws Exception
	 */
	@Test
	public void applyMemberLevel()throws Exception{
		Dto param = new BaseDto();
		String actionPath = ip + "/Km/applyMemberLevel";
		param.put("access_token", accessToken);
		param.put("companyid",1197);
		HttpClientUtils.post(actionPath, param, BaseDto.class);
	}
	
	
	@Test
	public void testBindCardInfo() throws Exception{
		String actionPath = "/Km/bindCardInfo";
		KDynamicReqParam param = new KDynamicReqParam();
		param.setCompanyid(1197);
		param.setAccess_token(accessToken);
		param.set("cardnum", "5000028");
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(pDto);
	}
	@Test
	public void testMemberInfo() throws Exception{
		Dto  param = new BaseDto();
		String actionPath = "/Km/memberInfo";
		param.put("access_token",accessToken).put("customerid", 1197);
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(pDto);
		
	}
	
	
	@Test
	public void testBindCard() throws Exception{
		String actionPath = "/Km/bindCard";
		KDynamicReqParam param = new KDynamicReqParam();
		param.setCompanyid(1197);
		param.setAccess_token(accessToken);
		param.set("moblie","13500001111");
		param.set("cardnum", "5000028");
		param.set("customerid",1197);
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(pDto);
	}
	
	@Test
	public void testMemberConsumeInfo() throws Exception{
		String actionPath = "/Km/memberConsumeInfo";
		KDynamicReqParam param = new KDynamicReqParam();
		param.setCompanyid(1197);
		param.setAccess_token(accessToken);
		param.set("kmid","111111");
		param.set("begintime", "2015-01-01 00:00:00");
		param.set("endtime", "2015-06-23 23:59:59");
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(pDto);
	}
	@Test
	public void testUpdateMemberInfo() throws Exception{
		String actionPath = "/Km/updateMemberInfo";
		KDynamicReqParam param = new KDynamicReqParam();
		param.setCompanyid(1197);
		param.setAccess_token(accessToken);
		param.set("kmid","000000023");
		param.set("membername","林龙灯3");
		param.set("sex", "男");
		param.set("birthday", "1988-10-21");
		param.set("mobile", "18960863890");
		BaseDto pDto = HttpClientUtils.post(ip + actionPath, param,
				BaseDto.class);
		System.out.println(pDto);
	}
}




