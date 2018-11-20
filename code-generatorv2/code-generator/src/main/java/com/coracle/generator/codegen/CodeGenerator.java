package com.coracle.generator.codegen;

import com.coracle.generator.config.Config;
import com.coracle.generator.main.GeneratorMain;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @Copyright:
 * @Company:
 * @author
 * @version 1.0
 * 设置模板生成到指定的路径下
 */
public class CodeGenerator {
	private static final String TEMPLATE_PATH = "/template";
	private Resource res;
	private Map<Object, Object> data;
	private String templateName = "";//模板名
	private String targetFileName = "";//生成的文件名

	public CodeGenerator(Resource res, Map<Object, Object> data) {
		this.res = res;
		this.data = data;
	}

	/**
	 * 获取对应的模板配置
	 * @return
	 */
	private Configuration getConfiguration() {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(super.getClass(), TEMPLATE_PATH);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}

	/**
	 * 指定模板进行生成
	 * @param fileType
	 */
	public void generateFile(FileType fileType) {
		if (fileType == FileType.ENTITY) {
			this.templateName = "entity.ftl";
			this.targetFileName = this.res.getPoFileName();
		}else if(fileType ==FileType.ENTITYVO){
			this.templateName = "vo.ftl";
			this.targetFileName=this.res.getVoFileName();
			this.data.put("packageName", this.res.getVoPackageName());
		}
		else if (fileType == FileType.DAO) {
			this.templateName = "dao.ftl";
			this.targetFileName = this.res.getDaoFileName();
			this.data.put("packageName", this.res.getDaoPackageName());
		}else if (fileType == FileType.MAPPER) {
			this.templateName = "mapper.ftl";
			this.targetFileName = this.res.getMapperFileName();
		}else if (fileType == FileType.SERVICE) {
			this.templateName = "service.ftl";
			this.targetFileName = this.res.getServiceFileName();
			this.data.put("packageName", this.res.getServicePackageName());
		}else if (fileType == FileType.SERVICEIMPL) {
			this.templateName = "serviceImpl.ftl";
			this.targetFileName = this.res.getServiceImplFileName();
			this.data.put("packageName", this.res.getServiceImplPackageName());
		}else if (fileType == FileType.CONTROLLER) {
			this.templateName = "controller.ftl";
			this.targetFileName = this.res.getControllerFileName();
			this.data.put("packageName", this.res.getControllerPackageName());
		}
		//输出文件
		outputFile();
	}

	/**
	 * 输出文件
	 */
	private void outputFile() {
		Template template;
		try {
			template = getConfiguration().getTemplate(this.templateName);
			FileOutputStream fos = new FileOutputStream(createFile(this.targetFileName));
			createFile("ceshi");
			Writer out = new OutputStreamWriter(fos, Config.getFileEncoding());
			template.process(this.data, out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建文件目录
	 * @param fullFileName
	 * @return
	 */
	private File createFile(String fullFileName) {
		File file = new File(GeneratorMain.basePath() + fullFileName);
		file.getParentFile().mkdirs();
		return file;
	}
}