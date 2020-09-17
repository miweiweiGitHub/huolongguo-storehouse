package org.meteorite.com.base;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.meteorite.com.base.constant.LocalConstant;
import org.meteorite.com.dto.DictDto;
import org.meteorite.com.dto.UserDynamicParam;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author EX_052100260
 * @title: LocalUtils
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-15 9:51
 */
@Slf4j
public class LocalUtils {


    public static void main(String[] args) {
        String content = "{user_name}你好，你的会员等级是{user_level}";
        UserDynamicParam userDynamicParam = new UserDynamicParam();
        userDynamicParam.setLevel("至尊会员");
        userDynamicParam.setName("对的");
        userDynamicParam.setPhone("13570841422");
        userDynamicParam.setUid("1285033685434781697");
        long start = System.currentTimeMillis();
        for (int i = 0; i <64 ; i++) {
            String s = handContentUserParamMulti(content, userDynamicParam, getMatchedUserParamDict());
        }
        System.out.println(System.currentTimeMillis()-start);

//        final List<String> aliasList = new ArrayList<>(alias);
//        int sendSize = "JSONObject.toJSONString(channel.getMessage())".length();
//        int pages = ToolUtils.getPages(aliasList, sendSize);
//        log.info("PUSH SEND_BY_MULTI 需要循环发送 {} 次",pages);
//        for (int i = 0; i < pages; i++) {
//            final Integer current = i;
//            List<String> subList = ToolUtils.getSubList(aliasList, current, pages, sendSize);
//            subList.stream().forEach(item -> {
//                PushSendData multiSendData = (PushSendData) ToolUtils.compareCopyBean(pushSendData, new PushSendData());    // 发送短信实体
//                Map<String, String> multiData = new HashMap<>();    // 消息体
//                multiData.putAll(data);
//                multiSendData.setExtData(multiData);
//                multiSendData.setPhone(new HashSet<>(Arrays.asList(item)));    // 手机号码
//                multiSendData.setContent(this.handContentUserParamMulti(content, phoneMap.get(item), listUserParamDict));    // 替换用户动态参数
//                // 调用别名发送接口
//                sendList.add(multiSendData);
//            });
//            pushMessageData.setOperation(PushMessageData.Operation.SEND_BY_MULTI);
//            pushMessageData.setMultiData(pushMultiSendData);
//
//            messageProducer.send(msgPushTopic, pushMessageData);
//            log.info("PUSH SEND_BY_MULTI... 第 {} 次，taskId：{}，alias：{}",current, channel.getTaskId(), alias);
//
//        }


    }

    public static int getChineseNum(String content) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        while(m.find())
            count++;
        return count;
    }


    public static String handContentUserParamMulti(String content, UserDynamicParam userDynamicParam, List<DictDto> listUserParamDict) {
        if (StringUtils.isEmpty(content) || ObjectUtils.isEmpty(userDynamicParam) || CollectionUtils.isEmpty(listUserParamDict)) {
            return content;
        }

        Method method;
        for (DictDto dict : listUserParamDict) {
            if (content.contains(LocalConstant.SYMBOL_BRACE_LEFT.concat(dict.getDictCode()).concat(LocalConstant.SYMBOL_BRACE_RIGHT))) {
                try {
                    method = BeanUtils.findDeclaredMethod(UserDynamicParam.class, "get".concat(dict.getValue().substring(0, 1).toUpperCase()).concat(dict.getValue().substring(1)));
                    if (method == null) {
                        continue;
                    }
                    content = content.replace(LocalConstant.SYMBOL_BRACE_LEFT.concat(dict.getDictCode()).concat(LocalConstant.SYMBOL_BRACE_RIGHT), String.valueOf(method.invoke(userDynamicParam, null)));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return content;
    }


    public static List<DictDto> getMatchedUserParamDict() {
        DictDto dictDto1 = new DictDto();
        DictDto dictDto2 = new DictDto();

        dictDto1.setAlias("用户姓名");
        dictDto1.setDictCode("user_name");
        dictDto1.setDictType("2");
        dictDto1.setName("user_name");
        dictDto1.setParentCode("user");
        dictDto1.setSort(1);
        dictDto1.setValue("name");

        dictDto2.setAlias("会员等级");
        dictDto2.setDictCode("user_level");
        dictDto2.setDictType("2");
        dictDto2.setName("user_level");
        dictDto2.setParentCode("user");
        dictDto2.setSort(2);
        dictDto2.setValue("level");

        List<DictDto> listUserParamDict = new ArrayList<>();
        listUserParamDict.add(dictDto1);
        listUserParamDict.add(dictDto2);
        return listUserParamDict;
    }


}
