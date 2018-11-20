package com.coracle.generator.codegen;

import com.coracle.generator.config.Config;
import com.coracle.generator.util.StringUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 
 */
public class Resource {
	public static final String FS = System.getProperty("file.separator");
	public static final String PS = ".";
	public static final String PACKAGES_TYPE_MODULE = "module";// 模块划分
	public static final String PACKAGES_TYPE_CATEGORY = "category";// 工程划分
	public static final String PACKAGES_TYPE_CUSTOM = "customization";// 自定义
	private static final String PACKAGE_PREFIX = "com.fox.";// 包前缀
	private static final String DAO_PACKAGE = "mapper";
	private static final String MAPPER_PACKAGE = "mapper";
	private static final String SERVICE_PACKAGE = "service";
	private static final String SERVICE_IMPL_PACKAGE = "service.impl";
	private static final String CONTROLLER_PACKAGE = "controller";
	private static final String ENTITY_PACKAGE = "model";
	private static final String ENTITYVO_PACKAGE = "vo.request";

	private static final String DAO_SUFFIX = "Mapper.java";
	private static final String PO_SUFFIX = ".java";
	private static final String VO_SUFFIX = "Vo.java";
//	private static final String SERVICE_PREFIX = "I";
	private static final String SERVICE_SUFFIX = "Service.java";
	private static final String SERVICE_IMPL_SUFFIX = "ServiceImpl.java";
	private static final String CONTROLLER_SUFFIX = "Controller.java";
	private static final String MAPPER_SUFFIX = "Mapper.xml";

	protected String packagesType;
	protected String fileEncoding;
	protected String projectName;
	protected String moduleName;
	protected String daoPackageName;
	protected String poPackageName;

	public String getVoPackageName() {
		return voPackageName;
	}

	protected String voPackageName;
	protected String mapperPackageName;
	protected String servicePackageName;
	protected String serviceImplPackageName;
	protected String controllerPackageName;
	protected String jspPackageName;
	protected String objectName;
	protected String daoFileName;
	protected String poFileName;

	public String getVoFileName() {
		return voFileName;
	}

	public void setVoFileName(String voFileName) {
		this.voFileName = voFileName;
	}

	protected String voFileName;
	protected String serviceFileName;
	protected String serviceImplFileName;
	protected String controllerFileName;
	protected String mapperFileName;
	protected String tableName;

	public Resource(String tableName,String objectName, String packagesType) {
		this.tableName = tableName;
		this.objectName = objectName;
		this.packagesType = packagesType;
		initialize();
	}

	private void initialize() {
		this.fileEncoding = Config.getFileEncoding();
		this.projectName = Config.getPackagesTypeConfig().getProject();

		if ("module".equals(this.packagesType)) {
			initModule();
		}

		if ("category".equals(this.packagesType)) {
			initCategory();
		}

		if ("customization".equals(this.packagesType))
			initCustom();
	}

	/**
	 * 按模块划分
	 */
	private void initModule() {
		String module = Config.getModuleName(tableName);
		if(StringUtils.isNotBlank(module)){
			this.moduleName = module;
		}else{
			this.moduleName = "";
		}
		this.daoPackageName = genModuleLayerPackage(DAO_PACKAGE,null);
		this.poPackageName = genModuleLayerPackage(ENTITY_PACKAGE,null);
		//新加
		this.voPackageName=genModuleLayerPackage(ENTITYVO_PACKAGE,null);
		this.servicePackageName = genModuleLayerPackage(SERVICE_PACKAGE,null);
		this.serviceImplPackageName = genModuleLayerPackage(SERVICE_IMPL_PACKAGE,null);
		this.controllerPackageName = genModuleLayerPackage(CONTROLLER_PACKAGE,null);
		this.mapperPackageName = genModuleLayerPackage(MAPPER_PACKAGE,null);

		this.daoFileName = genFullFileName(this.daoPackageName, null,
				DAO_SUFFIX);
		this.poFileName = genFullFileName(this.poPackageName, null, PO_SUFFIX);
		this.voFileName=genFullFileName(this.voPackageName,null,VO_SUFFIX);
		this.serviceFileName = genFullFileName(this.servicePackageName,
				null, SERVICE_SUFFIX);
		this.serviceImplFileName = genFullFileName(this.serviceImplPackageName,
				null, SERVICE_IMPL_SUFFIX);
		this.controllerFileName = genFullFileName(this.controllerPackageName, null,
				CONTROLLER_SUFFIX);
		this.mapperFileName = genFullFileName(this.mapperPackageName, null,
				MAPPER_SUFFIX);
	}

	/**
	 * 按工程划分
	 */
	private void initCategory() {
		this.daoPackageName = genCategoryPackage(DAO_PACKAGE);
		this.poPackageName = genCategoryPackage(ENTITY_PACKAGE);
		this.voPackageName=genCategoryPackage(ENTITY_PACKAGE);
		this.servicePackageName = genCategoryPackage(SERVICE_PACKAGE);
		this.serviceImplPackageName = genCategoryPackage(SERVICE_IMPL_PACKAGE);
		this.controllerPackageName = genCategoryPackage(CONTROLLER_PACKAGE);
		this.mapperPackageName = genCategoryPackage(MAPPER_PACKAGE);

		this.daoFileName = genFullFileName(this.daoPackageName, null,
				DAO_SUFFIX);
		this.poFileName = genFullFileName(this.poPackageName, null, PO_SUFFIX);
		this.voFileName=genFullFileName(this.voPackageName,null,VO_SUFFIX);
		this.serviceFileName = genFullFileName(this.servicePackageName,
				null, SERVICE_SUFFIX);
		this.serviceImplFileName = genFullFileName(this.serviceImplPackageName,
				null, SERVICE_IMPL_SUFFIX);
		this.controllerPackageName = genFullFileName(this.controllerPackageName, null,
				CONTROLLER_SUFFIX);
		this.mapperFileName = genFullFileName(this.mapperPackageName, null,
				MAPPER_SUFFIX);
	}

	/**
	 * 读取配置文件的自定义包名
	 */
	private void initCustom() {
		this.moduleName = StringUtil.firstCharToLowerCase(this.objectName);

		this.daoPackageName = Config.getPackagesTypeConfig()
				.getDaoPackageName();
		this.poPackageName = Config.getPackagesTypeConfig().getPoPackageName();
		this.voPackageName=Config.getPackagesTypeConfig().getVoPackageName();
		this.servicePackageName = Config.getPackagesTypeConfig()
				.getServicePackageName();
		this.serviceImplPackageName = Config.getPackagesTypeConfig()
				.getServiceImplPackageName();
		this.controllerPackageName = Config.getPackagesTypeConfig()
				.getControllerPackageName();
		this.mapperPackageName = Config.getPackagesTypeConfig()
				.getMapperPackageName();
		this.jspPackageName = Config.getPackagesTypeConfig()
				.getJspPackageName();

		this.daoFileName = genFullFileName(this.daoPackageName, null,
				DAO_SUFFIX);
		this.poFileName = genFullFileName(this.poPackageName, null, PO_SUFFIX);
		this.voFileName=genFullFileName(this.voPackageName,null,PO_SUFFIX);
		this.serviceFileName = genFullFileName(this.servicePackageName,
				null, SERVICE_SUFFIX);
		this.serviceImplFileName = genFullFileName(this.serviceImplPackageName,
				null, SERVICE_IMPL_SUFFIX);
		this.controllerPackageName = genFullFileName(this.controllerPackageName, null,
				CONTROLLER_SUFFIX);
		this.mapperFileName = genFullFileName(this.mapperPackageName, null,
				MAPPER_SUFFIX);
	}

	/**
	 * 按模块划分的包名
	 * 
	 * @param layer
	 * @return
	 */
	private String genModuleLayerPackage(String layer,String moduleSuffix) {
		StringBuilder sb = new StringBuilder();

		sb.append(PACKAGE_PREFIX);
		sb.append(this.projectName);
		sb.append(".");
		sb.append(layer);
		if(StringUtils.isNotBlank(moduleName)){
			sb.append(".");
			sb.append(this.moduleName);
		}
		if(StringUtils.isNotBlank(moduleSuffix)){
			sb.append(".");
			sb.append(moduleSuffix);
		}
		return sb.toString();
	}

	/**
	 * 按工程划分的包名
	 * 
	 * @param layer
	 * @return
	 */
	private String genCategoryPackage(String layer) {
		StringBuilder sb = new StringBuilder();

		sb.append(PACKAGE_PREFIX);
		sb.append(this.projectName);
		sb.append(".");
		sb.append(layer);

		return sb.toString();
	}

	/**
	 * 获取java的全路径
	 * 
	 * @param packageName
	 * @param prefix
	 *            前缀
	 * @param suffix
	 *            后缀
	 * @return
	 */
	private String genFullFileName(String packageName, String prefix,
			String suffix) {
		String path = packageName.replace(".", FS);
		StringBuilder sb = new StringBuilder(path);

		sb.append(FS);
		if (!(StringUtils.isBlank(prefix))) {
			sb.append(prefix);
		}
		sb.append(this.objectName);
		if (!(StringUtils.isBlank(suffix))) {
			sb.append(suffix);
		}

		return sb.toString();
	}


	public String getProjectName() {
		return this.projectName;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public String getDaoPackageName() {
		return this.daoPackageName;
	}

	public String getPoPackageName() {
		return this.poPackageName;
	}

	public String getServicePackageName() {
		return this.servicePackageName;
	}

	public String getControllerPackageName() {
		return controllerPackageName;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public String getServiceImplPackageName() {
		return this.serviceImplPackageName;
	}

	public String getDaoFileName() {
		return this.daoFileName;
	}

	public String getPoFileName() {
		return this.poFileName;
	}

	public String getServiceFileName() {
		return this.serviceFileName;
	}

	public String getServiceImplFileName() {
		return this.serviceImplFileName;
	}


	public String getMapperFileName() {
		return this.mapperFileName;
	}

	public String getFileEncoding() {
		return this.fileEncoding;
	}

	public String getMapperPackageName() {
		return this.mapperPackageName;
	}

	public String getJspPackageName() {
		return this.jspPackageName;
	}

	public String getPackagesType() {
		return this.packagesType;
	}

	public String getControllerFileName() {
		return controllerFileName;
	}

	public String getTableName() {
		return tableName;
	}
	public void setVoPackageName(String voPackageName) { this.voPackageName = voPackageName; }
}