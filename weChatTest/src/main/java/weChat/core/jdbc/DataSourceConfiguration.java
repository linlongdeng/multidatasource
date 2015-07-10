package weChat.core.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableConfigurationProperties(SecondDataSourceProperites.class)
public class DataSourceConfiguration {

	@Autowired
	private DataSourceProperties properties;

	@Bean
	@Primary
	public DataSource dataSource() {
		DataSourceBuilder factory = DataSourceBuilder
				.create(this.properties.getClassLoader())
				.driverClassName(this.properties.getDriverClassName())
				.url(this.properties.getUrl())
				.username(this.properties.getUsername())
				.password(this.properties.getPassword());
		DataSource dataSource = factory.build();
		return dataSource;
	}
	@Bean
	@Primary
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	public static final String SECOND_JDBC_TEMPLATE="secondjdbctemplate";
	@Autowired
	private SecondDataSourceProperites secondProperites;

	@Bean(name = "secordDataSource")
	@ConfigurationProperties(prefix = SecondDataSourceProperites.PREFIX)
	public DataSource secondaryDataSource() {

		DataSourceBuilder factory = DataSourceBuilder
				.create(this.secondProperites.getClassLoader())
				.driverClassName(this.secondProperites.getDriverClassName())
				.url(this.secondProperites.getUrl())
				.username(this.secondProperites.getUsername())
				.password(this.secondProperites.getPassword());
		DataSource dataSource = factory.build();
		return dataSource;
	}
	@Bean(name=SECOND_JDBC_TEMPLATE)
	public JdbcTemplate secondjdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	

}
