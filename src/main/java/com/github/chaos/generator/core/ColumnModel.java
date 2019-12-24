/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午1:24:11
 * @version V1.0
 */
package com.github.chaos.generator.core;


import lombok.Data;

/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午1:24:11
 */
@Data
public class ColumnModel {

  private String name;

  private String propertyName;

  private String dataType;

  private String jdbcType;

  private boolean isPrimaryKey;

  private boolean increment;

  private String comment;

  private String getterName;

  private String setterName;

}
