<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="<#if res.daoPackageName != "">${res.daoPackageName}.</#if>${res.objectName?cap_first}Mapper">
	<!-- 结果集映射 -->
	<resultMap id="${res.objectName?uncap_first}ResultMap" type="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
	<#list tableInfo.entityColumns as column>
		<#if column.isPrimaryKey>
		<id column="${column.columnName}" property="${column.javaProperty}" jdbcType="${column.jbdcTypeStr}"/>
		<#else>
		<result column="${column.columnName}" property="${column.javaProperty}" jdbcType="${column.jbdcTypeStr}"/>
		</#if>	
	</#list>
	</resultMap>



	
	<!-- 列定义 -->
	<sql id="${res.objectName?uncap_first}Columns">
  	  <#list tableInfo.entityColumns as column>
	  t.${column.columnName}<#if column_has_next>,</#if>
  	  </#list>
  	</sql>
	
	<!-- 查询条件SQL -->
	<sql id="whereColumnBySelect">
		<trim prefix="WHERE" prefixOverrides="AND | OR">
			<#list tableInfo.queryColumns as column>
			<#if column.javaType == "Date">
			<#--<if test="start${column.javaProperty?cap_first} != null and start${column.javaProperty?cap_first} !=''">-->
				<#--AND <![CDATA[ t.${column.columnName} >= TO_DATE(${"#{"+"start"+column.javaProperty?cap_first+"}"}||' 00:00:00','YYYY-MM-DD HH24:MI:SS')]]>-->
			<#--</if>-->
			<#--<if test="end${column.javaProperty?cap_first} != null and end${column.javaProperty?cap_first} !=''">-->
				<#--AND <![CDATA[ t.${column.columnName} <= TO_DATE(${"#{"+"end"+column.javaProperty?cap_first+"}"}||' 23:59:59','YYYY-MM-DD HH24:MI:SS')]]>-->
			<#--</if>-->
			<#else>
			<if test="${column.javaProperty} != null and ${column.javaProperty} !=''">
				AND t.${column.columnName} = ${"#{"+column.javaProperty+"}"}
			</if>
			</#if>
			</#list>
		</trim>
	</sql>
	
	<#if tableInfo.isView?? && !tableInfo.isView>
	<!-- 修改字段SQL -->
	<sql id="whereColumnByUpdate">
		<trim prefix="SET" suffixOverrides=",">
			<#list tableInfo.baseColumns as column>
			<if test="${column.javaProperty} != null">
				t.${column.columnName} = ${"#{"+column.javaProperty+"}"},
			</if>
			</#list>
		</trim>
	</sql>
	
	<!-- 根据主键ID获取数据 -->
	<select id="get" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo" resultMap="${res.objectName?uncap_first}ResultMap">
		SELECT <include refid="${res.objectName?uncap_first}Columns" /> 
		FROM ${tableInfo.tableName} t 
		WHERE t.${tableInfo.primaryKeyColumns[0].columnName} = ${"#{"+tableInfo.primaryKeyColumns[0].javaProperty+"}"}
	</select>
	</#if>
	
	<#macro jspEl value>${r"${"}${value}}</#macro>
	<!-- 查询列表可以根据分页进行查询 -->
	<select id="findList" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo" resultMap="${res.objectName?uncap_first}ResultMap">
		SELECT
		<include refid="${res.objectName?uncap_first}Columns" /> 
		FROM ${tableInfo.tableName} t 
		<include refid="whereColumnBySelect" />
		   ORDER BY <#list tableInfo.primaryKeyColumns as primaryKey>t.${primaryKey.columnName}<#if primaryKey_has_next>,</#if></#list> DESC
	</select>
	
	<!-- 查询所有列表 -->
	<select id="findAllList" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo" resultMap="${res.objectName?uncap_first}ResultMap">
		SELECT
		<include refid="${res.objectName?uncap_first}Columns" /> 
		FROM ${tableInfo.tableName} t 
		<include refid="whereColumnBySelect" />
		<#if (tableInfo.primaryKeyColumns)??>
		ORDER BY <#list tableInfo.primaryKeyColumns as primaryKey>t.${primaryKey.columnName}<#if primaryKey_has_next>,</#if></#list> DESC
		</#if>
	</select>
	
	<!-- 根据条件查询对象 -->
	<select id="getByEntity" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo" resultMap="${res.objectName?uncap_first}ResultMap">
		SELECT <include refid="${res.objectName?uncap_first}Columns" /> 
		FROM ${tableInfo.tableName} t 
		<include refid="whereColumnBySelect"></include>
	</select>
	
	<#if tableInfo.isView?? && !tableInfo.isView>
	<!-- 新增并设置主键ID至参数对象 -->
	<insert id="insert" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
		<selectKey keyProperty="${tableInfo.primaryKeyColumns[0].javaProperty}" resultType="${tableInfo.primaryKeyColumns[0].fullJavaType}" order="BEFORE">
            select replace(uuid(),'-','') from dual
        </selectKey>
		INSERT INTO ${tableInfo.tableName}
			(<#list tableInfo.entityColumns as column>${column.columnName}<#if column_has_next>, </#if></#list>)
		VALUES
			(<#list tableInfo.entityColumns as column>${"#{"+column.javaProperty+"}"}<#if column_has_next>, </#if></#list>)
	</insert>
	
	<#macro mapperEl value>${r"#{"}${value}</#macro>
	<!-- 新增并设置主键ID判断哪些列不为空时，则进行插入 -->
	<insert id="insertSelective" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
		<selectKey keyProperty="${tableInfo.primaryKeyColumns[0].javaProperty}" resultType="${tableInfo.primaryKeyColumns[0].fullJavaType}" order="BEFORE">
            select replace(uuid(),'-','') from dual
        </selectKey>
	    INSERT INTO ${tableInfo.tableName}
	    <trim prefix="(" suffix=")" suffixOverrides="," >
	      <#list tableInfo.entityColumns as column>
			<if test="${column.javaProperty} != null">
				${column.columnName},
			</if>
		  </#list>
	    </trim>
	    <trim prefix="values (" suffix=")" suffixOverrides="," >
		  <#list tableInfo.entityColumns as column>
		  <if test="${column.javaProperty} != null">
				<@mapperEl column.javaProperty/>,jdbcType=${column.jbdcTypeStr}},
		  </if>
  		  </#list>
	    </trim>
  	</insert>
  	
  	<!-- 根据主键ID进行修改 -->
  	<update id="update" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
		UPDATE ${tableInfo.tableName} t 
		<include refid="whereColumnByUpdate"></include>
		<where>
			t.${tableInfo.primaryKeyColumns[0].columnName} = <@mapperEl tableInfo.primaryKeyColumns[0].javaProperty/>,jdbcType=${tableInfo.primaryKeyColumns[0].jbdcTypeStr}}
		</where>
	</update>
	
	<!-- 根据条件进行修改 -->
	<update id="updateByCondition" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
		UPDATE ${tableInfo.tableName} t 
		<include refid="whereColumnByUpdate"></include>
		<where><@jspEl "whereSqlParam"/></where>
	</update>

	<!-- 根据主键ID进行逻辑删除 -->
	<update id="delete" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
		UPDATE ${tableInfo.tableName} t SET
		t.IS_DELETED = 1
		WHERE t.${tableInfo.primaryKeyColumns[0].columnName} = <@mapperEl tableInfo.primaryKeyColumns[0].javaProperty/>,jdbcType=${tableInfo.primaryKeyColumns[0].jbdcTypeStr}}
	</update>

	<!-- 根据主键ID进行批量逻辑删除 -->
	<update id="batchDelete" parameterType="java.util.List">
		UPDATE ${tableInfo.tableName} t SET
		t.IS_DELETED = 1
		WHERE t.${tableInfo.primaryKeyColumns[0].columnName} IN
		<foreach item="item" collection="list" index="index" open="("
			separator="," close=")">
			<@mapperEl "item"/>}
		</foreach>
	</update>
	
	<!-- 根据主键ID进行物理删除 -->
	<delete id="remove" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
		DELETE t.* FROM ${tableInfo.tableName} t
		WHERE t.${tableInfo.primaryKeyColumns[0].columnName} = <@mapperEl tableInfo.primaryKeyColumns[0].javaProperty/>,jdbcType=${tableInfo.primaryKeyColumns[0].jbdcTypeStr}}
	</delete>
	
	<!-- 根据主键ID进行批量物理删除 -->
	<delete id="batchRemove" parameterType="java.util.List">
		DELETE t.* FROM ${tableInfo.tableName} t
		WHERE t.${tableInfo.primaryKeyColumns[0].columnName} IN
		<foreach item="item" collection="list" index="index" open="("
			separator="," close=")">
			<@mapperEl "item"/>}
		</foreach>
	</delete>

	
	<!-- 根据条件进行逻辑删除 -->
	<update id="deleteByEntity" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
		UPDATE ${tableInfo.tableName} t SET
			t.IS_DELETED = 1
		<include refid="whereColumnBySelect"/>
	</update>

	<delete id="removeByEntity" parameterType="<#if res.voPackageName != "">${res.voPackageName}.</#if>${res.objectName?cap_first}Vo">
        DELETE t.* FROM meet_subsidiary t
        <include refid="whereColumnBySelect"></include>
    </delete>
	</#if>
</mapper>