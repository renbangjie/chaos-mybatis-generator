package com.github.chaos.generator.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.Assert;

/**
 * Created by Aaron on 2016/12/1.
 */
public class XmlUtil {


  /**
   * 获取 xml document
   */
  public static Document getDocument(String xml) throws Exception {
    Assert.hasText(xml, "参数异常,配置文件不能为空.");
    File xmlFile = new File(xml);
    if (!xmlFile.exists()) {
      throw new Exception("没有找到配置文件：" + xml);
    }
    return new SAXReader().read(xmlFile);
  }


  /**
   * 获取 xml element
   */
  public static Element getDocument(Element element, String path) {
    Assert.hasText(path, "参数异常,节点路径不能为空.");
    for (String o : path.split("/")) {
      element = element.element(o);
      Assert.notNull(element, "节点不存在:" + path);
    }
    return element;
  }


  /**
   * 获取多个属性
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String> attributes(Element element) {
    Assert.notNull(element, "参数为空");
    Map<String, String> resultMap = new HashMap<>();
    //noinspection unchecked
    List<Attribute> attributes = (List<Attribute>) element.attributes();
    for (Attribute attribute : attributes) {
      resultMap.put(attribute.getName(), attribute.getValue());
    }
    return resultMap;
  }


  /**
   * 获取多个属性
   */
  @SuppressWarnings("unchecked")
  public static Map<String, String> attributes(List<Element> elements) {
    Map<String, String> resultMap = new HashMap<String, String>();
    if (elements == null || elements.isEmpty()) {
      return resultMap;
    }
    List<Attribute> attibutes = null;
    for (Element element : elements) {
      attibutes = (List<Attribute>) element.attributes();
      resultMap.put(attibutes.get(0).getValue(),
          attibutes.get(1).getValue());
    }
    return resultMap;
  }


  /**
   * 获取多个节点的值
   */
  public static Map<String, String> elements(List<Element> elements) {
    Map<String, String> jsonMap = new HashMap<>();
    for (Element element : elements) {
      if (StringUtils.isBlank(element.getTextTrim())) {
        continue;
      }
      jsonMap.put(element.getName(), element.getTextTrim());
    }
    return jsonMap;
  }

}
