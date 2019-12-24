package com.github.chaos.generator.core;

import com.github.chaos.generator.generate.GenerateModel;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by Aaron on 2016/12/2.
 */
@Data
@AllArgsConstructor
public class ClassModel extends GenerateModel {

  /**
   * 类全名
   */
  private String name;

  /**
   * 类名
   */
  private String simpleName;

  /**
   * 包名称
   */
  private String packages;

  /**
   * 父类
   */
  private String parentClassName;

  /**
   * 接口
   */
  private String interfaceClassName;

  /**
   * 泛型类名称
   */
  private String classT;

  /**
   * 字段
   */
  private List<FieldModel> fieldModels;

  /**
   * 导入的包
   */
  private List<String> importClassName = new ArrayList<>();

  /**
   * 描述
   */
  private String desc;


  public static Builder builder(String packages, String simpleName, String desc,
      String templateName) {
    return new Builder(packages, simpleName, desc, templateName);
  }


  @RequiredArgsConstructor
  public static class Builder {

    private final String packages;
    private final String simpleName;
    private String parentClassName;
    private String interfaceClassName;
    private String classT;
    private List<FieldModel> fieldModels;
    private List<String> imports;
    private final String desc;
    private final String templateName;


    public Builder addParentClassName(String parentClassName) {
      this.parentClassName = parentClassName;
      return this;
    }

    public Builder addInterfaceClassName(String interfaceClassName) {
      this.interfaceClassName = interfaceClassName;
      return this;
    }


    public Builder addClassT(String classT) {
      this.classT = classT;
      return this;
    }

    public Builder addFieldModel(FieldModel fieldModel) {
      if (fieldModels == null) {
        fieldModels = new ArrayList<>();
      }
      fieldModels.add(fieldModel);
      return this;
    }

    public Builder addImport(String importClassName) {
      if (imports == null) {
        imports = new ArrayList<>();
      }
      if (!imports.contains(importClassName)) {
        imports.add(importClassName);
      }
      return this;
    }

    public ClassModel of() {
      StringBuilder fullName = new StringBuilder(packages);
      fullName.append(".");
      fullName.append(simpleName);
      ClassModel classModel = new ClassModel(fullName.toString(), simpleName,
          packages,
          parentClassName,
          interfaceClassName, classT,
          fieldModels, imports, desc);
      classModel.setTemplateName(templateName);
      return classModel;
    }

  }

}
