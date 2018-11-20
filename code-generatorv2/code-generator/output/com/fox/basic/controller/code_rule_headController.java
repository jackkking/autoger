package com.fox.basic.controller;

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
import com.fox.basic.vo.request.Code_rule_headVo;
import com.fox.basic.service.Code_rule_headService;



/**
     * code_rule_headController
     * 最後修改人控制层
     * @date 2018-10-18 16:13:01
	 */
@RestController
@Api(value="code_rule_headController",tags = "code_rule_headVo",description ="最後修改人")
@RequestMapping(value="/code_rule_heads")
@Log4j2
@Validated
public class Code_rule_headController extends BaseController{
	@Autowired
	private Code_rule_headService code_rule_headService;

	/**
     * 查询数据列表-分页
     * @date 2018-10-29 10:45:15
	 * @param customer ：Code_rule_headVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
 	@ApiOperation(value = "查询列表带分页-queryPageList", notes = "输入的参数未对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/queryPageList")
	public RestResult queryPageList(@ModelAttribute Code_rule_headVo  code_rule_headVo)throws BusinessException{
		return returnSuccess(code_rule_headService.queryPageList(code_rule_headVo));
	}

	/**
     * 按条件查询数据
     * @date 2018-10-29 10:45:15
	 * @param customer ： Code_rule_headVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
 	@ApiOperation(value = "按条件查询-list", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/list")
	public RestResult list(@RequestBody Code_rule_headVo  code_rule_headVo)throws BusinessException{

		return returnSuccess(code_rule_headService.findList(code_rule_headVo));
	}

	/**
     * 查询单个对象
     * @date 2018-10-29 10:45:15
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
		return returnSuccess(code_rule_headService.get(id));
	}

	/**
     * 新增
     * @date 2018-10-29 10:45:15
	 * @param body ：Code_rule_headVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @ApiOperation(value = "新增-add", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/add")
	public RestResult save(@ModelAttribute @Valid Code_rule_headVo  code_rule_headVo,BindingResult result)throws BusinessException{
		try {
			int flag=code_rule_headService.saveOrUpdate(code_rule_headVo);
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
     * @date 2018-10-29 10:45:15
	 * @param body ：Code_rule_headVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @ApiOperation(value = "删除-delete", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/delete")
	public RestResult delete(@ModelAttribute Code_rule_headVo code_rule_headVo)throws BusinessException{
		try {

			int flag=code_rule_headService.deleteByEntity(code_rule_headVo);
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
     * @date 2018-10-29 10:45:15
	 * @param body ：Code_rule_headVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @ApiOperation(value = "更新-update", notes = "输入的参数为对象", httpMethod = "POST")
    @ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping("/update")
	public RestResult update(@ModelAttribute Code_rule_headVo  code_rule_headVo)throws BusinessException{
		try {

			int flag=code_rule_headService.saveOrUpdate(code_rule_headVo);
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
     * @date 2018-10-29 10:45:15
	 * @param body ：Code_rule_headVo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "导出", notes = "输入的参数为对象",httpMethod = "POST")
	@ApiResponses({@ApiResponse(code=500,message = "system inner error-系统错误")})
	@PostMapping(value="/export" )
	public void export(@RequestBody  Code_rule_headVo  code_rule_headVo,HttpServletResponse response) throws BusinessException {
		try {
			 List<Code_rule_headVo> listCode_rule_headVo=code_rule_headService.findList(code_rule_headVo);
             ExcelUtiles.exportExcel(listCode_rule_headVo, "最後修改人", "最後修改人", Code_rule_headVo.class, "最後修改人.xls", response);
	    } catch (Exception e) {
             e.printStackTrace();
             log.error(e.getMessage());
        }
    }
	
    /**
    * 导入EXCEL
    * @date 2018-10-29 10:45:15
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
