package com.github.chaos.generator.generate.service.impl;

import com.github.chaos.generator.core.ClassModel;
import com.github.chaos.generator.core.ColumnModel;
import com.github.chaos.generator.core.ContextModel;
import com.github.chaos.generator.core.SqlMapModel;
import com.github.chaos.generator.core.TableModel;
import com.github.chaos.generator.generate.GenerateModel;
import com.github.chaos.generator.generate.service.IGenService;
import com.github.chaos.generator.utils.DataBaseUtil;
import com.github.chaos.generator.utils.FreemarkerUtils;
import com.github.chaos.generator.utils.JavaBeanUtils;
import java.util.List;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

/**
 * Created by Aaron on 2016/12/1.
 */
public abstract class AbstractGenServiceImpl implements IGenService {

  protected static final Log log = new SystemStreamLog();

  /**
   * 生成代码
   */
  public void generate(ContextModel contextModel) throws Exception {
    StringBuilder sb = null;
    DataBaseUtil.buildContextModel(contextModel);
    for (TableModel tableModel : contextModel.getTableModels()) {
      if (tableModel.getColumnModels() == null
          || tableModel.getColumnModels().isEmpty()) {
        log.warn(tableModel.getTableName() + " not found.");
        continue;
      }
      List<GenerateModel> generateModels = this.process(tableModel);
      for (GenerateModel generateModel : generateModels) {
        generateModel.setAuthor(System.getenv().get("USERNAME"));
        sb = new StringBuilder();
        if (generateModel instanceof ClassModel) {
          sb.append(contextModel.getPackages());
          sb.append(JavaBeanUtils
              .splitString(((ClassModel) generateModel).getName()));
          sb.append(".java");
        } else if (generateModel instanceof SqlMapModel) {
          sb = new StringBuilder(contextModel.getSqlmapFile());
          sb.append("/");
          sb.append(((SqlMapModel) generateModel).getName());
          sb.append(".xml");
        }
        for (ColumnModel columnModel : tableModel.getColumnModels()) {
          if (columnModel.isPrimaryKey()) {
            generateModel.setPrimaryKey(columnModel);
          }
        }
        FreemarkerUtils.write(sb.toString(), FreemarkerUtils
            .process(generateModel.getTemplateName(),
                generateModel));
      }
    }
    sb.setLength(0);
  }

  /**
   * 生成代码
   */
  public abstract List<GenerateModel> process(TableModel tableModel)
      throws Exception;

}
