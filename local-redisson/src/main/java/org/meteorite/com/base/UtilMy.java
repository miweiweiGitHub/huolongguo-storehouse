package org.meteorite.com.base;

import java.util.regex.Pattern;

/**
 * @author EX_052100260
 * @title: UtilMy
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-12 10:40
 */
public class UtilMy {

    public static void main(String[] args) {
        Pattern compile = Pattern.compile("\\d{4,8}$");

        System.out.println(compile.matcher("123445670").matches());
    }
}
