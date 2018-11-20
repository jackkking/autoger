package com.fox.basic.model;

import com.fox.basic.model.DataEntity;
import io.swagger.annotations.ApiModel;
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
@NoArgsConstructor
@Data
@ApiModel(value = "Code_rule_head", description= "Code_rule_head")
public class Code_rule_head extends DataEntity<Code_rule_head> {


	/**
	 * 自增ID
	 */
	@NotBlank
	@ApiModelProperty(value = "id", name = "自增ID")
	private Long id;


	/**
	 * ORG_ID
	 */
	@Length(min=0, max=100, message="org_id长度必须介于 0 和 100 之间")
	@ApiModelProperty(value = "org_id", name = "ORG_ID")
	private String orgid;


	/**
	 * 父项ID
	 */
	@ApiModelProperty(value = "father_id", name = "父项ID")
	private Long fatherid;


	/**
	 * 父项属性名称
	 */
	@Length(min=0, max=100, message="father_att长度必须介于 0 和 100 之间")
	@ApiModelProperty(value = "father_att", name = "父项属性名称")
	private String fatheratt;


	/**
	 * 属性名称
	 */
	@Length(min=0, max=100, message="child_att长度必须介于 0 和 100 之间")
	@ApiModelProperty(value = "child_att", name = "属性名称")
	private String childatt;


	/**
	 * 是否有效(默认为0,不删除，1为删除)
	 */
	@Length(min=0, max=1, message="is_del长度必须介于 0 和 1 之间")
	@ApiModelProperty(value = "is_del", name = "是否有效(默认为0,不删除，1为删除)")
	private String isdel;


	/**
	 * 創建時間
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "create_datetime", name = "創建時間")
	private Date createdatetime;


	/**
	 * 創建人
	 */
	@Length(min=0, max=50, message="create_userid长度必须介于 0 和 50 之间")
	@ApiModelProperty(value = "create_userid", name = "創建人")
	private String createuserid;


	/**
	 * 最後修改時間
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "last_update_datetime", name = "最後修改時間")
	private Date lastupdatedatetime;


	/**
	 * 最後修改人
	 */
	@Length(min=0, max=50, message="last_update_userid长度必须介于 0 和 50 之间")
	@ApiModelProperty(value = "last_update_userid", name = "最後修改人")
	private String lastupdateuserid;


	/**
	 * 备注
	 */
	@Length(min=0, max=1000, message="remark长度必须介于 0 和 1000 之间")
	@ApiModelProperty(value = "remark", name = "备注")
	private String remark;

}