package com.coracle.generator.database;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0 列信息
 */
public class ColumnInfo {
	private String tableName;
	private String columnName;
	private String javaProperty;
	private String javaType;
	private String fullJavaType;
	private int jdbcType;
	private String jbdcTypeStr;// jdbcType字符串
	private String jdbcTypeName;// 数据库中的类型
	private int length;
	private int scale;
	private boolean isNullable;
	private String defaultValue;

	private String remarks;
	private boolean isPrimaryKey;

	public ColumnInfo() {
		super();
	}

	public ColumnInfo(String columnName) {
		this.columnName = columnName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

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

	public String getJavaType() {
		return this.javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getFullJavaType() {
		return this.fullJavaType;
	}

	public void setFullJavaType(String fullJavaType) {
		this.fullJavaType = fullJavaType;
	}

	public int getJdbcType() {
		return this.jdbcType;
	}

	public void setJdbcType(int jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJdbcTypeName() {
		return this.jdbcTypeName;
	}

	public void setJdbcTypeName(String jdbcTypeName) {
		this.jdbcTypeName = jdbcTypeName;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getScale() {
		return this.scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public boolean getIsNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean getIsPrimaryKey() {
		return this.isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getJbdcTypeStr() {
		return jbdcTypeStr;
	}

	public void setJbdcTypeStr(String jbdcTypeStr) {
		this.jbdcTypeStr = jbdcTypeStr;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("Column Name: ");
		sb.append(this.columnName);
		sb.append(", JDBC Type Name: ");
		sb.append(this.jdbcTypeName);
		sb.append(", Nullable: ");
		sb.append(this.isNullable);
		sb.append(", Length: ");
		sb.append(this.length);
		sb.append(", Scale: ");
		sb.append(this.scale);
		sb.append(", Remarks: ");
		sb.append(this.remarks);

		return sb.toString();
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (super.getClass() != obj.getClass()) {
			return false;
		}

		ColumnInfo column = (ColumnInfo) obj;

		return (column.getColumnName().equals(this.columnName));
	}
}