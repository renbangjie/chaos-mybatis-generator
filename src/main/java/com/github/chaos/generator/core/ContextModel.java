package com.github.chaos.generator.core;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by Aaron on 2016/12/1.
 */
@Data
@RequiredArgsConstructor
public class ContextModel {

  private DbConfModel dbConfModel;

  private List<TableModel> tableModels;

  private final String packages;

  private final String sqlmapFile;

}
