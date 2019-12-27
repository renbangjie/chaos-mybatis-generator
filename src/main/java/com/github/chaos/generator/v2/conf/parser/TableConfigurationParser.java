package com.github.chaos.generator.v2.conf.parser;

import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_DOMAIN_NAME;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_PACKAGE_NAME;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_TABLE_NAME;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.NODE_TABLE;

import com.github.chaos.generator.v2.conf.TableConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.Element;

@RequiredArgsConstructor
public class TableConfigurationParser {

  private final Document document;

  public List<TableConfiguration> getTableConfigurations() {
    List<Element> elements = document.selectNodes(NODE_TABLE);
    if (elements == null || elements.isEmpty()) {
      return Collections.emptyList();
    }
    List<TableConfiguration> tableConfigurations = new ArrayList<>(
        elements.size());
    for (Element element : elements) {
      tableConfigurations.add(createTableConfiguration(element));
    }
    return tableConfigurations;
  }


  private TableConfiguration createTableConfiguration(Element element) {
    String tableName = element.attributeValue(ATTR_TABLE_NAME);
    String domainName = element.attributeValue(ATTR_DOMAIN_NAME);
    String packageName = element.attributeValue(ATTR_PACKAGE_NAME);
    TableConfiguration tableConfiguration = new TableConfiguration();
    tableConfiguration.setTableName(tableName);
    tableConfiguration.setDomainName(domainName);
    tableConfiguration.setPackageName(packageName);
    return tableConfiguration;
  }

}
