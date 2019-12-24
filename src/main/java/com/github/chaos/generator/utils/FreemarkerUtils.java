/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午7:36:02
 * @version V1.0
 */
package com.github.chaos.generator.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午7:36:02
 */
public class FreemarkerUtils {

  protected static final Log log = new SystemStreamLog();

  private static Configuration cfg = new Configuration(
      Configuration.VERSION_2_3_28);

  private static PathMatchingResourcePatternResolver patternResolver;

  private static String suffix = ".ftl";

  private static String templatesName[] = {"freemarker-model",
      "freemarker-mapper", "freemarker-sql-map", "freemarker-service",
      "freemarker-service-impl"};

  static {
    initFreeMarkerTemplates();
  }


  /**
   * 处理模板
   *
   * @throws Exception String
   * @date 2015年12月25日 上午9:43:20
   */
  public static String process(String templateName, Object table)
      throws Exception {
    Template template = cfg.getTemplate(templateName, "utf-8");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    template.process(table, new OutputStreamWriter(bos));
    return bos.toString("UTF-8");
  }

  /**
   * 生成文件
   *
   * @throws Exception void
   * @date 2015年12月25日 上午9:42:06
   */
  public static void write(String path, String content) throws Exception {
    File sourceFile = new File(path);
    if (sourceFile.isDirectory()) {
      throw new Exception(path + "不是一个文件");
    }
    if (sourceFile.exists()) {
      return;
    }
    File parentFile = sourceFile.getParentFile();
    if (!parentFile.exists() && !parentFile.mkdirs()) {
      throw new Exception("文件夹创建失败");
    }
    FileCopyUtils.copy(content, new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream(sourceFile),
            "UTF-8")));
    log.info("created:" + path);
  }

  private static void initFreeMarkerTemplates() {
    cfg.setDefaultEncoding("UTF-8");
    patternResolver = new PathMatchingResourcePatternResolver();
    StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
    for (String templateName : templatesName) {
      Resource resource = patternResolver.getResource(
          "classpath:META-INF/templates/" + templateName + suffix);
      try {
        stringTemplateLoader.putTemplate(templateName,
            new String(FileCopyUtils.copyToByteArray(resource.getInputStream()),
                "UTF-8"));
      } catch (IOException e) {
        log.error("读取模板文件异常:", e);
        System.exit(0);
      }
      cfg.setTemplateLoader(stringTemplateLoader);
    }
  }

}
