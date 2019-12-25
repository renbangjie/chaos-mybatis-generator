<#assign propertyName="${classT?uncap_first}">
package ${packages};

<#list importClassName as item>
import ${item};
</#list>

/**
 * <#if desc??>
 *  ${desc}
 * </#if>
 * @author ${author}
 * @date ${.now}
 */
@Mapper
public interface ${simpleName} {

  /**
   * 根据主键查询
   *
   * @param ${primaryKey.propertyName} 主键
   * @return ${classT} 数据模型
   * @date ${.now}
   */
  ${classT} selectByPrimaryKey(${primaryKey.dataType} ${primaryKey.propertyName});

  /**
   * 插入新数据，返回受影响行数
   *
   * @param ${propertyName} 数据模型
   * @return int 受影响行数
   * @date ${.now}
   */
  int insert(${classT} ${propertyName});

  /**
   * 批量插入多条数据,返回受影响行数
   *
   * @param list 数据模型集合
   * @return int 受影响行数
   * @date ${.now}
   */
  int insertBatch(List<${classT}> list);

  /**
   * 更新,更新条件为主键，返回受影响行数
   *
   * @param ${propertyName} 数据模型
   * @return int 受影响行数
   * @date ${.now}
   */
  int updateByPrimaryKey(${classT} ${propertyName});

  /**
   * 根据主键删除，返回受影响行数
   *
   * @param ${primaryKey.propertyName} 主键
   * @return int 受影响行数
   * @date ${.now}
   */
  int deleteByPrimaryKey(${primaryKey.dataType} ${primaryKey.propertyName});

  /**
   * 查询一行记录
   * @param ${propertyName} 查询条件
   * @return ${classT} 数据模型
   * @date ${.now}
   */
  ${classT} selectOne(${classT} ${propertyName});

  /**
   * 查询一个集合
   *
   * @param ${propertyName} 查询条件
   * @return List<${classT}> 模型集合
   * @date ${.now}
   */
  List<${classT}> list${classT}s(${classT} ${propertyName});

}
