<#assign propertyName="${classT?uncap_first}">
package ${packages};

<#if importClassName??>
    <#list importClassName as item>
import ${item};
    </#list>
</#if>

/**
 * <#if desc??>
 *	${desc}
 * </#if>
 * @author ${author}
 * @date ${.now}
 */
public interface ${simpleName} {

  <#--/**-->
  <#--* 插入-->
  <#--*-->
  <#--* @param ${propertyName} 数据模型-->
  <#--* @return ${classT} 数据模型-->
  <#--* @date ${.now}-->
  <#--*/-->
  <#--${classT} insert(${classT} ${propertyName});-->
  /**
   * 根据主键查询
   *
   * @param ${primaryKey.propertyName} 主键
   * @return ${classT} 数据模型
   * @date ${.now}
   */
  ${classT} selectByPrimaryKey(${primaryKey.dataType} ${primaryKey.propertyName});

  /**
   * 根据主键更新
   * @param ${propertyName} 数据模型
   * @date ${.now}
   */
  void updateByPrimaryKey(${classT} ${propertyName});

  /**
   * 根据主键删除
   * @param ${primaryKey.propertyName} 主键
   * @date ${.now}
   */
  void deleteByPrimaryKey(${primaryKey.dataType} ${primaryKey.propertyName});
  <#--/**-->
  <#--* 查询一行记录-->
  <#--* @param query 查询条件-->
  <#--* @return ${classT} 数据模型-->
  <#--* @date ${.now}-->
  <#--*/-->
  <#--${classT} selectOne(Query query);-->

  <#--/**-->
  <#--* 查询多条数据-->
  <#--* @param query 查询条件-->
  <#--* @return List<${classT}> 数据模型集合-->
  <#--* @date ${.now}-->
  <#--*/-->
  <#--List<${classT}> selectList(Query query);-->

  <#--/**-->
  <#--* 分页查询-->
  <#--*-->
  <#--* @param query 查询条件-->
  <#--* @return PagerModel<${classT}> 数据模型集合-->
  <#--* @date ${.now}-->
  <#--*/-->
  <#--PagerModel<${classT}> getPagerModel(Query query);-->

}
