/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午1:21:18
 * @version V1.0
 * <b>Copyright (c)</b> 2014医信金融信息服务（上海）有限公司-版权所有<br/>
 */
package com.github.chaos.generator.utils;


import com.github.chaos.generator.core.ColumnModel;
import com.github.chaos.generator.core.ContextModel;
import com.github.chaos.generator.core.DbConfModel;
import com.github.chaos.generator.core.TableModel;
import com.mysql.cj.jdbc.ConnectionImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午1:21:18
 */
public class DataBaseUtil {

  protected static final Log log = new SystemStreamLog();

  private static final String SQL_SELECT_TABLE_COMMENT = "SELECT TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema=? AND TABLE_NAME = ?";

  private static final String SQL_SELECT_COLUMNS = "SELECT `COLUMN_NAME`, `COLUMN_COMMENT`, `DATA_TYPE`, `COLUMN_KEY`, `EXTRA` FROM information_schema.columns WHERE `table_schema` = ?  and `table_name` = ? ORDER BY ORDINAL_POSITION";


  public static void buildContextModel(ContextModel contextModel) {
    DbConfModel dbConfModel = contextModel.getDbConfModel();
    try {
      Class.forName(dbConfModel.getDriverClass());
      try (Connection conn = DriverManager
          .getConnection(dbConfModel.getConnectionURL(),
              dbConfModel.getUser(), dbConfModel.getPassword())) {
        for (final TableModel tableModel : contextModel.getTableModels()) {
          executeQuery(new ResultHandler() {
            @Override
            public void call(ResultSet rs) throws SQLException {
              if (rs.next()) {
                tableModel.setDesc(rs.getString("TABLE_COMMENT"));
              }
            }
          }, conn, tableModel.getTableName(), SQL_SELECT_TABLE_COMMENT);
          executeQuery(new ResultHandler() {
            @Override
            public void call(ResultSet rs) throws SQLException {
              tableModel.setColumnModels(getColumns(rs));
            }
          }, conn, tableModel.getTableName(), SQL_SELECT_COLUMNS);
        }
      } catch (SQLException e) {
        log.error(e);
      }
    } catch (ClassNotFoundException e) {
      log.error(e);
    }
  }


  private static void executeQuery(ResultHandler resultHandler, Connection conn,
      String tableName, String sql) {
    String dbName = ((ConnectionImpl) conn).getProperties()
        .getProperty("dbname");
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, dbName);
      pstmt.setString(2, tableName);
      try (ResultSet rs = pstmt.executeQuery()) {
        resultHandler.call(rs);
      }
    } catch (SQLException e) {
      log.error(e);
    }
  }

  public static List<ColumnModel> getColumns(ResultSet rs)
      throws SQLException {
    Map<String, Object> javaTypeResolverMap = ConfUtil
        .getJavaTypeResolverMap();
    List<ColumnModel> columnModels = new ArrayList<>();
    ColumnModel columnModel = null;
    while (rs.next()) {
      columnModel = new ColumnModel();
      columnModel.setComment(rs.getString("COLUMN_COMMENT"));
      columnModel.setName(rs.getString("COLUMN_NAME"));
      columnModel.setJdbcType(rs.getString("DATA_TYPE"));
      String key = columnModel.getJdbcType().toLowerCase();
      if (!javaTypeResolverMap.containsKey(key)) {
        javaTypeResolverMap.put(key, "Object");
      }
      Object javaTypeResolver = javaTypeResolverMap.get(key);
      if (javaTypeResolver instanceof String) {
        columnModel.setDataType(javaTypeResolver.toString());
      } else {
        columnModel.setDataType(Object.class.getName());
      }
      columnModel.setPropertyName(JavaBeanUtils
          .getCamelCaseString(columnModel.getName(), false));
      columnModel.setPrimaryKey(
          "PRI".equalsIgnoreCase(rs.getString("COLUMN_KEY")));
      columnModel.setIncrement(
          StringUtils.isNotBlank(rs.getString("EXTRA")));
      columnModels.add(columnModel);
    }
    return columnModels;
  }


  public interface ResultHandler {

    void call(ResultSet rs) throws SQLException;

  }

}
