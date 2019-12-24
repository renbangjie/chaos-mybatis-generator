<#assign propertyName="${classT?uncap_first}">
<#assign mapperName="${propertyName + 'Mapper'}">
package ${packages};

<#if importClassName??>
  <#list importClassName as item>
import ${item};
  </#list>
</#if>

/**
* <#if desc??>
*  ${desc}
* </#if>
* @author ${author}
* @date ${.now}
*/
@Service("${propertyName + 'Service'}")
public class ${simpleName} implements ${interfaceClassName} {

<#--  /**-->
<#--	 * 向数据库中新增一条记录-->
<#--   *-->
<#--	 * @param ${propertyName} 数据模型-->
<#--	 * @return ${classT} 数据模型-->
<#--   * @date ${.now}-->
<#--	 */-->
<#--	@Override-->
<#--	public ${classT} insert(${classT} ${propertyName}) {-->
<#--		${mapperName}.insert(${propertyName});-->
<#--		return ${propertyName};-->
<#--	}-->

  /**
	 * 根据主键获取该主键对应的数据详情
   *
	 * @param ${primaryKey.propertyName} 主键
	 * @return ${classT} 数据模型
   * @date ${.now}
	 */
	@Override
	public ${classT} selectByPrimaryKey(${primaryKey.dataType} ${primaryKey.propertyName}) {
		return ${mapperName}.selectByPrimaryKey(${primaryKey.propertyName});
	}

	/**
	 * 更新数据库中的一条记录
   *
	 * @param ${propertyName} 数据模型
   * @date ${.now}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateByPrimaryKey(${classT} ${propertyName}) {
		if (${mapperName}.updateByPrimaryKey(${propertyName}) != 1) {
			throw new RuntimeException("更新数据异常");
		}
	}

	/**
	 * 根据主键从数据库中删除一条记录
   *
	 * @param ${primaryKey.propertyName} 主键
   * @date ${.now}
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteByPrimaryKey(${primaryKey.dataType} ${primaryKey.propertyName}) {
		if (${mapperName}.deleteByPrimaryKey(${primaryKey.propertyName}) != 1) {
			throw new RuntimeException("删除数据异常");
		}
	}

<#--	/**-->
<#--	 * 查询单个对象-->
<#--   *-->
<#--	 * @param ${propertyName} 查询条件-->
<#--	 * @return ${classT} 数据模型-->
<#--   * @date ${.now}-->
<#--	*/-->
<#--	@Override-->
<#--	public ${classT} selectOne(${classT} ${propertyName}) {-->
<#--		return ${mapperName}.selectOne(${propertyName});-->
<#--	}-->

<#--	/**-->
<#--	 * 查询多个对象-->
<#--   *-->
<#--	 * @param ${propertyName} 查询条件-->
<#--	 * @return List<${classT}> 数据模型集合-->
<#--   * @date ${.now}-->
<#--	*/-->
<#--	@Override-->
<#--	public List<${classT}> list${classT}s(${classT} ${propertyName}) {-->
<#--  	return ${mapperName}.list${classT}s(${propertyName});-->
<#--  }-->

  <#--/**-->
   <#--* 分页查询-->
   <#--*-->
   <#--* @param query-->
   <#--* @return PagerModel<${classT}>-->
   <#--* @date ${.now}-->
   <#--*/-->
  <#--@Override-->
  <#--public PagerModel<${classT}> getPagerModel(Query query) {-->
  	<#--return PagerUtil.getPagerModel(new PagerService<${classT}>() {-->
			<#--@Override-->
			<#--public int selectCount(Query query) {-->
				<#--return ${mapperName}.selectCount(query);-->
			<#--}-->
			<#--@Override-->
			<#--public List<${classT}> selectList(Query query) {-->
				<#--return ${mapperName}.selectList(query);-->
			<#--}-->
    <#--}, query);-->
  <#--}-->

<#if fieldModels??>
	<#list fieldModels as being>
		<#if being.desc??>
	/**
	* ${being.desc}
	*/
		</#if>
	${being.annotation}
	${being.accessModifier} ${being.typeName} ${being.name};

	</#list>
</#if>

}
