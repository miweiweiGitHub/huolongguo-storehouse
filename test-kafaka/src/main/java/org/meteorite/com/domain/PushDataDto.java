package org.meteorite.com.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@Slf4j
public class PushDataDto {

    //电话号码
    private Set<String> phone;
    //别名
    private Set<String> alias;

    // 通知标题
    private String title;
    // 通知子标题
    private String subTitle;
    // 通知内容
    private String content;
    // 指定发送时间
    private LocalDateTime sendTime;
    //发送编号
    private String refId;
    // 推送消息体
    private Map<String, String> extData = new HashMap<>();
    //使用者类型：0：恒大宝平台；1:恒大宝机构经纪人；2：项目运营（案场宝）;3:C端消费者；
    private String type2;




}
