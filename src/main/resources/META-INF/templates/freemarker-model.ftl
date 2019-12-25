package ${packages};

<#list importClassName as item>
import ${item};
</#list>

/**
 * <#if desc??>
 *	${desc}
 * </#if>
 * @author ${author}
 * @date ${.now}
 */
@Getter
@Setter
public class ${simpleName} implements ${interfaceClassName} {

  private static final long serialVersionUID = 1L;

<#if fieldModels??>
  <#list fieldModels as field>
    <#if field.desc??>
  /**
   * ${field.desc}
   */
    </#if>
  private ${field.typeName} ${field.name};

  </#list>
</#if>

}
