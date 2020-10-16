package org.meteorite.com.base;

import org.apache.commons.lang3.CharEncoding;
import org.meteorite.com.domain.UserLocal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EX_052100260
 * @title: BantchHandlerData
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-17 15:20
 */
public class BatchHandlerData {

    public static void main(String[] args) {
        Class beanClass = UserLocal.class;
        Map<String, Object> senders = new HashMap<>();

        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream("C:\\Users\\EX_052100260\\Downloads\\msgtask628_0918094130058.csv"), CharEncoding.UTF_8);

            List<List> listUser = BatchUtil.parseCsvToBean(reader, beanClass, ',');


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }




    }




}
