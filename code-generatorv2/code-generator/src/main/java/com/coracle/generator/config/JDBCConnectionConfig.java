package com.coracle.generator.config;

import com.coracle.generator.util.message.Message;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * jdbc连接数据源配置
 */
public class JDBCConnectionConfig {
	private String driverClass;
	private String connectionURL;
	private String user;
	private String password;

	public String getDriverClass() {
		return this.driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getConnectionURL() {
		return this.connectionURL;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void validate(List<String> errors) {
		if (StringUtils.isBlank(this.driverClass)) {
			errors.add(Message.getString("ValidationError.0"));
		}

		if (StringUtils.isBlank(this.connectionURL)) {
			errors.add(Message.getString("ValidationError.1"));
		}

		if (StringUtils.isBlank(this.user))
			errors.add(Message.getString("ValidationError.2"));
	}
}