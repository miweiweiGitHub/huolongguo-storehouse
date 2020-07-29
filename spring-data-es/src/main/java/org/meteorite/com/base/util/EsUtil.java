///*
// * Copyright (C) 1999-2018 IFLYTEK Inc.All Rights Reserved.
// */
//package org.example.common.util;
//
//import com.alibaba.csp.sentinel.util.StringUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.PageInfo;
//import com.google.gson.JsonObject;
//import io.searchbox.client.JestClient;
//import io.searchbox.client.JestResult;
//import io.searchbox.core.Bulk;
//import io.searchbox.core.BulkResult;
//import io.searchbox.core.Index;
//import io.searchbox.core.Search;
//import io.searchbox.core.SearchScroll;
//import io.searchbox.indices.CreateIndex;
//import io.searchbox.indices.DeleteIndex;
//import io.searchbox.indices.IndicesExists;
//import io.searchbox.indices.mapping.GetMapping;
//import io.searchbox.indices.mapping.PutMapping;
//import io.searchbox.params.Parameters;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.*;
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import org.apache.commons.lang3.*;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.cglib.beans.BeanMap;
//import com.github.pagehelper.Page;
//import org.springframework.stereotype.Component;
//
//
///**
// * <p>
// * elasticSearch工具类
// * </p>
// *
// **/
//@Component
//public class EsUtil {
//
//    private final static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EsUtil.class);
//
//    /**
//     * 游标大小
//     */
//    private final static int MAX_SCROLL_SIZE = 200;
//
//    /**
//     * es工具类实例
//     */
//    private static EsUtil esUtil;
//
//    /**
//     * Jest客户端
//     */
//    @Resource
//    private JestClient jestClient;
//
//    /**
//     * 初始化方法
//     */
//    @PostConstruct
//    public void init() {
//        esUtil = this;
//        esUtil.jestClient = this.jestClient;
//    }
//
//    /**
//     * 全文搜索 分页查询
//     *
//     * @param indexName 索引名称
//     * @param typeName 类型名称
//     * @param searchSourceBuilder 查询DSL构建类
//     * @param pageNum 当前页
//     * @param pageSize 每页记录数
//     * @return
//     */
//    public static <T> PageInfo<T> pageSearch(String indexName, String typeName, SearchSourceBuilder searchSourceBuilder,
//                                             Map<String, String> fieldSorts, Class<T> clazz, int pageNum, int pageSize) {
//        int offSet = 0;
//        //计算分页起始行
//        if (pageNum > 0 && pageSize > 0) {
//            offSet = pageSize * (pageNum - 1);
//        }
//
//        //分页查询
//        searchSourceBuilder.from(offSet).size(pageSize);
//        //排序
//        if (fieldSorts != null && fieldSorts.size() > 0) {
//            for (Map.Entry<String, String> field : fieldSorts.entrySet()) {
//                if (null == field) {
//                    continue;
//                }
//                if (SortOrder.DESC.toString().equals(field.getValue())) {
//                    searchSourceBuilder.sort(field.getKey(), SortOrder.DESC);
//                } else {
//                    searchSourceBuilder.sort(field.getKey(), SortOrder.ASC);
//                }
//
//            }
//        }
//
//        //获取es的jest客户端连接
//        Search.Builder builder = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexName)
//                .addType(typeName);
//
//        Search search = builder.build();
//
//        //DSL语句
//        String query = searchSourceBuilder.toString();
//        log.info("indexName=" + indexName + ", typeName=" + typeName + ", elasticsearch搜索查询语句:\n" + query);
//
//        //获取es的jest客户端连接
//        try {
//            JestResult jestResult = esUtil.jestClient.execute(search);
//            //获取返回结果的json字符串
//            String resultJson = jestResult.getSourceAsString();
//            //log.info("elasticsearch检索返回结果:\n" + resultJson);
//
//            //获取结果集
//            List<T> resultList = getSourceAsObjectList(resultJson, clazz);
//            //获取查询总记录数
//            int totalNum = jestResult.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt();
//
//            //封装分页结果并返回
//            Page resultPage = transListToPage(resultList);
//            resultPage.setCount(false); //不查询总记录数
//            resultPage.setTotal(totalNum); //总记录数
//            resultPage.setPageNum(pageNum); //当前页
//            resultPage.setPageSize(pageSize); //每页记录数
//            PageInfo pageInfo = new PageInfo<>(resultPage);
//            //log.info("elasticsearch分页检索成功，total=" + totalNum);
//            return pageInfo;
//        } catch (IOException e) {
//            log.error("查询elasticsearch出错", e);
//        }
//        return null;
//    }
//
//    /**
//     * 全文搜索 分页查询
//     *
//     * @param indexName 索引名称
//     * @param typeName 类型名称
//     * @param searchSourceBuilder 查询DSL构建类
//     * @param pageNum 当前页
//     * @param pageSize 每页记录数
//     * @return
//     */
//    public static <T> Map<String, Object> pageSearch2(String indexName, String typeName, SearchSourceBuilder searchSourceBuilder,
//            Map<String, String> fieldSorts, Class<T> clazz, int pageNum, int pageSize) {
//        int offSet = 0;
//        //计算分页起始行
//        if (pageNum > 0 && pageSize > 0) {
//            offSet = pageSize * (pageNum - 1);
//        }
//
//        //分页查询
//        searchSourceBuilder.from(offSet).size(pageSize);
//        //排序
//        if (fieldSorts != null && fieldSorts.size() > 0) {
//            for (Map.Entry<String, String> field : fieldSorts.entrySet()) {
//                if (null == field) {
//                    continue;
//                }
//                if (SortOrder.DESC.toString().equals(field.getValue())) {
//                    searchSourceBuilder.sort(field.getKey(), SortOrder.DESC);
//                } else {
//                    searchSourceBuilder.sort(field.getKey(), SortOrder.ASC);
//                }
//
//            }
//        }
//
//        //获取es的jest客户端连接
//        Search.Builder builder = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexName)
//                .addType(typeName);
//
//        Search search = builder.build();
//
//        //DSL语句
//        String query = searchSourceBuilder.toString();
//        log.info("indexName=" + indexName + ", typeName=" + typeName + ", elasticsearch搜索查询语句:\n" + query);
//
//        //获取es的jest客户端连接
//        try {
//            JestResult jestResult = esUtil.jestClient.execute(search);
//            //获取返回结果的json字符串
//            String resultJson = jestResult.getSourceAsString();
//            //log.info("elasticsearch检索返回结果:\n" + resultJson);
//
//            //获取结果集
//            List<T> resultList = getSourceAsObjectList(resultJson, clazz);
//            //获取查询总记录数
//            int totalNum = jestResult.getJsonObject().get("hits").getAsJsonObject().get("total").getAsInt();
//
//            //封装分页结果并返回(手动代码分页)
//            Map<String, Object> resultMap = new LinkedHashMap<>();
//            resultMap.put("total", totalNum); //总记录数
//            resultMap.put("pageNum", pageNum); //页码
//            resultMap.put("pageSize", pageSize); //每页记录数
//            resultMap.put("list", resultList); //数据集合
//            return resultMap;
//        } catch (IOException e) {
//            log.error("查询elasticsearch出错", e);
//        }
//        return null;
//    }
//
//    /**
//     * <p>
//     * 全文搜索 不分页
//     * </p>
//     *
//     * @param indexName
//     * @param typeName
//     * @param searchSourceBuilder
//     * @param clazz
//     * @return java.util.List<T>
//     * @author zqceng@iflytek.com
//     * @version 1.0
//     * @since 2018-11-21 12:56
//     */
//    public static <T> List<T> searchAll(String indexName, String typeName, SearchSourceBuilder searchSourceBuilder,
//            Map<String, String> fieldSorts, Class<T> clazz) {
//        searchSourceBuilder.size(MAX_SCROLL_SIZE);
//
//
//        //排序
//        if (fieldSorts != null && fieldSorts.size() > 0) {
//            for (Map.Entry<String, String> field : fieldSorts.entrySet()) {
//                if (null == field) {
//                    continue;
//                }
//                if (SortOrder.DESC.toString().equals(field.getValue())) {
//                    searchSourceBuilder.sort(field.getKey(), SortOrder.DESC);
//                } else {
//                    searchSourceBuilder.sort(field.getKey(), SortOrder.ASC);
//                }
//
//            }
//        }
//
//        //获取es的jest客户端连接
//        Search.Builder builder = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexName)
//                .addType(typeName).setParameter(Parameters.SCROLL, "1m");
//
//        Search search = builder.build();
//
//        log.info("indexName=" + indexName + ", typeName=" + typeName + ", elasticsearch搜索查询语句:\n" + searchSourceBuilder
//                .toString());
//
//        List<T> fullResultList = new ArrayList<>(); //最后的结果数组
//        try {
//            JestResult jestResult = esUtil.jestClient.execute(search);
//            //获取返回结果的json字符串
//            String resultJson = jestResult.getSourceAsString();
//            //log.info("elasticsearch检索返回结果:\n" + resultJson);
//            List<T> resultList = getSourceAsObjectList(resultJson, clazz);
//            fullResultList.addAll(resultList);
//            String scrollId = "";
//            if (jestResult.getJsonObject().get("_scroll_id") == null) {
//                return new ArrayList<T>();
//            } else {
//                scrollId = jestResult.getJsonObject().get("_scroll_id").getAsString();
//            }
//
//            //循环遍历
//            do {
//                SearchScroll scroll = new SearchScroll.Builder(scrollId, "5m").build();
//                jestResult = esUtil.jestClient.execute(scroll);
//                resultJson = jestResult.getSourceAsString();
//                resultList = getSourceAsObjectList(resultJson, clazz);
//                fullResultList.addAll(resultList);
//
//                if (jestResult.getJsonObject().get("_scroll_id") == null) {
//                    break;
//                } else {
//                    scrollId = jestResult.getJsonObject().getAsJsonPrimitive("_scroll_id").getAsString();
//                }
//            } while (resultList != null && resultList.size() > 0);
//            return fullResultList;
//        } catch (Exception e) {
//            log.error("查询elasticsearch出错", e);
//        }
//        return null;
//    }
//
//    /**
//     * <p>
//     * 全文搜索 不分页
//     * </p>
//     *
//     * @param indexName
//     * @param typeName
//     * @param clazz
//     * @return java.util.List<T>
//     * @author zqceng@iflytek.com
//     * @version 1.0
//     * @since 2018-11-21 12:56
//     */
//    public static <T> List<T> searchAll(String indexName, String typeName, String query, Class<T> clazz) {
//        //获取es的jest客户端连接
//        Search search = new Search.Builder(query).addIndex(indexName).addType(typeName).build();
//        try {
//            JestResult jestResult = esUtil.jestClient.execute(search);
//            //获取返回结果的json字符串
//            String resultJson = jestResult.getSourceAsString();
//            log.info("indexName=" + indexName + ", typeName=" + typeName + ", elasticsearch搜索查询语句:\n" + query);
//            //log.info("elasticsearch检索返回结果:\n" + resultJson);
//            //获取结果集
//            List<T> resultList = jestResult.getSourceAsObjectList(clazz);
//            //log.info("elasticsearch检索成功，total=" + resultList.size());
//            return resultList;
//        } catch (IOException e) {
//            log.error("查询elasticsearch出错", e);
//        }
//        return null;
//    }
//
//    /**
//     * <p>
//     * 转换list为page
//     * </p>
//     *
//     * @param srcList
//     * @return com.github.pagehelper.Page<T>
//     * @author zqceng@iflytek.com
//     * @version 1.0
//     * @since 2018-11-21 12:58
//     */
//    public static <T> Page<T> transListToPage(List srcList) {
//        String jsonString = JSONObject.toJSONString(srcList);
//        Page resultPage =  JSONObject.parseObject(jsonString, Page.class);
//        return resultPage;
//    }
//
//
//    /**
//     * @param indexName 索引名
//     * @return java.lang.Boolean
//     * @description //TODO 创建索引
//     * @author yuli
//     * @date 2019/1/14 9:32
//     * @version
//     **/
//    public static Boolean createIndex(String indexName) throws Exception {
//        if (StringUtils.isEmpty(indexName)) {
//            throw new Exception("索引名为空");
//        }
//        Boolean success = false;
//        try {
//            CreateIndex createIndex = new CreateIndex.Builder(indexName).build();
//            JestResult result = esUtil.jestClient.execute(createIndex);
//            if (null == result) {
//                throw new Exception("索引 " + indexName + " 创建失败");
//            }
//            if (result.isSucceeded()) {
//                success = true;
//            } else {
//                throw new Exception("创建索引失败" + result.getErrorMessage());
//            }
//        } catch (Exception e) {
//            throw new Exception("索引 " + indexName + " 创建失败", e);
//        }
//        return success;
//    }
//
//    /**
//     * @param indexName 索引名
//     * @return java.lang.Boolean
//     * @description //TODO 判断索引是否存在
//     * @author yuli
//     * @date 2019/1/12 16:14
//     * @version
//     **/
//    public static Boolean indicesExists(String indexName) throws Exception {
//        if (StringUtils.isEmpty(indexName)) {
//            throw new Exception("索引名为空！");
//        }
//        Boolean isExists = false;
//        try {
//            IndicesExists indicesExists = new IndicesExists.Builder(indexName).build();
//            JestResult result = esUtil.jestClient.execute(indicesExists);
//            if (result != null) {
//                isExists = result.isSucceeded();
//            } else {
//                throw new Exception("判断索引" + indexName + "是否存在失败，JestResult返回结果为null");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("判断索引" + indexName + "是否存在失败", e);
//        }
//        return isExists;
//    }
//
//
//    /**
//     * @description //TODO 空类型
//     * @author yuli
//     * @date 2019/1/12 17:07
//     **/
//    private static final String EMPTY_TYPE = "{}";
//
//    /**
//     * @param indexName 索引名
//     * @param typeName 类型名
//     * @return Boolean
//     * @description //TODO 判断索引下某类型是否存在
//     * @author yuli
//     * @date 2019/1/12 16:46
//     * @version
//     **/
//    public static Boolean typeExists(String indexName, String typeName) throws Exception {
//        if (StringUtils.isEmpty(indexName) || com.alibaba.csp.sentinel.util.StringUtil.isEmpty(typeName)) {
//            throw new Exception("索引名或类型名为空");
//        }
//        Boolean isExists = false;
//        try {
//            GetMapping.Builder builder = new GetMapping.Builder();
//            builder.addIndex(indexName).addType(typeName);
//            JestResult result = esUtil.jestClient.execute(builder.build());
//            if (result != null) {
//                if (result.getSourceAsObject(JsonObject.class) != null
//                        && !result.getSourceAsObject(JsonObject.class).toString().equals(EMPTY_TYPE)) {
//                    //System.err.println(result.getSourceAsObject(JsonObject.class).toString());
//                    isExists = true;
//                }
//            } else {
//                throw new Exception("判断索引名：" + indexName + ", 类型名：" + typeName + " 是否存在失败，JestResult返回结果为null");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("判断索引名：" + indexName + ", 类型名：" + typeName + " 是否存在失败", e);
//        }
//        return isExists;
//    }
//
//    /**
//     * @param indexName 索引名
//     * @param typeName 类型名
//     * @param mappingString 映射字符串
//     * @return java.lang.Boolean
//     * @description //TODO 建映射
//     * @author yuli
//     * @date 2019/1/12 17:17
//     * @version
//     **/
//    public static Boolean createTypeMapping(String indexName, String typeName, String mappingString) throws Exception {
//        if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(typeName) || StringUtils.isEmpty(mappingString)) {
//            throw new Exception("创建映射失败，索引名或类型名或映射字符串为空");
//        }
//        Boolean success = false;
//        try {
//            PutMapping.Builder builder = new PutMapping.Builder(indexName, typeName, mappingString);
//            JestResult result = esUtil.jestClient.execute(builder.build());
//            if (result == null || !result.isSucceeded()) {
//                if (result != null) {
//                    throw new Exception("判断索引名：" + indexName + ", 类型名：" + typeName + ", 映射 ： " + mappingString + " 创建失败 "+ result.getErrorMessage());
//                } else {
//                    throw new Exception("判断索引名：" + indexName + ", 类型名：" + typeName + ", 映射 ： " + mappingString + " 创建失败");
//                }
//            } else {
//                success = true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception("判断索引名：" + indexName + ", 类型名：" + typeName + ", 映射 ： " + mappingString + " 创建失败", e);
//        }
//        return success;
//    }
//
//    /**
//     * @param indexName 索引名
//     * @return java.lang.Boolean
//     * @description //TODO 删除索引
//     * @author yuli
//     * @date 2019/1/14 10:08
//     * @version
//     **/
//    public static Boolean deleteIndex(String indexName) throws Exception {
//        if (StringUtils.isEmpty(indexName)) {
//            throw new Exception("待删除的索引名为空");
//        }
//        Boolean success = false;
//        try {
//            DeleteIndex deleteIndex = new DeleteIndex.Builder(indexName).build();
//            JestResult result = esUtil.jestClient.execute(deleteIndex);
//            if (null == result) {
//                throw new Exception("删除索引失败，索引名：" + indexName);
//            }
//            if (result.isSucceeded()) {
//                success = true;
//            }
//        } catch (Exception e) {
//            throw new Exception("删除索引失败，索引名：" + indexName);
//        }
//        return success;
//    }
//
//    /**
//     * @param indexName 索引名
//     * @param typeName 类型名
//     * @param list 待插入数据
//     * @return java.lang.Boolean
//     * @description //TODO 批量插入数据
//     * @author yuli
//     * @date 2019/1/14 10:43
//     * @version
//     **/
//    public static <T> Boolean bulkIndex(String indexName, String typeName, List<T> list) throws Exception {
//        if (StringUtils.isEmpty(indexName) || StringUtils.isEmpty(typeName)) {
//            throw new Exception("带插入数据的索引或类型名为空");
//        }
//        Boolean success = false;
//        try {
//            if (list == null || list.isEmpty()) {
//                log.error("ES批量插数据，待插入数据为空");
//                return success;
//            }
//            Bulk.Builder bulk = new Bulk.Builder().defaultIndex(indexName).defaultType(typeName);
//            for (T obj : list) {
//                Index index = new Index.Builder(obj).build();
//                bulk.addAction(index);
//            }
//            BulkResult bulkResult = esUtil.jestClient.execute(bulk.build());
//            if (null == bulkResult) {
//                throw new Exception("ES批量插入数据失败, 索引名：" + indexName + " 类型名：" + typeName + " 待插数据总条数：" + list.size());
//            }
//            if (bulkResult.isSucceeded()) {
//                success = true;
//            }
//        } catch (Exception e) {
//            throw new Exception("ES批量插入数据失败, 索引名：" + indexName + " 类型名：" + typeName, e);
//        }
//        return success;
//    }
//
//
//    /**
//     * <p>
//     * 根据ES查询结果json串转为list集合
//     * </p>
//     *
//     * @param resultJson 查询结果集
//     * @param clazz 类
//     * @return java.util.List<T>
//     * @author zqceng@iflytek.com
//     * @version 1.0
//     * @since 2019-01-09 9:27
//     */
//    private static <T> List<T> getSourceAsObjectList(String resultJson, Class<T> clazz) {
//        List<T> list = new ArrayList<>();
//        resultJson = "[" + resultJson + "]";
//        try {
//            List<LinkedHashMap> maplist = JSON.parseArray(resultJson, LinkedHashMap.class);
//            Field[] sourceFields = clazz.getDeclaredFields();
//            //不规范数据处理
//            if (!(maplist==null||maplist.isEmpty())) {
//                for (LinkedHashMap map : maplist) {
//                    if (null != map) {
//                        for (Field srcField : sourceFields) {
//                            String srcName = srcField.getName();
//                            String fieldType = srcField.getType().getName();
//                            //空值数据处理
//                            if (map.get(srcName) == null || (map.get(srcName) instanceof String && ""
//                                    .equals(map.get(srcName)))) {
//                                if ("java.lang.Integer".equals(fieldType)) {
//                                    map.put(srcName, 0);
//                                } else if ("java.lang.Float".equals(fieldType)) {
//                                    map.put(srcName, 0f);
//                                } else if ("java.lang.Double".equals(fieldType)) {
//                                    map.put(srcName, 0d);
//                                } else if ("java.lang.Long".equals(fieldType)) {
//                                    map.put(srcName, 0L);
//                                } else if ("java.lang.Short".equals(fieldType)) {
//                                    map.put(srcName, 0);
//                                } else if ("java.lang.Byte".equals(fieldType)) {
//                                    map.put(srcName, 0);
//                                } else if ("java.lang.String".equals(fieldType)) {
//                                    map.put(srcName, "");
//                                }
//                            }
//
//                            //不规范数据处理
//                            String tempValue = map.get(srcName) != null ? map.get(srcName).toString() : "";
//                            if (StringUtil.isNotEmpty(tempValue)) {
//                                if ("java.lang.Integer".equals(fieldType)) {
//                                    map.put(srcName, Integer.parseInt(tempValue));
//                                } else if ("java.lang.Float".equals(fieldType)) {
//                                    map.put(srcName, Float.parseFloat(tempValue));
//                                } else if ("java.lang.Double".equals(fieldType)) {
//                                    map.put(srcName, Double.parseDouble(tempValue));
//                                } else if ("java.lang.Long".equals(fieldType)) {
//                                    map.put(srcName, Long.parseLong(tempValue));
//                                } else if ("java.lang.Short".equals(fieldType)) {
//                                    map.put(srcName, Short.parseShort(tempValue));
//                                } else if ("java.lang.Byte".equals(fieldType)) {
//                                    map.put(srcName, Byte.parseByte(tempValue));
//                                } else if ("java.lang.String".equals(fieldType)) {
//                                    map.put(srcName, String.valueOf(tempValue));
//                                }
//                            }
//                        }
//                        T obj = clazz.newInstance();
//                        BeanMap beanMap = BeanMap.create(obj);
//                        beanMap.putAll(map);
//                        list.add(obj);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.error("数据转换出错", e);
//            return null;
//        }
//        return list;
//    }
//}
