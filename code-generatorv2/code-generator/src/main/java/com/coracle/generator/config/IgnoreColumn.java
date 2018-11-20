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
 * 排除不需要生成的列
 */
public class IgnoreColumn {
	private String columnName;

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void validate(List<String> errors, String tableName) {
		if (StringUtils.isBlank(this.columnName))
			errors.add(Message.getString("ValidationError.5", tableName));
	}
}