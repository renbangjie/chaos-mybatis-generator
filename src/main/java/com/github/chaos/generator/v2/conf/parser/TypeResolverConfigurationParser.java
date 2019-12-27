package com.github.chaos.generator.v2.conf.parser;

import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_JAVA_TYPE;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_JDBC_TYPE;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.NODE_JAVA_TYPE_RESOLVER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.Element;

@RequiredArgsConstructor
public class TypeResolverConfigurationParser {

  private final Document document;

  public Map<String, String> getTypeResolverMap() {
    List<Element> properties = document
        .selectNodes(NODE_JAVA_TYPE_RESOLVER);
    if (properties == null || properties.isEmpty()) {
      return getDefaultResolverMap();
    }
    Map<String, String> javaTypeResolverMap = getDefaultResolverMap();
    for (Element property : properties) {
      String key = property.attributeValue(ATTR_JDBC_TYPE);
      String value = property.attributeValue(ATTR_JAVA_TYPE);
      javaTypeResolverMap.put(key, value);
    }
    return javaTypeResolverMap;
  }


  private Map<String, String> getDefaultResolverMap() {
    Map<String, String> javaTypeResolverMap = new HashMap<>();
    javaTypeResolverMap.put("int", "Integer");
    javaTypeResolverMap.put("tinyint", "Integer");
    javaTypeResolverMap.put("mediumint", "Integer");
    javaTypeResolverMap.put("bigint", "Long");
    javaTypeResolverMap.put("char", "String");
    javaTypeResolverMap.put("varchar", "String");
    javaTypeResolverMap.put("text", "String");
    javaTypeResolverMap.put("float", "Float");
    javaTypeResolverMap.put("double", "Double");
    javaTypeResolverMap.put("decimal", "java.math.BigDecimal");
    javaTypeResolverMap.put("smallint", "Integer");
    javaTypeResolverMap.put("date", "java.util.Date");
    javaTypeResolverMap.put("datetime", "java.util.Date");
    return javaTypeResolverMap;
  }

}
