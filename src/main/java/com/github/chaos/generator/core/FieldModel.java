package com.github.chaos.generator.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Aaron on 2016/12/2.
 */
@Data
@AllArgsConstructor
public class FieldModel {

  /**
   * 访问修饰符
   */
  private String accessModifier;

  /**
   * 类型
   */
  private String typeName;

  /**
   * 字段名称
   */
  private String name;

  /**
   * 注解
   */
  private String annotation;

  /**
   * 字段描述
   */
  private String desc;


  public FieldModel(String accessModifier, String typeName, String name) {
    this.accessModifier = accessModifier;
    this.typeName = typeName;
    this.name = name;
  }

  public FieldModel(String accessModifier, String typeName, String name,
      String desc) {
    this.accessModifier = accessModifier;
    this.typeName = typeName;
    this.name = name;
    this.desc = desc;
  }

}
