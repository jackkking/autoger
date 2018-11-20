package com.fox.basic.vo.request;
import com.fox.basic.model.DataEntity;
import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.*;

/**
 *
 * @Title: 最後修改人实体类
 * @Description:
 * @Copyright:
 * @Company:
 * @author
 * @date 2018-10-29 10:45:15
 * @version 1.0
 *
 */
@Data
public class Code_rule_headVo extends DataEntity<Code_rule_headVo> implements  java.io.Serializable {
    private static final long serialVersionUID = 1L;


	/**
	 * 自增ID
	 */
        @Excel(name = "自增ID")

    @ApiModelProperty(value = "id", name = "自增ID")
	private Long id;


	/**
	 * ORG_ID
	 */
        @Excel(name = "ORG_ID")
    @ApiModelProperty(value = "org_id", name = "ORG_ID")
	private String orgid;


	/**
	 * 父项ID
	 */
        @Excel(name = "父项ID")
    @ApiModelProperty(value = "father_id", name = "父项ID")
	private Long fatherid;


	/**
	 * 父项属性名称
	 */
        @Excel(name = "父项属性名称")
    @ApiModelProperty(value = "father_att", name = "父项属性名称")
	private String fatheratt;


	/**
	 * 属性名称
	 */
        @Excel(name = "属性名称")
    @ApiModelProperty(value = "child_att", name = "属性名称")
	private String childatt;


	/**
	 * 是否有效(默认为0,不删除，1为删除)
	 */
        @Excel(name = "是否有效(默认为0,不删除，1为删除)")
    @ApiModelProperty(value = "is_del", name = "是否有效(默认为0,不删除，1为删除)")
	private String isdel;


	/**
	 * 創建時間
	 */
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @Excel(name = "創建時間", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    @ApiModelProperty(value = "create_datetime", name = "創建時間")
	private Date createdatetime;


	/**
	 * 創建人
	 */
        @Excel(name = "創建人")
    @ApiModelProperty(value = "create_userid", name = "創建人")
	private String createuserid;


	/**
	 * 最後修改時間
	 */
	    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        @Excel(name = "最後修改時間", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    @ApiModelProperty(value = "last_update_datetime", name = "最後修改時間")
	private Date lastupdatedatetime;


	/**
	 * 最後修改人
	 */
        @Excel(name = "最後修改人")
    @ApiModelProperty(value = "last_update_userid", name = "最後修改人")
	private String lastupdateuserid;


	/**
	 * 备注
	 */
        @Excel(name = "备注")
    @ApiModelProperty(value = "remark", name = "备注")
	private String remark;

}