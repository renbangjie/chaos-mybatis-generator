package com.github.chaos.generator.v2.conf.parser;

import com.github.chaos.generator.v2.conf.Configuration;
import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class DefaultConfigurationParser implements IConfigurationParser {

  @Override
  public Configuration parser(File configurationFile) throws DocumentException {
    if (configurationFile == null || !configurationFile.isFile()
        || !configurationFile.exists()) {
      throw new RuntimeException("配置文件路径异常");
    }
    Document document = new SAXReader().read(configurationFile);

    Configuration configuration = new Configuration();
    configuration.setJdbcConfiguration(
        new JdbcConfigurationParser(document).getJdbcConfiguration());
    configuration.setTableConfigurations(
        new TableConfigurationParser(document).getTableConfigurations());
    configuration.setTypeResolverMap(
        new TypeResolverConfigurationParser(document).getTypeResolverMap());
    return configuration;
  }


}
