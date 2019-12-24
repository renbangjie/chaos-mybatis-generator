package com.github.chaos.generator;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.chaos.generator.core.ContextModel;
import com.github.chaos.generator.generate.service.IGenService;
import com.github.chaos.generator.generate.service.impl.GenServiceImpl;
import com.github.chaos.generator.utils.ConfUtil;
import java.io.File;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @phase process-sources
 */
@Mojo(name = "mybatis-generate")
public class MybatisMojo extends AbstractMojo {

  /**
   * sqlmap路径
   */
  @Parameter(required = true)
  private String sqlMap;

  /**
   * 类路径
   */
  @Parameter(required = true)
  private String packages;

  /**
   * 配置文件路径
   */
  @Parameter(required = true)
  private String configDirectory;


  public void execute() throws MojoExecutionException {
    try {
      File context = new File(configDirectory);
      if (context.isDirectory() || !context.exists()) {
        getLog().error(new Exception(
            "configDirectory:" + configDirectory + ",必须是一个文件"));
        return;
      }
      if (!new File(packages).isDirectory()) {
        getLog().error(new Exception("packages:" + packages + ",必须是一个文件夹"));
        return;
      }
      if (!new File(sqlMap).isDirectory()) {
        getLog().error(new Exception("sqlmap:" + sqlMap + ",必须是一个文件夹"));
        return;
      }
      IGenService genService = new GenServiceImpl();
      genService.generate(ConfUtil.load(configDirectory, packages, sqlMap));
    } catch (Exception e) {
      getLog().error(e);
    }
  }


  public static void main(String[] args) throws Exception {
    String sqlmap = "E:\\workspace\\chaos-mybatis-generator\\src\\main\\java\\com\\github\\chaos\\test\\sql";
    String packages = "E:\\workspace\\chaos-mybatis-generator\\src\\main\\java";
    String configDirectory = "E:\\workspace\\chaos-mybatis-generator\\src\\main\\resources\\META-INF\\generatorConfig.xml";
    ContextModel contextModel = ConfUtil
        .load(configDirectory, packages, sqlmap);
    IGenService genService = new GenServiceImpl();
    genService.generate(contextModel);
  }

}
