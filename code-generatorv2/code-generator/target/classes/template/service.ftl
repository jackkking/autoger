<#if res.servicePackageName != "">package ${res.servicePackageName};</#if>

import com.fox.basic.service.BaseService;
<#if res.voPackageName != "">import ${res.voPackageName}.${res.objectName?cap_first}Vo;</#if>
/**
 *
 * @Title: ${tableInfo.remarks!"${tableInfo.domainObjectName}Vo"}业务逻辑访问接口
 * @Description: 
 * @Copyright:
 * @Company:
 * @author
 * @date ${create_time}
 * @version 1.0
 *
 */
public interface ${res.objectName?cap_first}Service extends BaseService<${res.objectName?cap_first}Vo> {

}