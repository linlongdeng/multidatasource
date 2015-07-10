package weChat.core.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = SecondDataSourceProperites.PREFIX)
public class SecondDataSourceProperites {
	public static final String PREFIX = "spring.datasource2";
	
	/**
	 * 第二个数据源名称
	 */
	public static final String SECOND_DATA_SOURCE_NAME="secordDataSource";
	
	/**
	 * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
	 */
	private String driverClassName;

	/**
	 * JDBC url of the database.
	 */
	private String url;

	/**
	 * Login user of the database.
	 */
	private String username;

	/**
	 * Login password of the database.
	 */
	private String password;

	private ClassLoader classLoader;

	/**
	 * JNDI location of the datasource. Class, url, username & password are ignored when
	 * set.
	 */
	private String jndiName;

	/**
	 * Populate the database using 'data.sql'.
	 */
	private boolean initialize = true;

	/**
	 * Platform to use in the schema resource (schema-${platform}.sql).
	 */
	private String platform = "all";

	/**
	 * Schema (DDL) script resource reference.
	 */
	private String schema;

	/**
	 * Data (DML) script resource reference.
	 */
	private String data;

	/**
	 * Do not stop if an error occurs while initializing the database.
	 */
	private boolean continueOnError = false;

	/**
	 * Statement separator in SQL initialization scripts.
	 */
	private String separator = ";";

	/**
	 * SQL scripts encoding.
	 */
	private String sqlScriptEncoding;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public String getJndiName() {
		return jndiName;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public boolean isInitialize() {
		return initialize;
	}

	public void setInitialize(boolean initialize) {
		this.initialize = initialize;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isContinueOnError() {
		return continueOnError;
	}

	public void setContinueOnError(boolean continueOnError) {
		this.continueOnError = continueOnError;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getSqlScriptEncoding() {
		return sqlScriptEncoding;
	}

	public void setSqlScriptEncoding(String sqlScriptEncoding) {
		this.sqlScriptEncoding = sqlScriptEncoding;
	}

	public static String getPrefix() {
		return PREFIX;
	}
	
}
