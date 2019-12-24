package com.github.chaos.generator.utils;


import com.github.chaos.generator.core.ColumnModel;
import com.github.chaos.generator.core.FieldModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2016/12/3.
 */
public class ColumnUtil {

  /**
   * 获取字段
   */
  public static List<FieldModel> getFieldModels(
      List<ColumnModel> columnModels) {
    List<FieldModel> fieldModels = new ArrayList<FieldModel>();
    for (ColumnModel columnModel : columnModels) {
      fieldModels.add(new FieldModel("",
          columnModel.getDataType(),
          columnModel.getPropertyName(), columnModel.getComment()));
    }
    return fieldModels;
  }


}
