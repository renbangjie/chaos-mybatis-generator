package com.github.chaos.generator.core;

import java.util.List;
import lombok.Data;

/**
 * Created by Aaron on 2016/12/1.
 */
@Data
public class TableModel {

  /**
   * 表名
   */
  private String tableName;

  /**
   * 对应的类名
   */
  private String domainName;

  /**
   * 包路径
   */
  private String packages;

  /**
   * 描述
   */
  private String desc;

  /**
   * 列
   */
  private List<ColumnModel> columnModels;

}
