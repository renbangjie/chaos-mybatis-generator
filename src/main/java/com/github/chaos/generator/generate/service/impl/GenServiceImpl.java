package com.github.chaos.generator.generate.service.impl;

import com.github.chaos.generator.core.ClassModel;
import com.github.chaos.generator.core.ClassModel.Builder;
import com.github.chaos.generator.core.FieldModel;
import com.github.chaos.generator.core.SqlMapModel;
import com.github.chaos.generator.core.TableModel;
import com.github.chaos.generator.generate.GenerateModel;
import com.github.chaos.generator.generate.service.IGenService;
import com.github.chaos.generator.utils.ColumnUtil;
import com.github.chaos.generator.utils.JavaBeanUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Aaron on 2016/12/2.
 */
public class GenServiceImpl extends AbstractGenServiceImpl
    implements IGenService {

  /**
   * 生成代码
   */
  public List<GenerateModel> process(TableModel tableModel) {
    List<GenerateModel> generateModels = new ArrayList<>();

    ClassModel domainModel = this.getDomainModel(tableModel);
    ClassModel mapper = this.getMapper(tableModel, domainModel);
    ClassModel service = this.getService(tableModel, domainModel);
    ClassModel serviceImpl = this
        .getServiceImpl(tableModel, service, mapper, domainModel);
    SqlMapModel sqlMapModel = this
        .getSqlMapModel(tableModel, mapper, domainModel);

    generateModels.add(domainModel);
    generateModels.add(mapper);
    generateModels.add(service);
    generateModels.add(serviceImpl);
    generateModels.add(sqlMapModel);

    return generateModels;
  }


  /**
   * 数据模型
   *
   * @param tableModel 表属性
   * @return com.magic.generate.core.ClassModel
   */
  private ClassModel getDomainModel(TableModel tableModel) {
    String packages = tableModel.getPackages() + ".model";
    List<FieldModel> fieldModels = ColumnUtil
        .getFieldModels(tableModel.getColumnModels());
    Builder builder = ClassModel
        .builder(packages, tableModel.getDomainName(), tableModel.getDesc(),
            "freemarker-model")
        .addInterfaceClassName(Serializable.class.getSimpleName())
        .addImport(Getter.class.getName())
        .addImport(Setter.class.getName())
        .addImport(Serializable.class.getName());
    for (FieldModel fieldModel : fieldModels) {
      if (fieldModel.getTypeName().matches("^([a-zA-Z]+\\.+)+[a-zA-Z]+$")) {
        builder.addImport(fieldModel.getTypeName());
        fieldModel.setTypeName(
            fieldModel.getTypeName()
                .substring(fieldModel.getTypeName().lastIndexOf(".") + 1));
      }
    }
    ClassModel classModel = builder.of();
    classModel.setFieldModels(fieldModels);
    return classModel;
  }


  /**
   * mapper接口
   *
   * @param tableModel 表属性
   * @param domainModel 数据模型
   * @return com.magic.generate.core.ClassModel
   */
  private ClassModel getMapper(TableModel tableModel, ClassModel domainModel) {
    String packages = tableModel.getPackages() + ".mapper";
    String simpleName = domainModel.getSimpleName() + "Mapper";
    return ClassModel.builder(packages, simpleName, domainModel.getDesc(),
        "freemarker-mapper")
        .addClassT(domainModel.getSimpleName())
        .addImport(domainModel.getName())
        .addImport(Mapper.class.getName())
        .addImport(List.class.getName()).of();
  }


  /**
   * service接口
   *
   * @param tableModel 表属性
   * @param domainModel 数据模型
   * @return com.magic.generate.core.ClassModel
   */
  private ClassModel getService(TableModel tableModel, ClassModel domainModel) {
    String packages = tableModel.getPackages() + ".service";
    String simpleName = domainModel.getSimpleName() + "Service";
    return ClassModel.builder(packages, simpleName, domainModel.getDesc(),
        "freemarker-service")
        .addClassT(domainModel.getSimpleName())
        .addImport(domainModel.getName())
        .of();
  }


  /**
   * 生成service实现类
   *
   * @param tableModel 表属性
   * @param serviceClass 接口
   * @param mapperClass mapper接口
   * @param domainModel 数据模型
   * @return com.magic.generate.core.ClassModel
   */
  private ClassModel getServiceImpl(TableModel tableModel,
      ClassModel serviceClass,
      ClassModel mapperClass,
      ClassModel domainModel) {
    String fieldName = JavaBeanUtils
        .firstLowerCase(mapperClass.getSimpleName());
    String packages = tableModel.getPackages() + ".service.impl";
    String simpleName = domainModel.getSimpleName() + "ServiceImpl";
    return ClassModel
        .builder(packages, simpleName, domainModel.getDesc(),
            "freemarker-service-impl")
        .addClassT(domainModel.getSimpleName())
        .addFieldModel(new FieldModel("private", mapperClass.getSimpleName(),
            fieldName, "@Resource", mapperClass.getDesc()))
        .addInterfaceClassName(serviceClass.getSimpleName())
        .addImport(domainModel.getName())
        .addImport(Service.class.getName())
        .addImport(Resource.class.getName())
        .addImport(Transactional.class.getName())
        .addImport(Exception.class.getName())
        .addImport(RuntimeException.class.getName())
        .addImport(mapperClass.getName())
        .addImport(serviceClass.getName()).of();
  }


  /**
   * 生成sqlmap
   */
  private SqlMapModel getSqlMapModel(TableModel tableModel, ClassModel mapper,
      ClassModel domainModel) {
    SqlMapModel sqlMapModel = new SqlMapModel();
    sqlMapModel.setName(mapper.getSimpleName());
    sqlMapModel.setNamespace(mapper.getName());
    sqlMapModel.setModelName(domainModel.getName());
    sqlMapModel.setModelSimpleName(domainModel.getSimpleName());
    sqlMapModel.setColumnModels(tableModel.getColumnModels());
    sqlMapModel.setTableName(tableModel.getTableName());
    sqlMapModel.setTemplateName("freemarker-sql-map");
    return sqlMapModel;
  }


}
