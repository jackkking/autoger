package com.coracle.generator.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;

import com.coracle.generator.exception.InvalidConfigurationException;
import com.coracle.generator.util.DBUtil;
import com.coracle.generator.util.StringUtil;
import com.coracle.generator.util.message.Message;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 加载各种配置文件
 */
public class Config {
	private static String configFile = "codegenerator-config.xml";
	private static Configuration configuration = null;
	private static String fileEncoding = null;
	private static JDBCConnectionConfig jdbcConnectionConfig;
	private static PackagesTypeConfig packagesTypeConfig;//包名配置
	private static TypeConvert typeConvert;
	private static List<TableConfig> tableConfigs;
	private static List<IgnoreColumn> poIgnoreColumns;//po忽视列
	private static Map<String,String> tableToModule = new HashMap<String,String>();//表对应的模块

	public static String getFileEncoding() {
		return fileEncoding;
	}

	public static JDBCConnectionConfig getJdbcConnectionConfig() {
		return jdbcConnectionConfig;
	}

	public static PackagesTypeConfig getPackagesTypeConfig() {
		return packagesTypeConfig;
	}

	public static TypeConvert getTypeConvert() {
		return typeConvert;
	}

	public static List<TableConfig> getTableConfigs() {
		return tableConfigs;
	}
	
	public static List<IgnoreColumn> getPoIgnoreColumns() {
		return poIgnoreColumns;
	}

	public static boolean init() {
		boolean isSuccess = true;
		try {
			configuration = new XMLConfiguration(configFile);
			List<String> errors = new ArrayList<String>();
			
			parseEncoding();
			parsePackagesType(errors);
			parseJDBCConnectionConfig(errors);
			parseTypeConvert(errors);
			parseTableConfig(errors);

			if (!errors.isEmpty()) {
				isSuccess = false;
				InvalidConfigurationException ex = new InvalidConfigurationException(
						errors);
				ex.printStackTrace();
			}
		} catch (ConfigurationException e) {
			isSuccess = false;
			e.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 解析包名配置
	 * @param errors
	 */
	private static void parsePackagesType(List<String> errors) {
		packagesTypeConfig = new PackagesTypeConfig();
		packagesTypeConfig.setPackagesType(configuration
				.getString("packages[@type]"));
		packagesTypeConfig.setProject(configuration
				.getString("packages[@project]"));
		packagesTypeConfig.setPoPackageName(configuration.getString(
				"packages.entity", ""));
		packagesTypeConfig.setVoPackageName(configuration.getString(
				"packages.vo",""));
		packagesTypeConfig.setDaoPackageName(configuration.getString(
				"packages.dao", ""));
		packagesTypeConfig.setMapperPackageName(configuration.getString(
				"packages.sqlMapper", ""));
		packagesTypeConfig.setServicePackageName(configuration.getString(
				"packages.service", ""));
		packagesTypeConfig.setServiceImplPackageName(configuration.getString(
				"packages.serviceImpl", ""));
		packagesTypeConfig.setControllerPackageName(configuration.getString(
				"packages.controller", ""));
//		String jspPackageName = configuration.getString("packages.jsp");
//		if (StringUtils.isBlank(jspPackageName))
//			jspPackageName = "WebContent";

//		packagesTypeConfig.setJspPackageName(jspPackageName);
		packagesTypeConfig.validate(errors);
	}

	/**
	 * 指定文件编码
	 */
	private static void parseEncoding() {
		fileEncoding = configuration.getString("fileEncoding", "UTF-8");
	}

	/**
	 * 解析jdbc数据源
	 * @param errors
	 */
	private static void parseJDBCConnectionConfig(List<String> errors) {
		jdbcConnectionConfig = new JDBCConnectionConfig();
		jdbcConnectionConfig.setDriverClass(configuration
				.getString("jdbcConnection[@driverClass]"));
		jdbcConnectionConfig.setConnectionURL(configuration
				.getString("jdbcConnection[@connectionURL]"));
		jdbcConnectionConfig.setUser(configuration
				.getString("jdbcConnection[@user]"));
		jdbcConnectionConfig.setPassword(configuration.getString(
				"jdbcConnection[@password]", ""));
		jdbcConnectionConfig.validate(errors);
	}

	/**
	 * 解析codegenerator-config.xml配置文件中配置的每个表
	 * @param errors
	 */
	private static void parseTableConfig(List<String> errors) {
		List<?> tableList = configuration.getList("table[@tableName]");
		if(tableList.isEmpty()){
			tableConfigs = DBUtil.getCurrUserTablesConfig();
			
			poIgnoreColumns = new ArrayList<IgnoreColumn>();
			List<?> poIgnoreColumnsList = configuration.getList("poIgnoreColumns(0).ignoreColumn[@column]");
			for (int j = 0; j < poIgnoreColumnsList.size(); j++) {
				String columnName = (String) poIgnoreColumnsList.get(j);
				IgnoreColumn ignoreColumn = new IgnoreColumn();
				ignoreColumn.setColumnName(columnName);
				poIgnoreColumns.add(ignoreColumn);
			}
		}else{
			tableConfigs = new ArrayList<TableConfig>();
			for (int i = 0; i < tableList.size(); i++) {
				TableConfig tableConfig = new TableConfig();
				String tableName = configuration.getString("table(" + i
						+ ")[@tableName]");
				tableConfig.setTableName(tableName);
				String domainObjectName = configuration.getString("table(" + i
						+ ")[@domainObjectName]");
				if (StringUtils.isBlank(domainObjectName)) {
					domainObjectName = StringUtil.getCamelCaseString(tableName,
							true);
				}
				tableConfig.setDomainObjectName(domainObjectName);
//				String sequenceName = configuration.getString("table(" + i
//						+ ")[@sequenceName]");
//				if (StringUtils.isBlank(sequenceName)) {
//					sequenceName = "SEQ_" + tableName;
//				}
//				tableConfig.setSequenceName(sequenceName);
				String columnCount = configuration.getString("table(" + i
						+ ")[@columnCount]");
				if (StringUtils.isBlank(columnCount)) {
					columnCount = "1";
				}
				try {
					if (Integer.parseInt(columnCount) < 1) {
						errors.add(Message.getString("ValidationError.11"));
					}
				} catch (NumberFormatException e) {
					errors.add(Message.getString("ValidationError.11"));
					columnCount = "1";
				}
				tableConfig.setColumnCount(Integer.parseInt(columnCount));

				List<?> overrideList = configuration.getList("table(" + i
						+ ").columnOverride[@column]");
				List<ColumnOverride> columnOverrides = new ArrayList<ColumnOverride>();
				for (int j = 0; j < overrideList.size(); j++) {
					ColumnOverride columnOverride = new ColumnOverride();
					columnOverride.setColumnName(configuration.getString("table("
							+ i + ").columnOverride(" + j + ")[@column]"));
					columnOverride.setJavaProperty(configuration.getString("table("
							+ i + ").columnOverride(" + j + ")[@property]"));
					columnOverride.setJavaType(configuration.getString("table(" + i
							+ ").columnOverride(" + j + ")[@javaType]"));
					columnOverride.setJdbcType(configuration.getString("table(" + i
							+ ").columnOverride(" + j + ")[@jdbcType]"));
					columnOverride.validate(errors, tableName);
					columnOverrides.add(columnOverride);
				}
				tableConfig.setColumnOverrides(columnOverrides);

				List<?> ignoreColumnList = configuration.getList("table(" + i
						+ ").ignoreColumn[@column]");
				List<IgnoreColumn> ignoreColumns = new ArrayList<IgnoreColumn>();
				for (int j = 0; j < ignoreColumnList.size(); j++) {
					IgnoreColumn ignoreColumn = new IgnoreColumn();
					ignoreColumn.setColumnName(configuration.getString("table(" + i
							+ ").ignoreColumn(" + j + ")[@column]"));
					ignoreColumn.validate(errors, tableName);
					ignoreColumns.add(ignoreColumn);
				}
				tableConfig.setIgnoreColumns(ignoreColumns);

				List<?> queryColumnList = configuration.getList("table(" + i
						+ ").queryColumn[@column]");
				List<QueryColumn> queryColumns = new ArrayList<QueryColumn>();
				for (int j = 0; j < queryColumnList.size(); j++) {
					QueryColumn queryColumn = new QueryColumn();
					queryColumn.setColumnName(configuration.getString("table(" + i
							+ ").queryColumn(" + j + ")[@column]"));
					queryColumn.validate(errors, tableName);
					queryColumns.add(queryColumn);
				}
				tableConfig.setQueryColumns(queryColumns);
				tableConfig.validate(errors, i);

				tableConfigs.add(tableConfig);
			}
		}
		
		//获取配置文件中配置的各个模块对应的表
		List<?> moduleList = configuration.getList("module[@moduleName]");
		if(!moduleList.isEmpty()){
			for (int j = 0; j < moduleList.size(); j++) {
				String moduleName = (String) moduleList.get(j);
				List<?> tableNames = configuration.getList("module(" + j+ ").tableNames");
				for (int k = 0; k < tableNames.size(); k++) {
					String tableName = (String) tableNames.get(k);
					tableToModule.put(tableName.toUpperCase(), moduleName);
				}
			}
		}
		
	}

	/**
	 * 解析codegenerator-config.xml配置文件中指定的类型转换
	 * @param errors
	 */
	private static void parseTypeConvert(List<String> errors) {
		typeConvert = new TypeConvert();
		typeConvert.setIntegerJavaType(configuration
				.getString("typeConvert.integer[@javaType]"));
		typeConvert.setDecimalJavaType(configuration
				.getString("typeConvert.decimal[@javaType]"));
		typeConvert.validate(errors);
	}
	
	/**
	 * 通过表名获取对应的模块名
	 * @param tableName
	 * @return
	 */
	public static String getModuleName(String tableName){
		return tableToModule.get(tableName) == null ? "":tableToModule.get(tableName);
	}

}