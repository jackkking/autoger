<#if res.controllerPackageName != "">package ${res.controllerPackageName};</#if>

import com.fox.basic.controller.BaseController;
import com.fox.basic.exception.BusinessException;
import com.fox.basic.model.RestResult;
import com.fox.basic.util.ExcelUtiles;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import javax.validation.constraints.Size;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import javax.validation.constraints.Max;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import <#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo;
import <#if res.servicePackageName != "">${res.servicePackageName}.</#if>${res.objectName?cap_first}Service;


<#macro jspEl value>${r"${"}${value}}</#macro>

/**
     * ${res.objectName}Controller
     * ${tableInfo.remarks?trim}控制层
     * @date 2018-10-18 16:13:01
	 */
@RestController
@Api(value="${res.objectName}Controller",tags = "${tableInfo.domainObjectName}Vo",description ="${tableInfo.remarks?trim}")
@RequestMapping(value="/${res.objectName?uncap_first}s")
@Log4j2
@Validated
public class ${res.objectName?cap_first}Controller extends BaseController{
	@Autowired
	private ${res.objectName?cap_first}Service ${res.objectName?uncap_first}Service;

	/**
     * 查询数据列表-分页
     * @date ${create_time}
	 * @param customer ：${res.objectName?cap_first}Vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
 	@ApiOperation(value = "查询列表带分页-queryPageList", notes = "输入的参数未对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/queryPageList")
	public RestResult queryPageList(@ModelAttribute ${res.objectName?cap_first}Vo  ${res.objectName?uncap_first}Vo)throws BusinessException{

		return returnSuccess(${res.objectName?uncap_first}Service.queryPageList(${res.objectName?uncap_first}Vo));
	}

	/**
     * 按条件查询数据
     * @date ${create_time}
	 * @param customer ： ${res.objectName?cap_first}Vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
 	@ApiOperation(value = "按条件查询-list", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/list")
	public RestResult list(@RequestBody ${res.objectName?cap_first}Vo  ${res.objectName?uncap_first}Vo)throws BusinessException{

		return returnSuccess(${res.objectName?uncap_first}Service.findList(${res.objectName?uncap_first}Vo));
	}

	/**
     * 查询单个对象
     * @date ${create_time}
	 * @param customer ：ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
 	@ApiOperation(value = "查询单个对象-query", notes = "输入的参数为数据ID", httpMethod ="GET")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@GetMapping("/query")
	public RestResult query(@Size(max = 255) @RequestParam(value = "id")String id)throws BusinessException{

		return returnSuccess(${res.objectName?uncap_first}Service.get(id));
	}

	/**
     * 新增
     * @date ${create_time}
	 * @param body ：${res.objectName?cap_first}Vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @ApiOperation(value = "新增-add", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/add")
	public RestResult save(@ModelAttribute @Valid ${res.objectName?cap_first}Vo  ${res.objectName?uncap_first}Vo,BindingResult result)throws BusinessException{
		try {

			int flag=${res.objectName?uncap_first}Service.saveOrUpdate(${res.objectName?uncap_first}Vo);
			if(flag==0){
				return returnFailed();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return returnFailed();
		}
		return returnSuccess();
	}
    /**
     * 删除
     * @date ${create_time}
	 * @param body ：${res.objectName?cap_first}Vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @ApiOperation(value = "删除-delete", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/delete")
	public RestResult delete(@ModelAttribute ${res.objectName?cap_first}Vo ${res.objectName?uncap_first}Vo)throws BusinessException{
		try {

			int flag=${res.objectName?uncap_first}Service.deleteByEntity(${res.objectName?uncap_first}Vo);
			if(flag==0){
				return returnFailed();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return returnFailed();
		}
		return returnSuccess();
	}
     /**
     * 更新
     * @date ${create_time}
	 * @param body ：${res.objectName?cap_first}Vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @ApiOperation(value = "更新-update", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/update")
	public RestResult update(@ModelAttribute ${res.objectName?cap_first}Vo  ${res.objectName?uncap_first}Vo)throws BusinessException{
		try {

			int flag=${res.objectName?uncap_first}Service.saveOrUpdate(${res.objectName?uncap_first}Vo);
			if(flag==0){
				return returnFailed();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return returnFailed();
		}
		return returnSuccess();
	}
     /**
     * 导出EXCEL
     * @date ${create_time}
	 * @param body ：${res.objectName?cap_first}Vo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "导出", notes = "输入的参数为对象",httpMethod = "POST")
	@ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping(value="/export" )
	public void export(@RequestBody  ${res.objectName?cap_first}Vo  ${res.objectName?uncap_first}Vo,HttpServletResponse response) throws BusinessException {
		try {
			 List<${res.objectName?cap_first}Vo> list${res.objectName?cap_first}Vo=${res.objectName?uncap_first}Service.findList(${res.objectName?uncap_first}Vo);
             ExcelUtiles.exportExcel(list${res.objectName?cap_first}Vo, "${tableInfo.remarks?trim}", "${tableInfo.remarks?trim}", ${res.objectName?cap_first}Vo.class, "${tableInfo.remarks?trim}.xls", response);
	    } catch (Exception e) {
             e.printStackTrace();
             log.error(e.getMessage());

        }

    }
    /**
    * 导入EXCEL
    * @date ${create_time}
    * @param body
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
    @ApiOperation(value = "导入", notes = "文件", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
    @PostMapping(value="/uplod")
    public void text(@RequestParam("file")MultipartFile file , HttpServletRequest request) throws BusinessException {
        try {
             ImportParams importParams=new ImportParams();
	         List<Map<String,Object>> result=ExcelImportUtil.importExcel(file.getInputStream(),Map.class,importParams);
        } catch (Exception e) {
	         e.printStackTrace();
             log.error(e.getMessage());
        }
    }

}
