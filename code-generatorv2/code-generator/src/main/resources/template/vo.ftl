<#if res.voPackageName != "">package ${res.voPackageName};</#if>
import com.fox.basic.model.DataEntity;
import cn.afterturn.easypoi.excel.annotation.Excel;
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
@Data
public class ${tableInfo.domainObjectName ?cap_first}Vo extends DataEntity<${tableInfo.domainObjectName ?cap_first}Vo> implements  java.io.Serializable {
    private static final long serialVersionUID = 1L;
<#list tableInfo.allColumns as column>


	/**
	 * ${column.remarks!"${column.columnName}"}
	 */
    <#if column.javaType == "Date">
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @Excel(name = "${column.remarks}", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    </#if>
    <#if column.javaType != "Date">
        @Excel(name = "${column.remarks}")
    </#if>
    <#if column.isNullable?? && !column.isNullable>

    </#if>
    @ApiModelProperty(value = "${column.columnName}", name = "${column.remarks}")
	private ${column.javaType} ${column.javaProperty};
</#list>

}