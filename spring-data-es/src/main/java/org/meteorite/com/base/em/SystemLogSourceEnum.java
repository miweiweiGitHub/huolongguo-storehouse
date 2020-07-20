package org.meteorite.com.base.em;

import java.util.ArrayList;
import java.util.List;


public enum SystemLogSourceEnum {

    LOGIN_LOG(1,"登录日志","LOGIN_LOG_TOPIC","login_log_index_"),
    SMS_LOG(2,"短信日志","SMS_LOG_TOPIC","sms_log_index_"),
    THIRD_PART_OUT_LOG(3,"第三方日志（出）","THIRD_PART_OUT_LOG_TOPIC","third_out_log_index_"),
    THIRD_PART_IN_LOG(4,"第三方日志（入）","THIRD_PART_IN_LOG_TOPIC","third_in_log_index_"),
    UNIFIED_CERTIFICATION_LOG(5,"统一认证接口","UNIFIED_CERTIFICATION_LOG_TOPIC","unified_certificate_log_index_"),
    BMP_FACE_LOG(6,"BMP对接日志","BMP_FACE_LOG_TOPIC","bmp_face_log_index_"),
    BACK_USER_LOG(7,"后台用户日志","BACK_USER_LOG_TOPIC","back_user_log_index_"),
    ACCOUNT_HANDLER_LOG(8,"账号处理日志","ACCOUNT_HANDLER_LOG_TOPIC","account_handler_log_index_"),
//    ONLINE_HOUSE_SOURCE_OPERATOR_LOG(9,"在线房源操作日志","ONLINE_HOUSE_SOURCE_OPERATOR_LOG_TOPIC"),
//    OPEN_HOUSE_SOURCE_OPERATOR_LOG(10,"开盘房源操作日志","OPEN_HOUSE_SOURCE_OPERATOR_LOG_TOPIC"),
    REMOVE_RECOMMEND_BLACK_LIST_LOG(11,"解除推荐黑名单","REMOVE_RECOMMEND_BLACK_LIST_LOG_TOPIC","remove_recommend_log_index_"),
//    TEST_LOG(12,"test log","TEST_LOG_TOPIC");
    TEST_LOG(12,"test log","SMS_LOG_TOPIC_TEST","test_log_index_");

    //日志类型值 用于请求路径识别
    private int type;
    //日志类型来源
    private String source;
    //日志类型 MQ 的topic
    private String topic;
    //日志类型 索引前缀
    private String indexPrefix;

    SystemLogSourceEnum(int type, String source, String topic, String indexPrefix){
        this.type = type;
        this.source = source;
        this.topic = topic;
        this.indexPrefix = indexPrefix;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getIndexPrefix() {
        return indexPrefix;
    }

    public void setIndexPrefix(String indexPrefix) {
        this.indexPrefix = indexPrefix;
    }

    /**
     * 返回需要清理历史数据的日志类型
     * @return
     */
    public static List<SystemLogSourceEnum> getCleanHistoryEnum(){
            List<SystemLogSourceEnum> list = new ArrayList<>();
        list.add(SystemLogSourceEnum.THIRD_PART_IN_LOG);
        list.add(SystemLogSourceEnum.THIRD_PART_OUT_LOG);
        list.add(SystemLogSourceEnum.UNIFIED_CERTIFICATION_LOG);
        list.add(SystemLogSourceEnum.BMP_FACE_LOG);
        list.add(SystemLogSourceEnum.TEST_LOG);
        return list;
    }

    /**
     * 返回 不需要鉴权脱敏 的日志类型
     * @return
     */
    public static List<SystemLogSourceEnum> getNoDesensitizedLogSourceEnum(){
        List<SystemLogSourceEnum> list = new ArrayList<>();

        list.add(SystemLogSourceEnum.THIRD_PART_OUT_LOG);
        list.add(SystemLogSourceEnum.THIRD_PART_IN_LOG);
        list.add(SystemLogSourceEnum.BMP_FACE_LOG);
        list.add(SystemLogSourceEnum.ACCOUNT_HANDLER_LOG);
        list.add(SystemLogSourceEnum.TEST_LOG);
        return list;
    }

    /**
     * 返回 需要鉴权脱敏 的日志类型
     * @return
     */
    public static List<SystemLogSourceEnum> getDesensitizedLogSourceEnum(){
        List<SystemLogSourceEnum> list = new ArrayList<>();
        list.add(SystemLogSourceEnum.LOGIN_LOG);
        list.add(SystemLogSourceEnum.SMS_LOG);
        list.add(SystemLogSourceEnum.UNIFIED_CERTIFICATION_LOG);
        list.add(SystemLogSourceEnum.BACK_USER_LOG);
        list.add(SystemLogSourceEnum.REMOVE_RECOMMEND_BLACK_LIST_LOG);

        list.add(SystemLogSourceEnum.TEST_LOG);
        return list;
    }

}
