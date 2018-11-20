package com.coracle.generator.config;

import com.coracle.generator.util.message.Message;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * codegenerator-config.xml配置文件中的表配置
 */
public class TableConfig {
	private String tableName;//表名
	private boolean isView = false;//是否为视图
	private String domainObjectName;//对象名
	private String sequenceName;//sequence名
	private Integer columnCount;//列数量
	private List<ColumnOverride> columnOverrides;
	private List<IgnoreColumn> ignoreColumns;
	private List<QueryColumn> queryColumns;

	public TableConfig() {
		this.columnOverrides = new ArrayList<ColumnOverride>();
		this.ignoreColumns = new ArrayList<IgnoreColumn>();
		this.queryColumns = new ArrayList<QueryColumn>();
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getSequenceName() {
		return this.sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getDomainObjectName() {
		return this.domainObjectName;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	public List<ColumnOverride> getColumnOverrides() {
		return this.columnOverrides;
	}

	public void setColumnOverrides(List<ColumnOverride> columnOverrides) {
		this.columnOverrides = columnOverrides;
	}

	public List<IgnoreColumn> getIgnoreColumns() {
		return this.ignoreColumns;
	}

	public void setIgnoreColumns(List<IgnoreColumn> ignoreColumns) {
		this.ignoreColumns = ignoreColumns;
	}

	public List<QueryColumn> getQueryColumns() {
		return this.queryColumns;
	}

	public void setQueryColumns(List<QueryColumn> queryColumns) {
		this.queryColumns = queryColumns;
	}
	
	public void validate(List<String> errors, int listPosition) {
		if (StringUtils.isBlank(this.tableName))
			errors.add(Message.getString("ValidationError.3",
					Integer.toString(listPosition)));
	}

	public Integer getColumnCount() {
		return this.columnCount;
	}

	public void setColumnCount(Integer columnCount) {
		this.columnCount = columnCount;
	}

	public boolean getIsView() {
		return isView;
	}

	public void setIsView(boolean isView) {
		this.isView = isView;
	}
	
}