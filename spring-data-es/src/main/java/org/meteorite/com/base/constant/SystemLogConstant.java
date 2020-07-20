package org.meteorite.com.base.constant;

/**
 * <p>
 * 消息服务常量类
 * </p>
 *
 * @author laixiangqun 2019/3/12
 */
public class SystemLogConstant {


    public static class ResultMessage {

        // P60001	参数错误
        public static final String ERROR_NOT_VALID_PARAM = "P60001";
        // P60002	无匹配脱敏权限校验码
        public static final String ERROR_NO_MATCH_DESENSITIZED_CODE = "P60002";
        // P60003	json格式异常
        public static final String ERROR_JSON_FORMAT = "P60003";
        // P60004	日志来源类型不存在
        public static final String ERROR_LOG_SOURCE_TYPE = "P60004";
        // P60005	获取用户信息异常
        public static final String ERROR_GET_MID_PLAT_USER_FUNCTION_INFO = "P60005";

        public static final String ERROR_MESSAGE_P60001 = "error.not_valid_param";
        public static final String ERROR_MESSAGE_P60002 = "error.no_match_desensitized_code";
        public static final String ERROR_MESSAGE_P60003 = "error.json_format";
        public static final String ERROR_MESSAGE_P60004 = "error.log_source_type";
        public static final String ERROR_MESSAGE_P60005 = "error.get_mid_plat_user_function_info";

    }


    public static class QueryCondition {

        public static final String PHONE = "phone"; //手机号
        public static final String KEY_WORD = "keyWord"; //关键字
        public static final String REQUEST_PARAM = "requestParam"; //请求参数
        public static final String REQUEST_TYPE = "requestType"; //请求类型
        public static final String OPERATOR_NO = "operatorNo"; //操作账号
        public static final String OPERATOR_TYPE = "operatorType"; //操作类型

        /**
         * test_log
         */
        public static final String TEST_TIME = "testTime"; //测试时间

        /**
         * login_log
         */
        public static final String LOGIN_TIME = "loginTime"; //用户登录时间
        /**
         * sms_log
         */
        public static final String SEND_TIME = "sendTime"; //短信发送时间
        /**
         * third_part_in_log
         * third_part_out_log
         * unified_certification_log
         */
        public static final String REQUEST_TIME = "requestTime"; //请求时间


        /**
         * bmp_face_log
         */
        public static final String BUSINESS_TYPE = "businessType"; //业务类型

        /**
         * back_user_log
         */
        public static final String ACCOUNT = "account"; //账号
        public static final String BACKSTAGE_TYPE = "backstageType"; //后台类型

        /**
         * back_user_log
         * account_handler_log
         */
        public static final String OPERATOR_TIME = "operatorTime"; //操作时间

        /**
         * account_handler_log
         */
        public static final String PROCESSED_ACCOUNT = "processedAccount"; //被处理账号

        /**
         * open_house_source_operator_log
         * online_house_source_operator_log
         */
        public static final String PROJECT_NAME = "projectName"; //项目名称
        public static final String ROOM_NAME = "roomName"; //房间名称

        /**
         * remove_recommend_black_list_log
         */
        public static final String AGENT_NAME = "agentName"; //经纪人姓名
        public static final String OPERATOR = "operator"; //操作人


    }

    public static class QueryIndex {

        //登录日志
        public static final String LOGIN_LOG = "login_log_index_*";
        //短信日志
        public static final String SMS_LOG = "sms_log_index_*";
        //第三方日志（出）
        public static final String THIRD_OUT_LOG = "third_out_log_index_*";
        //第三方日志（入）
        public static final String THIRD_IN_LOG = "third_in_log_index_*";
        //统一认证接口
        public static final String UNIFIED_CERTIFICATE_LOG = "unified_certificate_log_index_*";
        //BMP对接日志
        public static final String BMP_FACE_LOG = "bmp_face_log_index_*";
        //后台用户日志
        public static final String BACK_USER_LOG = "back_user_log_index_*";
        //账号处理日志
        public static final String ACCOUNT_HANDLER_LOG = "account_handler_log_index_*";
        //解除推荐黑名单
        public static final String REMOVE_RECOMMEND_LOG = "remove_recommend_log_index_*";
        //测试日志
        public static final String TEST_LOG = "test_log_index_*";

    }

    public static class DesensitizedField {

        // 手机号脱敏权限code
        public static final String PHONE_MASK_CODE = "PHONE_MASK";
        // 名字号脱敏权限code
        public static final String NAME_MASK_CODE = "NAME_MASK";
        // 【用户姓名、手机号、身份证号、银行卡号脱敏查看】权限code
        public static final String SENSITIVE_MASK_CODE = "SENSITIVE_MASK";

        /************  统一认证日志 请求参数 返回结果 敏感字段名  *************/
        //phone 手机号 属性字段名
        public static final String PHONE_FIELD = "phone";
        // name 名字 属性字段名
        public static final String NAME_FIELD = "name";
        // id_card 证件号 属性字段名
        public static final String ID_CARD_FIELD = "id_card";
        // idCardNo 证件号 属性字段名
        public static final String ID_CARD_NO_FIELD = "idCardNo";
        // bank_card 银行卡号 属性字段名
        public static final String BANK_CARD_FIELD = "bank_card";

    }

}
