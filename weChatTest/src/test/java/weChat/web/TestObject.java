package weChat.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.junit.Test;

import weChat.core.metatype.BaseDto;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

public class TestObject {

	@Test
	public void testObjectMapper() throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		BaseDto dto = new BaseDto();
		dto.put("aaa", "1234");
		dto.put("aBc", "456");
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, dto);
		String value = stringWriter.toString();
		System.out.println(value);
		Persion persion = new Persion();
		persion.setAaa("1111");
		persion.setABc("2222");;
		stringWriter =  new StringWriter();
		objectMapper.writeValue(stringWriter, persion);
		value = stringWriter.toString();
		System.out.println(value);
	}
	@Test
	public void testProperties(){
		String path = System.getProperty("java.io.tmpdir");
		System.out.println(path);
	}
	@Test
	public void testToken(){
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis);
		System.out.println(currentTimeMillis/1000);
		System.out.println();
		long time = currentTimeMillis /1000;
		System.out.println(time);
		Date date = new Date(1436499126000L);
		System.out.println(date);
	}
	
	public static class Persion{
		private String ABc;
		
		private String aaa;

		public String getABc() {
			return ABc;
		}

		public void setABc(String aBc) {
			ABc = aBc;
		}

		public String getAaa() {
			return aaa;
		}

		public void setAaa(String aaa) {
			this.aaa = aaa;
		}
	}
}


