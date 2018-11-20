package com.coracle.generator.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.coracle.generator.config.ColumnOverride;
import com.coracle.generator.config.Config;
import com.coracle.generator.config.IgnoreColumn;
import com.coracle.generator.config.QueryColumn;
import com.coracle.generator.config.TableConfig;
import com.coracle.generator.util.DBUtil;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 
 */
public class DBProcess {
	
	/**
	 * 解析表信息
	 * @return
	 */
	public List<TableInfo> parseTableInfo() {
		List<TableInfo> tables = new ArrayList<TableInfo>();
		List<TableConfig> tableConfigs = Config.getTableConfigs();
		if ((tableConfigs != null) && (tableConfigs.size() > 0)) {
			String schema = DBUtil.getCurrUserDataBase();
			if (StringUtils.isBlank(schema))
				schema = Config.getJdbcConnectionConfig().getUser();

			for (Iterator<TableConfig> localIterator = tableConfigs.iterator(); localIterator
					.hasNext();) {
				TableConfig tc = (TableConfig) localIterator.next();
				TableInfo ti = new TableInfo();
				ti.setTableName(tc.getTableName());
				ti.setDomainObjectName(tc.getDomainObjectName());
				ti.setRemarks(DBUtil.getTableComments(tc.getTableName()));
				ti.setColumnCount(tc.getColumnCount().intValue());
				ti.setIsView(tc.getIsView());

				Map<Short, String>  tablePrimaryKeies = DBUtil.getTablePrimaryKey(null, schema,
						tc.getTableName());

				setAllColumns(ti, schema, tc.getTableName(), tablePrimaryKeies);
				setPrimaryKeyColumns(ti, tablePrimaryKeies);
				removeIgnoreColumns(ti, tc);
				removePoIgnoreColumns(ti);
				applyColumnOverride(ti, tc);
				setQueryColumns(ti, tc);

				tables.add(ti);
			}
		}
		return tables;
	}

	/**
	 * 设置主键列
	 * @param ti
	 * @param tablePrimaryKeies
	 */
	private void setPrimaryKeyColumns(TableInfo ti,
			Map<Short, String> tablePrimaryKeies) {
		if(!ti.getIsView()){
			List<ColumnInfo> allColumns = ti.getAllColumns();
			if (null != allColumns && allColumns.size() > 0) {
				List<ColumnInfo> primaryKeyColumns = new ArrayList<ColumnInfo>();
				for (Map.Entry<Short, String> entry : tablePrimaryKeies.entrySet()) {
					for (ColumnInfo columnInfo : allColumns) {
						if (columnInfo.getColumnName().equalsIgnoreCase(
								entry.getValue())) {
							primaryKeyColumns.add(columnInfo);
							break;
						}
					}
				}
				if (primaryKeyColumns.isEmpty()) {//如果该表没有主键，那么默认设置ID字段为主键
					for (int i = 0; i < allColumns.size(); i++) {
						ColumnInfo column = allColumns.get(i);
						if(column.getColumnName().equalsIgnoreCase("ID")){
							primaryKeyColumns.add(column);
							break;
						}
					}
				}
				ti.setPrimaryKeyColumns(primaryKeyColumns);
			}
		}
	}

	/**
	 * 设置所有列
	 * @param ti
	 * @param schema
	 * @param tableName
	 * @param tablePrimaryKeies
	 * @param isView
	 */
	private void setAllColumns(TableInfo ti, String schema, String tableName,
			Map<Short, String> tablePrimaryKeies) {
		List<ColumnInfo> columns = DBUtil.getTableColumnInfo(null, schema,
				tableName, null);

		List<ColumnInfo> comments = DBUtil.getTableColumnComment(tableName);

		for (ColumnInfo comment : comments) {
			for (ColumnInfo column : columns) {
				if (comment.getColumnName().equalsIgnoreCase(
						column.getColumnName())) {
					column.setRemarks(comment.getRemarks());
					break;
				}
			}
		}
		if(!ti.getIsView()){
			columnIsPrimaryKey(columns, tablePrimaryKeies);
		}
		ti.setAllColumns(columns);
		List<ColumnInfo> entityColumns = new ArrayList<ColumnInfo>();
		entityColumns.addAll(columns);
		ti.setEntityColumns(entityColumns);
	}

	/**
	 * 判断该列是否为主键
	 * @param columns
	 * @param tablePrimaryKeies
	 */
	private void columnIsPrimaryKey(List<ColumnInfo> columns,
			Map<Short, String> tablePrimaryKeies) {
		int count = 0;//主键计数
		for (ColumnInfo columnInfo : columns) {
			boolean isPrimaryKey = false;
			for (Map.Entry<Short, String> entry : tablePrimaryKeies.entrySet()) {
				if (entry.getValue().equalsIgnoreCase(
						columnInfo.getColumnName())) {
					isPrimaryKey = true;
					count++;
				}
			}
			columnInfo.setPrimaryKey(isPrimaryKey);
		}
		if(count == 0){//等于0说明该表没有主键列，那么默认设置ID字段为主键
			for (int i = 0; i < columns.size(); i++) {
				ColumnInfo column = columns.get(i);
				if(column.getColumnName().equalsIgnoreCase("ID")){
					column.setPrimaryKey(true);
					break;
				}
			}
		}
	}

	/**
	 * 移除不需要生成的列
	 * @param ti
	 * @param tc
	 */
	private void removeIgnoreColumns(TableInfo ti, TableConfig tc) {
		List<ColumnInfo> allColumns = ti.getAllColumns();
		List<IgnoreColumn> ignoreColumns = tc.getIgnoreColumns();
		if (null != ignoreColumns && ignoreColumns.size() > 0) {
			for (IgnoreColumn ignoreColumn : ignoreColumns) {
				if (null != allColumns && allColumns.size() > 0) {
					Iterator<ColumnInfo> tableColumns = ti.getAllColumns()
							.iterator();
					while (tableColumns.hasNext()) {
						ColumnInfo column = tableColumns.next();
						if (ignoreColumn.getColumnName().equalsIgnoreCase(
								column.getColumnName())) {
							tableColumns.remove();
						}
					}
				}
			}
		}
	}
	
	/**
	 * 移除PO不需要生成的列
	 * @param ti
	 * @param tc
	 */
	private void removePoIgnoreColumns(TableInfo ti) {
		List<ColumnInfo> entityColumnsList = ti.getEntityColumns();
		List<IgnoreColumn> poIgnoreColumnsList = Config.getPoIgnoreColumns();
		if (null != poIgnoreColumnsList && poIgnoreColumnsList.size() > 0) {
			for (IgnoreColumn poIgnoreColumn : poIgnoreColumnsList) {
				if (null != entityColumnsList && entityColumnsList.size() > 0) {
					Iterator<ColumnInfo> entityColumns = entityColumnsList.iterator();
					while (entityColumns.hasNext()) {
						ColumnInfo column = entityColumns.next();
						if (poIgnoreColumn.getColumnName().equalsIgnoreCase(
								column.getColumnName())) {
							entityColumns.remove();
						}
					}
				}
			}
		}
	}
	

	/**
	 * 指定一些列的属性
	 * @param ti
	 * @param tc
	 */
	private void applyColumnOverride(TableInfo ti, TableConfig tc) {
		List<ColumnInfo> allColumns = ti.getAllColumns();
		List<ColumnOverride> columnOverrides = tc.getColumnOverrides();
		if (null != columnOverrides && columnOverrides.size() > 0) {
			for (ColumnOverride columnOverride : columnOverrides) {
				if (null != allColumns && allColumns.size() > 0) {
					for (ColumnInfo column : allColumns) {
						if (column.getColumnName().equalsIgnoreCase(
								columnOverride.getColumnName())) {
							if (!StringUtils.isBlank(columnOverride
									.getJavaProperty())) {
								column.setJavaProperty(columnOverride
										.getJavaProperty());
							}

							if (!StringUtils.isBlank(columnOverride
									.getJavaType())) {
								column.setJavaType(columnOverride.getJavaType());
							}

							if (!StringUtils.isBlank(columnOverride
									.getJdbcType())) {
								column.setJdbcTypeName(columnOverride
										.getJdbcType());
							}
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * 设置查询列
	 * @param ti
	 * @param tc
	 */
	private void setQueryColumns(TableInfo ti, TableConfig tc) {
		List<ColumnInfo> queryColumns = new ArrayList<ColumnInfo>();
		List<QueryColumn> cfgQueryColumns = tc.getQueryColumns();
		List<ColumnInfo> allColumns = ti.getAllColumns();
		for (QueryColumn queryColumn : cfgQueryColumns) {
			for (ColumnInfo column : allColumns) {
				if (column.getColumnName().equalsIgnoreCase(
						queryColumn.getColumnName())) {
					queryColumns.add(column);
					break;
				}
			}
		}
		if (queryColumns.size() > 0) {
			ti.setQueryColumns(queryColumns);
		} else {
			ti.setQueryColumns(allColumns);
		}
	}
}