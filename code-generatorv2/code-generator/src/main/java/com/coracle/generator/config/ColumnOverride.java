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
 * 指定列的一些属性配置
 */
public class ColumnOverride {
	private String columnName;//列名
	private String javaProperty;//java属性名
	private String jdbcType;
	private String javaType;

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getJavaProperty() {
		return this.javaProperty;
	}

	public void setJavaProperty(String javaProperty) {
		this.javaProperty = javaProperty;
	}

	public String getJdbcType() {
		return this.jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJavaType() {
		return this.javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void validate(List<String> errors, String tableName) {
		if (StringUtils.isBlank(this.columnName))
			errors.add(Message.getString("ValidationError.4", tableName));
	}
}