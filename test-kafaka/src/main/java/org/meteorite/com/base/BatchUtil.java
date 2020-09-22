package org.meteorite.com.base;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author EX_052100260
 * @title: BatchUtil
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-17 15:21
 */
public class BatchUtil {

    /**
     * 模拟批量处理数据（一）
     * 当数据量过大过多导致超时等问题可以将数据进行分批处理
     * @author 【】
     *
     */
    public static void listBatchUtil(List<Integer> lists) {

        System.out.println(lists);
        System.out.println(lists.size());
        int temp = 1;
        for (int i = 0; i < lists.size(); i += 10) {
            System.out.println("======================进行第" + temp + "次批处理=======================");
            if (lists.size() - i > 10) {
                System.out.println(lists.subList(i, i + 10).toString());
            } else {
                if (lists.size() > i) {
                    System.out.println(lists.subList(i, lists.size()).toString());
                }
            }
            temp += 1;
        }
    }

    /**
     * 模拟批量处理数据（二）
     * 当数据量过大过多导致超时等问题可以将数据进行分批处理
     * @author 【】
     *
     */
    public static void listBatchUtil2(List<Integer> lists) {

        System.out.println(lists);
        // 定义批处理的数据数量（即批处理条件）
        int num = 10;
        // 判断集合数量，如果小于等于定义的数量（即未达到批处理条件），直接进行处理
        if (lists.size() <= num) {

            System.out.println(lists.size());
            System.out.println(lists.toString().substring(1, lists.toString().lastIndexOf("]")));

            return;
        }
        // 如果大于定义的数量，按定义数量进行批处理
        int  times = lists.size()/num + 1;

        System.out.println("一共要进行"+times+"次批处理");
        // 遍历分批处理次数，并进行批处理
        for (int i = 0; i < times; i++) {
            // 定义要进行批处理的临时集合
            List<Integer> tempList = new ArrayList<>();
            // 将要批处理数据放入临时集合中
            for (int j = i*num; j < lists.size(); j++) {
                tempList.add(lists.get(j));
                if (tempList.size() == num) {
                    break;
                }
            }

            // 进行批处理
            System.out.println("======================进行第"+(i+1)+"次批处理=======================");
            System.out.println(tempList.size());
            System.out.println(tempList.toString().substring(1, tempList.toString().lastIndexOf("]")));
            System.out.println("=========================================================");
        }
    }

    /**
     * 解析文件并返回beanList
     *
     * @param beanClass 对应的beanClass
     * @param separator 文件中使用的分割符
     * @return 返回beanList
     */
    public static <T> List<T> parseCsvToBean(InputStreamReader reader, Class< T > beanClass, char separator) {
        List<T> data = new CsvToBeanBuilder(reader)
                .withType(beanClass)
                .withSeparator(separator)
                .build()
                .parse();
        return data;
    }

    public static void main(String[] args) {
        List<Integer> lists = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            lists.add(i);
        }
        listBatchUtil(lists);

        listBatchUtil2(lists);
    }
}
