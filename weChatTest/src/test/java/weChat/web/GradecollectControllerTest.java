package weChat.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import weChat.core.metatype.BaseDto;
import weChat.json.PostJsonUtils;
import weChat.parameter.manager.MReqParam;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class GradecollectControllerTest {

	String access_token = "elubfexlgjlb9k6ad116ad3fu9ku0f";

	@Test
	public void testSyncGrade() throws JsonGenerationException,
			JsonMappingException, IOException {
		String actionPath = "/Membersync/member_level";
		MReqParam param = new MReqParam();
		param.setCompanycode("00127");
		param.setAccess_token("25d55ad283aa400af464c76d713c07ad");
		param.setWechatpubinfoid(43243243);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("gradeid", 123323);
		map.put("gradecode", "4324343");
		map.put("gradename", "林龙灯123");
		map.put("status", 0);
		list.add(map);
		param.setData(list);
		Map<String, Object> result = PostJsonUtils
				.postObject(actionPath, param);
		System.out.println(result);

	}

	@Test
	public void testMemberInfo() throws JsonGenerationException,
			JsonMappingException, IOException {
		String actionPath = "/Membersync/member_info";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token("e24df12a81fd814017980d0c1fb2f968");
		param.setWechatpubinfoid(43243243);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "1234567");
		map.put("gradeid", 0);
		map.put("cardnum", "444444444444444444");
		map.put("memberid", "123456790");
		map.put("membername", "张三");
		map.put("birthday", "1977-01-01");
		map.put("sex", "男");
		map.put("papertype", "身份证");
		map.put("papernumber", "350101011001010");
		map.put("createcardtime", "2010-01-01 11:00:00");
		map.put("memberpsw", "");
		map.put("status", "启用");
		map.put("mobile", "13500001111");
		map.put("useLimitdate", "2014-01-01");
		map.put("accountbalance", 200.23);
		map.put("integralbalance", 2512);
		map.put("consumetotal", 541.2);
		map.put("consumetimes", 909);
		map.put("lastconsumetime", "2010-01-01 11:00:00");
		map.put("accountcash", 20);
		map.put("accountpresent", 343);
		map.put("accountbalance", 33.44);
		map.put("integralbalance", 33);
		map.put("consumetotal", 33);
		list.add(map);

		param.setData(list);
		Map<String, Object> result = PostJsonUtils
				.postObject(actionPath, param);
		System.out.println(result);
	}

	/**
	 * 会员消费数据上传
	 * 
	 * @throws Exception
	 */
	@Test
	public void memberConsume() throws Exception {
		String actionPath = "/Membersync/memberConsume";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token(access_token);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "000000046");
		map.put("memberid", "125422");
		map.put("changedatetime", "2016-07-08 19:28:21");
		map.put("factacceptcharge", 1000);
		map.put("accountbalance", 9999);
		map.put("changeintegral", 1111);
		map.put("integralbalance", 8888);
		map.put("chose1", "选项一");
		map.put("chose2", "选项二");
		map.put("consumetype", "消费类型");
		list.add(map);

		BaseDto map1 = new BaseDto();
		map1.put("kmid", "000000047");
		map1.put("memberid", "125422");
		map1.put("changedatetime", "2015-07-08 19:28:21");
		map1.put("factacceptcharge", 1000);
		map1.put("accountbalance", 9999);
		map1.put("changeintegral", 1111);
		map1.put("integralbalance", 8888);
		map1.put("chose1", "选项A");
		map1.put("chose2", "选项B");
		map1.put("consumetype", "消费类型");
		list.add(map1);

		param.setData(list);
		PostJsonUtils.postObject(actionPath, param);
	}
	
	/**充值成功
	 * @throws Exception
	 */
	@Test
	public void memberRecharge()throws Exception{
		String actionPath = "/Membersync/memberRecharge";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token(access_token);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "000000046");
		map.put("memberid", "125422");
		map.put("changedatetime", "2015-07-08 19:10:21");
		map.put("changemoney", 1000);
		map.put("cashchangemoney", 9999);
		map.put("presentchangemoney", 1111);
		map.put("accountbalance", 8888);
		map.put("changeintegral", 10);
		map.put("integralbalance", 1002);
		map.put("consumetype", "消费类型");
		map.put("note", "备注");
		list.add(map);
		param.setData(list);
		PostJsonUtils.postObject(actionPath, param);
	}
	
	
	/**会员积分赠送
	 * @throws Exception
	 */
	@Test
	public void memberGivingPoints() throws Exception{
		String actionPath = "/Membersync/memberGivingPoints";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token(access_token);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "000000046");
		map.put("memberid", "125422");
		map.put("changedatetime", "2016-07-07 19:17:21");
		map.put("changeintegral", 10);
		map.put("integralbalance", 1002);
		map.put("note", "备注");
		list.add(map);
		param.setData(list);
		PostJsonUtils.postObject(actionPath, param);
	}
	
	
	/**会员积分扣除数据
	 * @throws Exception
	 */
	@Test
	public void memberPointsDeduction() throws Exception{
		String actionPath = "/Membersync/memberPointsDeduction";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token(access_token);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "000000046");
		map.put("memberid", "125422");
		map.put("changedatetime", "2016-07-07 19:17:21");
		map.put("changeintegral", 10);
		map.put("integralbalance", 1002);
		map.put("note", "备注");
		list.add(map);
		param.setData(list);
		PostJsonUtils.postObject(actionPath, param);
	}
	
	/**会员积分兑换物品
	 * @throws Exception
	 */
	@Test
	public void memberPointsToGift() throws Exception{
		String actionPath = "/Membersync/memberPointsToGift";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token(access_token);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "000000046");
		map.put("memberid", "125422");
		map.put("materialname", "兑换商品，按商品拆分成多条记录");
		map.put("changedatetime", "2016-07-07 19:17:21");
		map.put("changeintegral", 10);
		map.put("integralbalance", 1002);
		map.put("note", "备注");
		list.add(map);
		param.setData(list);
		PostJsonUtils.postObject(actionPath, param);
	}
	
	
	/**会员寄存数据上传
	 * @throws Exception
	 */
	@Test
	public void memberChecked() throws Exception{
		String actionPath = "/Membersync/memberChecked";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token(access_token);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "000000046");
		map.put("memberid", "125422");
		map.put("consignvoucher", "寄存单号test");
		map.put("materialname", "寄存商品名称，按寄存商品拆成多条记录test");
		map.put("materialunit", "寄存单位test");
		map.put("consignnum", 55);
		map.put("expiredatetime", "2016-07-15 19:17:21");
		map.put("consigndatetime", "2015-07-6 19:17:21");
		map.put("operatorname", "寄存人Test");
		map.put("consigntypename", "寄存类型test");
		map.put("note", "备注test");
		list.add(map);
		param.setData(list);
		PostJsonUtils.postObject(actionPath, param);
	}
	
	
	/**会员支取数据上传
	 * @throws Exception
	 */
	@Test
	public void memberDraw() throws Exception{
		String actionPath = "/Membersync/memberDraw";
		MReqParam param = new MReqParam();
		param.setCompanycode("01103");
		param.setAccess_token(access_token);
		List<BaseDto> list = new ArrayList<>();
		BaseDto map = new BaseDto();
		map.put("kmid", "000000046");
		map.put("memberid", "125422");
		map.put("drawdownvoucher", "支取单号test");
		map.put("materialname", "寄存商品名称，按寄存商品拆成多条记录test");
		map.put("materialunit", "寄存单位test");
		map.put("drawdownnum", 99);
		map.put("drawdowndatetime", "2016-07-15 19:17:21");
		map.put("servicename", "服务员Test");
		map.put("drawdownroom", "支取包厢test");
		map.put("consigndatetime", "2016-07-6 19:17:21");
		map.put("overplusnum", "剩余数量");
		map.put("note", "备注test");
		list.add(map);
		param.setData(list);
		PostJsonUtils.postObject(actionPath, param);
	}
}
