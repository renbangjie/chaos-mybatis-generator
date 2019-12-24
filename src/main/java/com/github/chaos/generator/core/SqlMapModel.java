package com.github.chaos.generator.core;

import com.github.chaos.generator.generate.GenerateModel;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Aaron on 2016/12/2.
 */
@Getter
@Setter
public class SqlMapModel extends GenerateModel {

  private String namespace;

  private String name;

  private List<ColumnModel> columnModels;

  private String modelName;

  private String modelSimpleName;

  private String tableName;

  private String queryClassName;

}
