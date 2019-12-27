package com.github.chaos.generator.v2.conf;

import lombok.Data;

@Data
public class JdbcConfiguration {

  private String driverClass;

  private String connectionUrl;

  private String userId;

  private String password;

}
