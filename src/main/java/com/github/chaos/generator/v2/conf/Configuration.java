package com.github.chaos.generator.v2.conf;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Configuration {

  private String sqlMapPath;

  private String classPath;

  private JdbcConfiguration jdbcConfiguration;

  private List<TableConfiguration> tableConfigurations;

  private Map<String, String> typeResolverMap;

}
