package org.meteorite.com.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Data
@Slf4j
public class TagDeviceDto {
    private String registrationId;//设备ID
    private String phone;//电话号码
//    private String account;//案场宝账号
    private String type2;//使用者类型：0：恒大宝平台；1:恒大宝机构经纪人；2：项目运营（案场宝）；
    private Set<String> tagAdd;//增加tag
    private Set<String> tagRemove;//删除tag
    /**
     * true: 初始化设备tag, 先清理掉设备所有tag,alias，后操作tag/alias
     * false: 操作tag/alias
     */
    private boolean isInit;

//    /*极光服务记录*/
//    private PushRecord record = new PushRecord();
//
//    public String getAliasByPhone(){
////        if (StringUtils.isBlank(phone) && StringUtils.isNotBlank( account )){
////            return account;
////        }
//
//        if(StringUtils.isBlank(phone)){
//            return null;
//        }
//
//        try {
//            return MD5Util.encode(phone, CharEncoding.UTF_8);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return null;
//        }
//    }
//
//    public void addRecordRequestParam(String reqParam){
//        record.setReqParam(reqParam);
//    }
//    public void addRecordOperation(String operation){
//        record.setOperation(operation);
//    }
//    public void addRecordResponse(String messageId, String resCode, String errorMsg){
//        record.setPlatformMsgId(messageId);
//        record.setResCode(resCode);
//        record.setErrMsg(errorMsg);
//    }
//    public void addRecordResponse(String errorMsg){
//        record.setResCode("-1");
//        record.setErrMsg(errorMsg);
//    }
//
//    public void addRecordSendTime(String sendTime){
//        record.setSendTime(sendTime);
//    }
}
