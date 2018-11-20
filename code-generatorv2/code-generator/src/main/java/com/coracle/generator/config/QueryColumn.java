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
 * 需要查询的列
 */
public class QueryColumn {
	private String columnName;

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void validate(List<String> errors, String tableName) {
		if (StringUtils.isBlank(this.columnName))
			errors.add(Message.getString("ValidationError.6", tableName));
	}
}