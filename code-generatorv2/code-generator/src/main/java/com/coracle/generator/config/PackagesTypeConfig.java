package com.coracle.generator.config;

import com.coracle.generator.util.message.Message;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 包名配置
 */
public class PackagesTypeConfig {
	private String packagesType;
	private String project;
	private String poPackageName;
	private String  voPackageName;
	private String daoPackageName;
	private String mapperPackageName;
	private String servicePackageName;
	private String serviceImplPackageName;
	private String controllerPackageName;
	private String jspPackageName;

	public String getPackagesType() {
		return this.packagesType;
	}

	public void setPackagesType(String packagesType) {
		this.packagesType = packagesType;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPoPackageName() {
		return this.poPackageName;
	}

	public void setPoPackageName(String poPackageName) {
		this.poPackageName = poPackageName;
	}

	public String getDaoPackageName() {
		return this.daoPackageName;
	}

	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}

	public String getMapperPackageName() {
		return this.mapperPackageName;
	}

	public void setMapperPackageName(String mapperPackageName) {
		this.mapperPackageName = mapperPackageName;
	}

	public String getServicePackageName() {
		return this.servicePackageName;
	}

	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}

	public String getServiceImplPackageName() {
		return this.serviceImplPackageName;
	}

	public void setServiceImplPackageName(String serviceImplPackageName) {
		this.serviceImplPackageName = serviceImplPackageName;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}

	public String getJspPackageName() {
		return this.jspPackageName;
	}

	public void setJspPackageName(String jspPackageName) {
		this.jspPackageName = jspPackageName;
	}

	public String getVoPackageName() {
		return voPackageName;
	}

	public void setVoPackageName(String voPackageName) {
		this.voPackageName = voPackageName;
	}

	public void validate(List<String> errors) {
		if (StringUtils.isBlank(this.packagesType)) {
			this.packagesType = "module";
		} else if ((!("module".equals(this.packagesType)))
				&& (!("category".equals(this.packagesType)))
				&& (!("customization".equals(this.packagesType)))) {
			errors.add(Message.getString("ValidationError.8"));
		}

		if ((!("customization".equals(this.packagesType)))
				&& (StringUtils.isBlank(this.project)))
			errors.add(Message.getString("ValidationError.9"));
	}
}