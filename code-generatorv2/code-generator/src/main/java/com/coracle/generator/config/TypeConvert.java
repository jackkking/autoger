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
 * 
 */
public class TypeConvert {
	private String integerJavaType;
	private String decimalJavaType;

	public String getIntegerJavaType() {
		return this.integerJavaType;
	}

	public void setIntegerJavaType(String integerJavaType) {
		this.integerJavaType = integerJavaType;
	}

	public String getDecimalJavaType() {
		return this.decimalJavaType;
	}

	public void setDecimalJavaType(String decimalJavaType) {
		this.decimalJavaType = decimalJavaType;
	}

	public void validate(List<String> errors) {
		if ((!("Long".equals(this.integerJavaType)))
				&& (!("Integer".equals(this.integerJavaType)))
				&& (!("Short".equals(this.integerJavaType)))
				&& (!("Double".equals(this.integerJavaType)))
				&& (!("Float".equals(this.integerJavaType)))
				&& (!("BigDecimal".equals(this.integerJavaType)))
				&& (!(StringUtils.isBlank(this.integerJavaType)))) {
			errors.add(Message.getString("ValidationError.10", "integer"));
		}

		if ((!("Long".equals(this.decimalJavaType)))
				&& (!("Integer".equals(this.decimalJavaType)))
				&& (!("Short".equals(this.decimalJavaType)))
				&& (!("Double".equals(this.decimalJavaType)))
				&& (!("Float".equals(this.decimalJavaType)))
				&& (!("BigDecimal".equals(this.decimalJavaType)))
				&& (!(StringUtils.isBlank(this.decimalJavaType))))
			errors.add(Message.getString("ValidationError.10", "decimal"));
	}
}