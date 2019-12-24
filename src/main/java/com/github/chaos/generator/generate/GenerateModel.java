package com.github.chaos.generator.generate;

import com.github.chaos.generator.core.ColumnModel;
import lombok.Data;

/**
 * Created by Aaron on 2016/12/2.
 */
@Data
public class GenerateModel {

  protected String author;

  protected String templateName;

  /**
   * 主键
   */
  private ColumnModel primaryKey;

}
