package weChat.test.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import weChat.core.jdbc.DataSourceConfiguration;
import weChat.core.jpa.JpaConfiguration;
import weChat.core.utils.CommonUtils;
import weChat.domain.second.Sendmsgdatadetail;
import weChat.repository.primary.SendmsgdatadetailRepository;
import weChat.repository.second.MySendmsgdatadetailRepository;


@Service
public class MyService {
	@Autowired
	private MySendmsgdatadetailRepository sendmsgdatadetailRepository;
	@Autowired
	private SendmsgdatadetailRepository primarysendmsgdatadetailRepository;
	@Resource(name=DataSourceConfiguration.SECOND_JDBC_TEMPLATE)
	private JdbcTemplate jdbcTemplate;
	@Transactional(JpaConfiguration.SECOND_TRANSACTION_MANAGER)
	public void testSecondData(){
		Sendmsgdatadetail detail = new Sendmsgdatadetail();
		detail.setItemName("aaa");
		detail.setItemValue("bbb");
		detail.setUniqueCode("ccc");
		List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from wj_tbl_sendmsgdatadetail ");
		if(CommonUtils.isNotEmpty(list)){
			Map<String, Object> map = list.get(0);
			Object itemName = map.get("ItemName");
			System.out.println(itemName);
		}
		sendmsgdatadetailRepository.save(detail);
		weChat.domain.primary.Sendmsgdatadetail primaryDetail = new weChat.domain.primary.Sendmsgdatadetail(); 
		primaryDetail.setItemName("aaa");
		primaryDetail.setItemValue("bbb");
		primaryDetail.setUniqueCode("ccc");
		primarysendmsgdatadetailRepository.save(primaryDetail);
		boolean flag = true;
		if(flag){
			throw new RuntimeException("测试错误");
		}
	}
}
