/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午7:30:56
 * @version V1.0
 */
package com.github.chaos.generator.utils;

/**
 * @author renbangjie renbangjie@126.com
 * @date 2015年12月19日 下午7:30:56
 */
public class JavaBeanUtils {

  public static String getSetterMethodName(String property) {
    StringBuilder sb = new StringBuilder();
    sb.append(property);
    if (Character.isLowerCase(sb.charAt(0))) {
      if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
      }
    }
    sb.insert(0, "set");

    return sb.toString();
  }

  public static String getGetterMethodName(String property) {
    StringBuilder sb = new StringBuilder();
    sb.append(property);
    if (Character.isLowerCase(sb.charAt(0))) {
      if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
      }
    }
    sb.insert(0, "get");
    return sb.toString();
  }


  public static String firstLowerCase(String property) {
    if (Character.isLowerCase(property.charAt(0))) {
      return property;
    }
    StringBuilder sb = new StringBuilder(property);
    sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
    return sb.toString();
  }


  public static String getCamelCaseString(String inputString,
      boolean firstCharacterUppercase) {
    StringBuilder sb = new StringBuilder();
    boolean nextUpperCase = false;
    for (int i = 0; i < inputString.length(); i++) {
      char c = inputString.charAt(i);
      switch (c) {
        case '_':
        case '-':
        case '@':
        case '$':
        case '#':
        case ' ':
        case '/':
        case '&':
          if (sb.length() > 0) {
            nextUpperCase = true;
          }
          break;
        default:
          if (nextUpperCase) {
            sb.append(Character.toUpperCase(c));
            nextUpperCase = false;
          } else {
            sb.append(Character.toLowerCase(c));
          }
          break;
      }
    }
    if (firstCharacterUppercase) {
      sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    }
    return sb.toString();
  }

  public static String splitString(String packageName) {
    StringBuilder sb = new StringBuilder("/");
    for (int i = 0; i < packageName.length(); i++) {
      char c = packageName.charAt(i);
      switch (c) {
        case '.':
          sb.append("/");
          break;
        default:
          sb.append(c);
          break;
      }
    }
    return sb.toString();
  }

}
