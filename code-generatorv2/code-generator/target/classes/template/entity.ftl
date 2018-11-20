<#if res.poPackageName != "">package ${res.poPackageName};</#if>

import com.fox.basic.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
${import!""}
import org.hibernate.validator.constraints.*;

/**
 *
 * @Title: ${tableInfo.remarks!"${tableInfo.domainObjectName}"}实体类
 * @Description: 
 * @Copyright:
 * @Company:
 * @author
 * @date ${create_time}
 * @version 1.0
 *
 */
@NoArgsConstructor
@Data
@ApiModel(value = "${tableInfo.domainObjectName?cap_first}", description= "${tableInfo.domainObjectName?cap_first}")
public class ${tableInfo.domainObjectName ?cap_first} extends DataEntity<${tableInfo.domainObjectName ?cap_first}> {
	<#list tableInfo.allColumns as column>


	/**
	 * ${column.remarks!"${column.columnName}"}
	 */
	<#if column.javaType == "Date">
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		</#if>
		<#if column.javaType == "String">
	@Length(min=0, max=${column.length?c}, message="${column.columnName}长度必须介于 0 和 ${column.length?c} 之间")
		</#if>
		<#if column.isNullable?? && !column.isNullable>
	@NotBlank
		</#if>
	@ApiModelProperty(value = "${column.columnName}", name = "${column.remarks}")
	private ${column.javaType} ${column.javaProperty};
	</#list>

}