package com.liuyang;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class MethodHelper {

    static String uri = "/admin/recommend/v1/eval/deal";
    static String method = "get";

    public static void main(String[] args) {
        System.out.println(getFullRestfulMethod(uri, method));
    }

    private static final Pattern PATTERN_FILTER = Pattern.compile("/+(api|admin)/\\S+");
    private static final Pattern PATTERN_NUMBER = Pattern.compile("\\d+");
    private static final Pattern PATTERN_VERSION = Pattern.compile("v\\d+");

    public static String getFullRestfulMethod(String uri, String method) {
        if (PATTERN_FILTER.matcher(uri).matches()) {//api或admin开头的请求才监控
            String[] ss = uri.split("/");
            StringBuilder className = new StringBuilder();
            StringBuilder methodName = new StringBuilder();
            int index = 0;
            for (String s1 : ss) {
                if (!StringUtils.isEmpty(s1)) {
                    if (index < 2) {
                        className.append(className.length() == 0 ? s1 : StringUtils.capitalize(s1));
                    } else {
                        if (PATTERN_NUMBER.matcher(s1).matches()) {//URI中纯数字的，作为某种ID参数处理
                            continue;
                        } else {
                            if (PATTERN_VERSION.matcher(s1).matches()) {//接口版本号
                                className.append(StringUtils.capitalize(s1));
                            } else {
                                if (methodName.length() == 0) {
                                    methodName.append(method).append(StringUtils.capitalize(s1));
                                } else {
                                    methodName.append(StringUtils.capitalize(s1));
                                }
                            }
                        }
                    }
                    index++;
                }
            }
            return className + "-" + methodName;
        }

        return "unknownMethod";
    }


}
