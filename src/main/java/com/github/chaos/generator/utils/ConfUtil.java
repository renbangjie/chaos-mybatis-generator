package com.github.chaos.generator.utils;

import com.alibaba.fastjson.JSON;
import com.github.chaos.generator.core.ContextModel;
import com.github.chaos.generator.core.DbConfModel;
import com.github.chaos.generator.core.TableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by Aaron on 2016/12/1.
 */
public class ConfUtil {

  private static Document document;

  private static final Map<String, Object> javaTypeResolverMap = new HashMap<>();

  /**
   * 加载配置
   */
  public static ContextModel load(String configDirectory, String packages,
      String sqlMapFile) throws Exception {
    document = XmlUtil.getDocument(configDirectory);
    ContextModel contextModel = new ContextModel(packages, sqlMapFile);
    ConfUtil.loadAppDbConf(contextModel);
    ConfUtil.loadAppTypeResolver();
    ConfUtil.loadAppTable(contextModel);
    return contextModel;
  }

  /**
   * 加载数据库连接配置
   */
  private static void loadAppDbConf(ContextModel contextModel) {
    contextModel.setDbConfModel(
        JSON.parseObject(
            JSON.toJSONString(XmlUtil.attributes(document.getRootElement())),
            DbConfModel.class));
  }

  /**
   * 数据类型
   */
  @SuppressWarnings("unchecked")
  private static void loadAppTypeResolver() {
    javaTypeResolverMap.put("int", "Integer");
    javaTypeResolverMap.put("tinyint", "Integer");
    javaTypeResolverMap.put("mediumint", "Integer");
    javaTypeResolverMap.put("bigint", "Long");
    javaTypeResolverMap.put("char", "String");
    javaTypeResolverMap.put("varchar", "String");
    javaTypeResolverMap.put("text", "String");
    javaTypeResolverMap.put("float", "Float");
    javaTypeResolverMap.put("double", "Double");
    javaTypeResolverMap.put("decimal", BigDecimal.class);
    javaTypeResolverMap.put("smallint", "Integer");
    javaTypeResolverMap.put("date", Date.class);
    javaTypeResolverMap.put("datetime", Date.class);
    //本地覆盖
    //noinspection unchecked
    List nodes = document.getRootElement()
        .selectNodes("//context/javaTypeResolver/property");
    //noinspection unchecked
    javaTypeResolverMap.putAll(XmlUtil.attributes(nodes));
  }


  /**
   * 加载表配置
   */
  private static void loadAppTable(ContextModel contextModel) {
    @SuppressWarnings("unchecked")
    List<Element> elements = document.getRootElement().elements("table");
    if (elements == null || elements.isEmpty()) {
      return;
    }
    List<TableModel> tableModels = new ArrayList<>();
    for (Element element : elements) {
      tableModels.add(parseAppTable(contextModel, element));
    }
    contextModel.setTableModels(tableModels);
  }


  /**
   * 解析表
   */
  private static TableModel parseAppTable(ContextModel contextModel,
      Element tableElement) {
    TableModel tableModel = new TableModel();
    tableModel.setTableName(tableElement.attributeValue("tableName"));
    tableModel.setDomainName(tableElement.attributeValue("domainName"));
    tableModel.setPackages(tableElement.attributeValue("packages"));
    tableModel.setDesc(tableElement.attributeValue("desc"));
    return tableModel;
  }

  public static Map<String, Object> getJavaTypeResolverMap() {
    return javaTypeResolverMap;
  }
}
