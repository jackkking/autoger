<#if res.serviceImplPackageName != "">package ${res.serviceImplPackageName};</#if>

import com.fox.basic.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import <#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo;
import <#if res.daoPackageName != "">${res.daoPackageName}.</#if>${res.objectName?cap_first}Mapper;
import <#if res.servicePackageName != "">${res.servicePackageName}.</#if>${res.objectName?cap_first}Service;

/**
 *
 * @Title: ${tableInfo.remarks!"${tableInfo.domainObjectName}Vo"}业务逻辑实现类
 * @Description: 
 * @Copyright:
 * @Company:
 * @author
 * @date ${create_time}
 * @version 1.0
 *
 */
@Service("${res.objectName?uncap_first}Service")
public class ${res.objectName?cap_first}ServiceImpl extends BaseServiceImpl<${res.objectName?cap_first}Mapper, ${res.objectName?cap_first}Vo> implements ${res.objectName?cap_first}Service{

}
