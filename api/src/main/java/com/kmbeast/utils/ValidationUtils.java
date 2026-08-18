package com.kmbeast.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 校验工具类
 *
 * @author B站「程序员辰星」原创出品
 */
public class ValidationUtils {
    // 邮箱正则表达式
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );
    // 中文正则表达式
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    // 中国大陆手机号正则表达式（严格版）
    private static final Pattern MOBILE_PATTERN = Pattern.compile(
            "^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$"
    );

    // 国际手机号简化版正则（可根据需求扩展）
    private static final Pattern INTERNATIONAL_MOBILE_PATTERN = Pattern.compile(
            "^\\+(?:[0-9] ?){6,14}[0-9]$"
    );

    /**
     * 校验中国大陆手机号
     *
     * @param mobile 手机号码
     * @return 是否有效
     */
    public static boolean isValidChineseMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(mobile).matches();
    }

    /**
     * 校验国际手机号
     *
     * @param mobile 手机号码
     * @return 是否有效
     */
    public static boolean isValidInternationalMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        return INTERNATIONAL_MOBILE_PATTERN.matcher(mobile).matches();
    }

    /**
     * 通用手机号校验（自动识别国内/国际）
     *
     * @param mobile 手机号码
     * @return 是否有效
     */
    public static boolean isValidMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }

        // 国内手机号
        if (mobile.startsWith("1") && mobile.length() == 11) {
            return isValidChineseMobile(mobile);
        }

        // 国际手机号
        if (mobile.startsWith("+")) {
            return isValidInternationalMobile(mobile);
        }

        return false;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }


    /**
     * 校验字符串是否包含中文
     *
     * @param input 待校验字符串
     * @return 是否包含中文
     */
    public static boolean containsChinese(String input) {
        if (StringUtils.isEmpty(input)) {
            return false;
        }
        return CHINESE_PATTERN.matcher(input).find();
    }
}
