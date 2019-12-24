package com.github.chaos.generator.generate.service;

import com.github.chaos.generator.core.ContextModel;

/**
 * Created by Aaron on 2016/12/1.
 */
public interface IGenService {

  /**
   * 生成代码
   */
  void generate(ContextModel contextModel) throws Exception;

}
