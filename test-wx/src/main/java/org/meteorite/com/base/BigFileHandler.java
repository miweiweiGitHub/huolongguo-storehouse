package org.meteorite.com.base;

import com.alibaba.fastjson.JSONObject;

/**
 * @author EX_052100260
 * @title: BigFileHandler
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-19 14:30
 */
public class BigFileHandler {


    public String test() {


        return "";
    }


    public static void main(String[] args) {
        JSONObject jsonObject = JSONObject.parseObject("{\"code\":\"0000\"}");
        JSONObject js = jsonObject.getJSONObject("data");
        js.getString("userType").toString();
        js.getString("userType1").toString();
    }
}
