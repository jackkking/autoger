package com.coracle.generator.util;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.coracle.generator.config.Config;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version
 * JdbcType对应的JavaType转换
 */
public class JavaTypeTranslator {

	/**
	 * jdbcType2javaType的map集合
	 */
	private static Map<Integer, String> typeMap;

	static {
		typeMap = new HashMap<Integer, String>();

		typeMap.put(Types.ARRAY, Object.class.getName());
		typeMap.put(Types.BIGINT, Long.class.getName());
		typeMap.put(Types.BINARY, "byte[]");
		typeMap.put(Types.BIT, Boolean.class.getName());
		typeMap.put(Types.BLOB, "byte[]");
		typeMap.put(Types.BOOLEAN, Boolean.class.getName());
		typeMap.put(Types.CHAR, String.class.getName());
		typeMap.put(Types.CLOB, String.class.getName());
		typeMap.put(Types.DATALINK, Object.class.getName());
		typeMap.put(Types.DATE, Date.class.getName());
		typeMap.put(Types.DISTINCT, Object.class.getName());
		typeMap.put(Types.DOUBLE, Double.class.getName());
		typeMap.put(Types.FLOAT, Double.class.getName());
		typeMap.put(Types.INTEGER, Integer.class.getName());
		typeMap.put(Types.JAVA_OBJECT, Object.class.getName());
		typeMap.put(Types.LONGVARBINARY, "byte[]");
		typeMap.put(Types.LONGVARCHAR, String.class.getName());
		typeMap.put(Types.NULL, Object.class.getName());
		typeMap.put(Types.OTHER, Object.class.getName());
		typeMap.put(Types.REAL, Float.class.getName());
		typeMap.put(Types.REF, Object.class.getName());
		typeMap.put(Types.SMALLINT, Short.class.getName());
		typeMap.put(Types.STRUCT, Object.class.getName());
		typeMap.put(Types.TIME, Date.class.getName());
		typeMap.put(Types.TIMESTAMP, Date.class.getName());
		typeMap.put(Types.TINYINT, Byte.class.getName());
		typeMap.put(Types.VARBINARY, "byte[]");
		typeMap.put(Types.VARCHAR, String.class.getName());
	}

	/**
	 * 通过jdbcType获取到对应的java类型名，如Integer
	 * @param jdbcType
	 * @param length
	 * @param scale
	 * @return
	 */
	public static String getJavaType(int jdbcType, int length, int scale) {
		String javaType = typeMap.get(jdbcType);
		if (StringUtils.isBlank(javaType)) {
			switch (jdbcType) {
			case Types.DECIMAL:
			case Types.NUMERIC:
				String decimalJavaType = Config.getTypeConvert().getDecimalJavaType();
				String integerJavaType = Config.getTypeConvert().getIntegerJavaType();
				if (scale > 0 && !StringUtils.isBlank(decimalJavaType)) {
					javaType = decimalJavaType;
				} else if (!StringUtils.isBlank(integerJavaType)) {
					javaType = integerJavaType;
				} else if (scale >0 && length<= 17) {
					javaType = "Double";
				}else if (scale > 0 && length > 18) {
					javaType = "BigDecimal";
				} else if (length > 9) {
					javaType = "Long";
				} else {
					javaType = "Integer";
				}
				break;
			default:
				javaType = null;
				break;
			}
		}
		if (!StringUtils.isBlank(javaType)) {
			javaType = javaType.substring(javaType.lastIndexOf(".") + 1);
		}
		return javaType;
	}

	/**
	 * 通过jdbcType获取对应的java全路径的类型名  如java.lang.Long
	 * @param jdbcType
	 * @param length
	 * @param scale
	 * @return
	 */
	public static String getFullJavaType(int jdbcType, int length, int scale) {
		String fullJavaType = typeMap.get(jdbcType);
		if (StringUtils.isBlank(fullJavaType)) {
			switch (jdbcType) {
			case Types.DECIMAL:
			case Types.NUMERIC:
				String decimalJavaType = Config.getTypeConvert().getDecimalJavaType();
				String integerJavaType = Config.getTypeConvert().getIntegerJavaType();
				if (scale > 0 && !StringUtils.isBlank(decimalJavaType)) {
					fullJavaType = Double.class.getName();
				} else if (!StringUtils.isBlank(integerJavaType)) {
					fullJavaType = Integer.class.getName();
				} else if (scale >0 && length<= 17) {
					fullJavaType = Double.class.getName();
				} else if (scale > 0 && length > 18) {
					fullJavaType = BigDecimal.class.getName();
				} else if (length > 9) {
					fullJavaType = Long.class.getName();
				} else {
					fullJavaType = Integer.class.getName();
				}
				break;
			default:
				fullJavaType = null;
				break;
			}
		}
		return fullJavaType;
	}

	public static void main(String[] args) {
		System.out.println(Types.DECIMAL + "," + Types.NUMERIC);
		String javaType = Long.class.getName();
		javaType = javaType.substring(javaType.lastIndexOf(".") + 1);
		System.out.println(javaType);
		System.out.println(Long.class.getName());
	}
}
