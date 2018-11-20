package com.coracle.generator.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * JdbcType对应的JdbcType字符串转换
 */
public class JdbcTypeTranslator {

	/**
	 * jdbcType2jdbcType的字符串map集合
	 */
	private static Map<Integer, String> typeMap;

	static {
		typeMap = new HashMap<Integer, String>();

		typeMap.put(Types.ARRAY, "ARRAY");
		typeMap.put(Types.BIGINT, "BIGINT");
		typeMap.put(Types.BINARY, "BINARY");
		typeMap.put(Types.BIT, "BIT");
		typeMap.put(Types.BLOB, "BLOB");
		typeMap.put(Types.BOOLEAN, "BOOLEAN");
		typeMap.put(Types.CHAR, "CHAR");
		typeMap.put(Types.CLOB, "CLOB");
		typeMap.put(Types.DATALINK, "DATALINK");
		typeMap.put(Types.DATE, "DATE");
		typeMap.put(Types.DISTINCT, "DISTINCT");
		typeMap.put(Types.DOUBLE, "DOUBLE");
		typeMap.put(Types.FLOAT, "FLOAT");
		typeMap.put(Types.INTEGER, "INTEGER");
		typeMap.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
		typeMap.put(Types.LONGVARBINARY, "LONGVARBINARY");
		typeMap.put(Types.LONGVARCHAR, "LONGVARCHAR");
		typeMap.put(Types.NULL, "NULL");
		typeMap.put(Types.OTHER, "OTHER");
		typeMap.put(Types.REAL, "REAL");
		typeMap.put(Types.REF, "REF");
		typeMap.put(Types.SMALLINT, "SMALLINT");
		typeMap.put(Types.STRUCT, "STRUCT");
		typeMap.put(Types.TIME, "TIME");
		typeMap.put(Types.TIMESTAMP, "TIMESTAMP");
		typeMap.put(Types.TINYINT, "TINYINT");
		typeMap.put(Types.VARBINARY, "VARBINARY");
		typeMap.put(Types.VARCHAR, "VARCHAR");
		//typeMap.put(Types.NUMERIC, "NUMERIC");
		//typeMap.put(Types.DECIMAL, "DECIMAL");
		//typeMap.put(Types.DECIMAL, "DECIMAL");
	}

	/**
	 * 通过jdbcType获取到对应的jdbcType字符串
	 * @param jdbcType
	 * @return
	 */
	public static String getJdbcTypeStr(int jdbcType, int length, int scale) {
		String jdbcTypeStr = typeMap.get(jdbcType);
		if (StringUtils.isBlank(jdbcTypeStr)) {
			switch (jdbcType) {
			case Types.DECIMAL:
			case Types.NUMERIC:
				if (scale > 0 && length <=17) {
					jdbcTypeStr = "DOUBLE";
				}else if (scale > 0 && length > 18) {
					jdbcTypeStr = "DECIMAL";
				} else if (length > 9) {
					jdbcTypeStr = "NUMERIC";
				}  else {
					jdbcTypeStr = "INTEGER";
				}
				break;
			default:
				jdbcTypeStr = null;
				break;
			}
		}
		return jdbcTypeStr;
	}
	public static void main(String[] args) {
		System.out.println(8/10);
	}

}
