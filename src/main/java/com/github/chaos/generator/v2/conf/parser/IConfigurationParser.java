package com.github.chaos.generator.v2.conf.parser;

import com.github.chaos.generator.v2.conf.Configuration;
import java.io.File;
import org.dom4j.DocumentException;

public interface IConfigurationParser {

  Configuration parser(File configurationFile) throws DocumentException;

}
