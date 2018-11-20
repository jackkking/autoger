package com.coracle.generator.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.coracle.generator.database.ColumnInfo;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 
 */
public class StringUtil {
	
	/**
	 * 表名转换成对象实体名，如crm_clue =>CrmClue
	 * @param inputString
	 * @param firstCharacterUppercase  是否首字母大写
	 * @return
	 */
	public static String getCamelCaseString(String inputString,
			boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
            case '_':
                if (sb.length() > 0) {
                    nextUpperCase = false;
                }
                break;
            case '-':
            case '@':
            case '$':
            case '#':
            case ' ':
            case '/':
            case '&':
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
                break;

            default:
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
                break;
            }
        }
        
        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        
        return sb.toString();
    }
	
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	public static String getImportClass(List<ColumnInfo> allColumns) {
		StringBuilder sb = new StringBuilder();
		Map<String, Object> temp = new HashMap<String, Object>();
		if (allColumns != null && allColumns.size() > 0) {
			for (ColumnInfo column : allColumns) {
				String javaType = column.getJavaType();
				if ("BigDecimal".equals(javaType) && !temp.containsKey("BigDecimal")) {
					temp.put("BigDecimal", null);
					if (sb.length() > 0) {
						sb.append("\r\n");
					}
					sb.append("import ");
					sb.append(BigDecimal.class.getName());
					sb.append(";");
				}
				
				if ("Date".equals(javaType) && !temp.containsKey("Date")) {
					temp.put("Date", null);
					if (sb.length() > 0) {
						sb.append("\r\n");
					}
					sb.append("import ");
					sb.append(Date.class.getName());
					sb.append(";");
					sb.append("\r\n");
					sb.append("import com.fasterxml.jackson.annotation.JsonFormat;");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 首字母小写
	 * @param inputString
	 * @return
	 */
	public static String firstCharToLowerCase(String inputString) {
		if (!StringUtils.isBlank(inputString)) {
			StringBuilder sb = new StringBuilder(inputString);
			
			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
			
			return sb.toString();
		} else {
			return null; 
		}
	}
	
	public static void main(String[] args) {
		String inputString = "UcFirm";
		System.out.println(StringUtil.firstCharToLowerCase(inputString));
		String str = getCamelCaseString("CRM_CLUE", true);
		System.out.println(str);
	}
}
