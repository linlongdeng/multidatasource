package weChat.core.jpa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import weChat.core.jdbc.SecondDataSourceProperites;

@Configuration
public class JpaConfiguration {

	/** The name of the persistence unit. */
	public static final String PRIMARY_PERSISTENT_UNIT = "PRIMARY_PERSISTENT_UNIT";

	public static final String PRIMARY_ENTITY_PACKAGE = "weChat.domain.primary";

	public static final String PRIMARY_REPOSITORY_PACKAGE = "weChat.repository.primary";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JpaProperties properties;

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			EntityManagerFactoryBuilder builder) {
		Map<String, Object> vendorProperties = getVendorProperties();
		return builder.dataSource(dataSource).packages(PRIMARY_ENTITY_PACKAGE)
				.properties(vendorProperties)
				.persistenceUnit(PRIMARY_PERSISTENT_UNIT).build();
	}

	protected Map<String, Object> getVendorProperties() {
		Map<String, Object> vendorProperties = new LinkedHashMap<String, Object>();
		vendorProperties.putAll(this.properties
				.getHibernateProperties(this.dataSource));
		return vendorProperties;
	}

	@Bean()
	@Primary
	public EntityManager entityManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		return entityManager;
	}

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(
				entityManagerFactory);
		return transactionManager;
	}

	@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", value = { PRIMARY_REPOSITORY_PACKAGE }, transactionManagerRef = "transactionManager")
	private static class EnableJpaRepositoriesConfiguration {

	}

	public static final String SECOND_ENTITY_PACKAGE = "weChat.domain.second";

	public static final String SECOND_REPOSITORY_PACKAGE = "weChat.repository.second";
	
	public static final String SECOND_ENTITY_MANAGERFACTORY="secordEntityManagerFactory";
	
	public static final String SECOND_PERSISTENT_UNIT = "SECOND_PERSISTENT_UNIT";
	
	/**事务管理器**/
	public static final String SECOND_TRANSACTION_MANAGER="secordTransactinManager";


	@Resource(name = SecondDataSourceProperites.SECOND_DATA_SOURCE_NAME)
	private DataSource secordDataSource;

	@Bean(name =SECOND_ENTITY_MANAGERFACTORY)
	public LocalContainerEntityManagerFactoryBean secordEntityManagerFactory(
			EntityManagerFactoryBuilder builder) {
		Map<String, Object> vendorProperties = getVendorProperties();
		return builder.dataSource(secordDataSource)
				.packages(SECOND_ENTITY_PACKAGE).properties(vendorProperties)
				.persistenceUnit(SECOND_PERSISTENT_UNIT).build();
	}


	@Bean(name=SECOND_TRANSACTION_MANAGER)
	public PlatformTransactionManager secordTransactinManager(
			@Qualifier(SECOND_ENTITY_MANAGERFACTORY) EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager(
				entityManagerFactory);
		return transactionManager;
	}

	@EnableJpaRepositories(entityManagerFactoryRef = SECOND_ENTITY_MANAGERFACTORY, value = SECOND_REPOSITORY_PACKAGE, transactionManagerRef = SECOND_TRANSACTION_MANAGER)
	private static class EnableSecondJpaRepositoriesConfiguration {

	}

}
