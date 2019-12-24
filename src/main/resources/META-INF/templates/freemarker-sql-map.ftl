<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${namespace}">

  <sql id="commonsColumns">
    <trim suffixOverrides=",">
    <#list columnModels as being>
      a.`${being.name}` as '${being.propertyName}',
    </#list>
    </trim>
  </sql>

  <select id="selectByPrimaryKey" parameterType="${primaryKey.dataType}"
    resultType="${modelName}">
    SELECT
    <include refid="commonsColumns"/>
    FROM
    `${tableName}` as a
    WHERE
    a.`${primaryKey.name}` = ${r'#'}{${primaryKey.propertyName}}
  </select>

  <delete id="deleteByPrimaryKey" parameterType="${primaryKey.dataType}">
    DELETE FROM `${tableName}` WHERE `${primaryKey.name}` = ${r'#'}{${primaryKey.propertyName}}
  </delete>

  <insert id="insert" parameterType="${modelName}">
    <#if primaryKey.increment>
    <selectKey resultType="${primaryKey.dataType}"
      keyProperty="${primaryKey.propertyName}" order="AFTER">
      <if test="_databaseId == 'mysql'">
        SELECT LAST_INSERT_ID()
      </if>
    </selectKey>
    </#if>
    insert into `${tableName}`
    <trim prefix="(" suffix=")" suffixOverrides=",">
    <#list columnModels as being>
      <if test="${being.propertyName} != null">
        `${being.name}`,
      </if>
    </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
    <#list columnModels as being>
      <if test="${being.propertyName} != null">
        ${r'#'}{${being.propertyName}},
      </if>
    </#list>
    </trim>
  </insert>

  <insert id="insertBatch" parameterType="java.util.List">
    <#if primaryKey.increment>
    <selectKey resultType="${primaryKey.dataType}"
      keyProperty="${primaryKey.propertyName}" order="AFTER">
      <if test="_databaseId == 'mysql'">
        SELECT LAST_INSERT_ID()
      </if>
    </selectKey>
    </#if>
    insert into `${tableName}`
    <trim prefix="(" suffix=")" suffixOverrides=",">
    <#list columnModels as being>
      <#if primaryKey.increment>
        <#if primaryKey.propertyName != being.propertyName>
      `${being.name}`,
        </#if>
      <#else>
      `${being.name}`,
      </#if>
    </#list>
    </trim>
    values
    <foreach collection="list" item="item" index="index"
      separator=",">
      <trim prefix="(" suffix=")" suffixOverrides=",">
      <#list columnModels as being>
        <#if primaryKey.increment>
            <#if primaryKey.propertyName != being.propertyName>
        ${r'#'}{item.${being.propertyName}},
            </#if>
        <#else>
        ${r'#'}{item.${being.propertyName}},
        </#if>
      </#list>
      </trim>
    </foreach>
  </insert>

  <update id="updateByPrimaryKey" parameterType="${modelName}">
    update `${tableName}`
    <set>
    <#list columnModels as being>
      <if test="${being.propertyName} != null">
        `${being.name}` = ${r'#'}{${being.propertyName}},
      </if>
    </#list>
    </set>
    where `${primaryKey.name}` = ${r'#'}{${primaryKey.propertyName}}
  </update>

  <select id="selectOne" resultType="${modelName}"
    parameterType="${modelName}">
    SELECT
    <include refid="commonsColumns"/>
    FROM
    `${tableName}` as a
    <include refid="commonsWhereSql"/>
  </select>

  <select id="list${modelSimpleName}s" resultType="${modelName}"
    parameterType="${modelName}">
    SELECT
    <include refid="commonsColumns"/>
    FROM
    `${tableName}` as a
    <include refid="commonsWhereSql"/>
  </select>

  <sql id="commonsWhereSql">
    <trim prefix="where" prefixOverrides="and |or">
    <#list columnModels as being>
      <if test="${being.propertyName} != null">
        and a.`${being.name}` = ${r'#'}{${being.propertyName}}
      </if>
    </#list>
    <#if primaryKey??>
      <if test="idList != null">
        and a.`${primaryKey.name}` in
        <foreach item="item" index="index" collection="idList"
          open="(" separator="," close=")">
          ${r'#'}{item}
        </foreach>
      </if>
    </#if>
    </trim>
  </sql>

</mapper>