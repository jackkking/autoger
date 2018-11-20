package com.coracle.generator.database;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 表信息
 */
public class TableInfo {
	private String tableName;//表名
	private boolean isView = false;//是否为视图
	private String domainObjectName;//对象名
//	private String sequenceName;//sequence名
	private String remarks;//备注
	private int columnCount;
	private String pageType;
	private List<ColumnInfo> allColumns;
	private List<ColumnInfo> baseColumns;
	private List<ColumnInfo> primaryKeyColumns;
	private List<ColumnInfo> queryColumns;
	private List<ColumnInfo> entityColumns;//用于实体模板生成时的列

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDomainObjectName() {
		return this.domainObjectName;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

//	public String getSequenceName() {
//		return this.sequenceName;
//	}
//
//	public void setSequenceName(String sequenceName) {
//		this.sequenceName = sequenceName;
//	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<ColumnInfo> getAllColumns() {
		return this.allColumns;
	}

	public void setAllColumns(List<ColumnInfo> allColumns) {
		this.allColumns = allColumns;
	}

	public List<ColumnInfo> getPrimaryKeyColumns() {
		return this.primaryKeyColumns;
	}

	public void setPrimaryKeyColumns(List<ColumnInfo> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}

	public List<ColumnInfo> getQueryColumns() {
		return this.queryColumns;
	}

	public void setQueryColumns(List<ColumnInfo> queryColumns) {
		this.queryColumns = queryColumns;
	}


	public List<ColumnInfo> getBaseColumns() {
		if (null != allColumns && allColumns.size() > 0) {
			baseColumns = new ArrayList<ColumnInfo>();
			for (ColumnInfo column : allColumns) {
				if (!column.getIsPrimaryKey()) {
					baseColumns.add(column);
				}
			}
		}
		return baseColumns;
	}
	
	public void setEntityColumns(List<ColumnInfo> entityColumns) {
		this.entityColumns = entityColumns;
	}

	public List<ColumnInfo> getEntityColumns() {
		return entityColumns;
	}

	public int getColumnCount() {
		return this.columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public String getPageType() {
		return this.pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public boolean getIsView() {
		return isView;
	}

	public void setIsView(boolean isView) {
		this.isView = isView;
	}
}