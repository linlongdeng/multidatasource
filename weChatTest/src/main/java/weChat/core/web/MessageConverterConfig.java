package weChat.core.web;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
public class MessageConverterConfig {
	/**
	 * 配置MappingJackson2HttpMessageConverter
	 * @param objectMapper
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
			ObjectMapper objectMapper) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(
				objectMapper);
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
		return converter;
	}
}
