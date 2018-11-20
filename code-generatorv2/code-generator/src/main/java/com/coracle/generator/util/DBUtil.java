package com.coracle.generator.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import com.coracle.generator.config.Config;
import com.coracle.generator.config.JDBCConnectionConfig;
import com.coracle.generator.config.TableConfig;
import com.coracle.generator.database.ColumnInfo;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 
 */
public class DBUtil {

	private DBUtil() {
		super();
	}

	private static Connection connection = null;

	private static void initConnection() {
		if (null == connection) {
			try {
				JDBCConnectionConfig config = Config.getJdbcConnectionConfig();
				Class.forName(config.getDriverClass());
				connection = DriverManager.getConnection(
						config.getConnectionURL(), config.getUser(),
						config.getPassword());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() {
		if (null == connection) {
			initConnection();
		}
		return connection;
	}

	/**
	 * 获取表主键
	 * 
	 * @param catalog
	 * @param schema
	 * @param tableName
	 * @return
	 */
	public static Map<Short, String> getTablePrimaryKey(String catalog,
			String schema, String tableName) {
		Map<Short, String> keyColumns = new TreeMap<Short, String>();
		ResultSet rs = null;
		try {
			DatabaseMetaData databaseMetaData = getConnection().getMetaData();
			rs = databaseMetaData.getPrimaryKeys(catalog, schema, tableName);
			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				short keySeq = rs.getShort("KEY_SEQ");
				keyColumns.put(keySeq, columnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
		}
		return keyColumns;
	}

	/**
	 * 获取所有列信息
	 * 
	 * @param catalog
	 * @param schemaPattern
	 * @param tableNamePattern
	 * @param columnNamePattern
	 * @return
	 */
	public static List<ColumnInfo> getTableColumnInfo(String catalog,
			String schemaPattern, String tableNamePattern,
			String columnNamePattern) {
		ResultSet rs = null;
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
		try {
			DatabaseMetaData databaseMetaData = getConnection().getMetaData();
			rs = databaseMetaData.getColumns(catalog, schemaPattern,
					tableNamePattern, columnNamePattern);
			while (rs.next()) {
				ColumnInfo column = new ColumnInfo();
				String columnName = rs.getString("COLUMN_NAME");
				column.setTableName(rs.getString("TABLE_NAME"));
				column.setColumnName(columnName);
				column.setDefaultValue(rs.getString("COLUMN_DEF"));
				column.setJdbcTypeName(rs.getString("TYPE_NAME"));
				int length = rs.getInt("COLUMN_SIZE");
				column.setLength(length);
				int scale = rs.getInt("DECIMAL_DIGITS");
				column.setScale(scale);
				int jdbcType = rs.getInt("DATA_TYPE");
				column.setJdbcType(jdbcType);
				column.setJavaType(JavaTypeTranslator.getJavaType(jdbcType,
						length, scale));
				column.setFullJavaType(JavaTypeTranslator.getFullJavaType(
						jdbcType, length, scale));
				column.setJbdcTypeStr(JdbcTypeTranslator.getJdbcTypeStr(jdbcType,length,scale));
				column.setNullable("YES".equalsIgnoreCase(rs
						.getString("IS_NULLABLE")) ? true : false);
				column.setJavaProperty(StringUtil.getCamelCaseString(
						columnName, false));
				columns.add(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
		}
		return columns;
	}

	/**
	 * 获取列注释
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<ColumnInfo> getTableColumnComment(String tableName) {
		String sql = "select  t.column_comment COMMENTS,t.column_name,t.data_type,t.character_maximum_length from information_schema.columns t where TABLE_NAME=?";
		ResultSet rs = null;
		List<ColumnInfo> columns = new ArrayList<ColumnInfo>();
		PreparedStatement pstmt = null;
		try {
			pstmt = getConnection().prepareStatement(sql);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ColumnInfo column = new ColumnInfo();
				column.setColumnName(rs.getString("COLUMN_NAME"));
				column.setRemarks(rs.getString("COMMENTS"));
				columns.add(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStmt(pstmt);
			closeResultSet(rs);
		}
		return columns;
	}

	/**
	 * 获取表注释
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getTableComments(String tableName) {
		String answer = "";
		String sql = "select TABLE_COMMENT COMMENTS from INFORMATION_SCHEMA.tables where TABLE_NAME=?";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = getConnection().prepareStatement(sql);
			pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String comment = rs.getString("COMMENTS");
				if (StringUtils.isBlank(comment)) {
					answer = null;
				} else {
					answer = comment;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStmt(pstmt);
			closeResultSet(rs);
		}
		return answer;
	}

	/**
	 * 获取当前用户表空间
	 *
	 * @return
	 */
	public static String getCurrUserDataBase() {
		String answer = null;
		String sql = "select database() as CURRENT_SCHEMA";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				answer = rs.getString("CURRENT_SCHEMA").trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeStmt(pstmt);
			closeResultSet(rs);
		}
		return answer;
	}

	/**
	 * 获取当前用户的表配置
	 * 
	 * @return
	 */
	public static List<TableConfig> getCurrUserTablesConfig() {
		List<TableConfig> tableConfigList =new ArrayList<TableConfig>();
		String dataBase = getCurrUserDataBase();
		String sql = "select t.table_name,(select count(1) from INFORMATION_SCHEMA.COLUMNS c where c.TABLE_NAME=t.table_name) as col_count from INFORMATION_SCHEMA.tables t" +
				"where TABLE_SCHEMA='?'";
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = getConnection().prepareStatement(sql);
			pstmt.setString(1, dataBase);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TableConfig tableConfig = new TableConfig();
				String tableName = rs.getString("TABLE_NAME");
				int columnCount = rs.getInt("COL_COUNT");
				tableConfig.setTableName(tableName);
				tableConfig.setDomainObjectName(StringUtil.getCamelCaseString(
						tableName, true));
				tableConfig.setColumnCount(columnCount);
				tableConfigList.add(tableConfig);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStmt(pstmt);
			closeResultSet(rs);
		}
		return tableConfigList;
	}

	public static void closeConnection() {
		if (null != connection) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeStmt(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
