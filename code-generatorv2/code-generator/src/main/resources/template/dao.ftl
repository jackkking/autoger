<#if res.daoPackageName != "">package ${res.daoPackageName};</#if>

import org.apache.ibatis.annotations.Mapper;
import com.fox.basic.mapper.MapperBase;
<#if res.voPackageName != "">import ${res.voPackageName}.${tableInfo.domainObjectName?cap_first}Vo;</#if>

/**
 *
 * @Title: ${tableInfo.remarks!"${tableInfo.domainObjectName}Vo"}数据库访问接口
 * @Description: 
 * @Copyright:
 * @Company:
 * @author
 * @date ${create_time}
 * @version 1.0
 *
 */
@Mapper
public interface ${tableInfo.domainObjectName?cap_first}Mapper extends MapperBase<${tableInfo.domainObjectName?cap_first}Vo> {
	
}