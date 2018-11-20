package com.coracle.generator.codegen;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 文件类型
 */
public enum FileType {
	ENTITY,ENTITYVO, DAO, MAPPER, SERVICE, SERVICEIMPL, CONTROLLER, JSP_LIST, JSP_ADD, JSP_ADD_TABLE, JSP_UPDATE, JSP_UPDATE_TABLE, JSP_VIEW, JSP_VIEW_TABLE;

	private String fileType;

	public String getFileType() {
		return this.fileType;
	}
}