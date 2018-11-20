package com.coracle.generator.main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coracle.generator.database.ColumnInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.coracle.generator.codegen.CodeGenerator;
import com.coracle.generator.codegen.FileType;
import com.coracle.generator.codegen.Resource;
import com.coracle.generator.config.Config;
import com.coracle.generator.database.DBProcess;
import com.coracle.generator.database.TableInfo;
import com.coracle.generator.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 
 */
public class GeneratorMain {
	private static final String OUTPUT_PATH = "output";
	private static Logger logger = Logger.getLogger(GeneratorMain.class);

	static {
		preProcess();
	}

	public static void main(String[] args) {
		logger.error("@@@ 自动代码生成工具开始运行，稍等片刻，精彩即将呈现！！！");
		logger.info("### 正在加载校验配置文件");
		boolean isSuccess = Config.init();
		if (isSuccess) {
			logger.info("\t配置文件校验成功！");
		} else {
			logger.info("\t配置文件校验失败！");
			System.exit(0);
		}
		getTableInfo();
		
		logger.info("@@@ 自动代码生成工具运行结束，请到当前项目的output目录查看所生成的代码！！！");
	}
	
	/**
	 * 获取表的元数据信息，进行模板生成
	 */
	private static void getTableInfo() {
		logger.info("### 正在获取单表数据库表元信息");
		DBProcess process = new DBProcess();
		List<TableInfo> tableInfos = process.parseTableInfo();
		logger.info("\t数据库单表元信息解析成功!");
		
		logger.info("### 开始单表代码生成");
		for (TableInfo tableInfo : tableInfos) {
			logger.info("### 数据库表[" + tableInfo.getTableName() + "]");
			if (tableInfo.getAllColumns().isEmpty()) {
				logger.info("\t表未找到，请确认表名填写是否正确");
				logger.info("\t代码生成失败");
				break;
			}
			Resource res = new Resource(tableInfo.getTableName(),tableInfo.getDomainObjectName(), Config.getPackagesTypeConfig().getPackagesType());
			Map<Object,Object> data = new HashMap<Object,Object>();
			data.put("res", res);
			data.put("create_time", StringUtil.getCurrentTime());
			data.put("tableInfo", tableInfo);
			data.put("import", StringUtil.getImportClass(tableInfo.getEntityColumns()));
			
			CodeGenerator cg = new CodeGenerator(res, data);
			cg.generateFile(FileType.ENTITY);
			logger.info("\tEntity类代码生成成功！");
			cg.generateFile(FileType.ENTITYVO);
			logger.info("\tVO类代码生成成功！");
			cg.generateFile(FileType.DAO);
			logger.info("\tDao接口类代码生成成功！");
			
			cg.generateFile(FileType.MAPPER);
			logger.info("\tMapper映射文件生成成功！");
			
			cg.generateFile(FileType.SERVICE);
			logger.info("\tService接口类代码生成成功！");
			
			cg.generateFile(FileType.SERVICEIMPL);
			logger.info("\tService实现类代码生成成功！");
			
			if(!tableInfo.getIsView()){
				cg.generateFile(FileType.CONTROLLER);
				logger.info("\tController类代码生成成功！");
			}
		}
		getTableExcel();
	}
	
	/**
	 * 生成EXCEL模板
	 */
	private static void getTableExcel() {
		DBProcess process = new DBProcess();
		List<TableInfo> tableInfos = process.parseTableInfo();
		List<ColumnInfo > allColumns =new ArrayList<ColumnInfo>();
		for (TableInfo tb:tableInfos){
			   //获取表头
			  allColumns=tb.getAllColumns();
		}
		if(StringUtils.isNotBlank(tableInfos.get(0).getRemarks())){
			createExecl(allColumns,tableInfos.get(0).getRemarks(),tableInfos.get(0).getTableName());
		}else{
			createExecl(allColumns,"table_Vo",tableInfos.get(0).getTableName());
		}
	}
	
	public static void createExecl(List<ColumnInfo > allColumns,String tableName,String excelName){
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet=workbook.createSheet(tableName);
		/*sheet.setColumnWidth((short) 3, 20 * 256);*///设置单元格宽度
		createHeadRows(workbook,sheet,allColumns);
		try {

			//ByteArrayOutputStream out = new ByteArrayOutputStream();
			String     ty=GeneratorMain.basePath() + excelName+".xls";
			FileOutputStream out= new FileOutputStream(ty);
			workbook.write(out);
            out.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void createHeadRows(HSSFWorkbook workbook,HSSFSheet sheet,List<ColumnInfo > allColumns){
		HSSFRow contentRow=sheet.createRow(0);
		for(int i=0;i<allColumns.size();i++){
			ColumnInfo   cn=allColumns.get(i);
			HSSFCell contentCell=contentRow.createCell(i);
			contentCell.setCellValue(cn.getRemarks());
		}

	}
	/**
	 * 先删除之前的目录路径，然后再进行创建
	 */
	private static void preProcess() {
		String path = basePath();
		deleteFileDirectory(path);
		File file = new File(path);
		file.mkdir();
	}
	
	/**
	 * 构建当前工程路径
	 */
	public static String basePath() {
		StringBuilder sb = new StringBuilder();

		sb.append(System.getProperty("user.dir"));
		sb.append(Resource.FS);
		sb.append(OUTPUT_PATH);
		sb.append(Resource.FS);
		return sb.toString();
	}


	/**
	 * 删除文件目录
	 * @param dest
	 */
	private static void deleteFileDirectory(String dest) {
		File f = new File(dest);
		if (f.exists()) {
			if (f.isDirectory()) {
				File[] fs = f.listFiles();
				if ((fs != null) && (fs.length > 0)) {
					File[] arrayOfFile1;
					int j = (arrayOfFile1 = fs).length;
					for (int i = 0; i < j; ++i) {
						File file = arrayOfFile1[i];
						deleteFileDirectory(file.getAbsolutePath());
					}
				}
			}
			f.delete();
		}
	}
}