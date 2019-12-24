package com.github.chaos.generator.core;

import lombok.Data;

/**
 * Created by Aaron on 2016/12/1.
 */
@Data
public class DbConfModel {

  private String driverClass = "com.mysql.cj.jdbc.Driver";

  private String connectionURL;

  private String user;

  private String password;

}
