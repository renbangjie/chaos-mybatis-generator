package com.github.chaos.generator.v2.conf.parser;

import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_CONNECTION_URL;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_DRIVER_CLASS;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_PASSWORD;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.ATTR_USER_ID;
import static com.github.chaos.generator.v2.conf.consts.XmlConst.NODE_JDBC_CONNECTION;

import com.github.chaos.generator.v2.conf.JdbcConfiguration;
import lombok.RequiredArgsConstructor;
import org.dom4j.Document;
import org.dom4j.Element;

@RequiredArgsConstructor
public class JdbcConfigurationParser {

  private final Document document;

  public JdbcConfiguration getJdbcConfiguration() {
    Element element = (Element) document.selectSingleNode(NODE_JDBC_CONNECTION);
    if (element == null) {
      throw new RuntimeException("配置文件异常,必须配置数据库连接");
    }
    JdbcConfiguration jdbcConfiguration = new JdbcConfiguration();
    String driverClass = element.attributeValue(ATTR_DRIVER_CLASS);
    String connectionUrl = element.attributeValue(ATTR_CONNECTION_URL);
    String userId = element.attributeValue(ATTR_USER_ID);
    String password = element.attributeValue(ATTR_PASSWORD);

    jdbcConfiguration.setDriverClass(driverClass);
    jdbcConfiguration.setConnectionUrl(connectionUrl);
    jdbcConfiguration.setUserId(userId);
    jdbcConfiguration.setPassword(password);

    return jdbcConfiguration;
  }

}
