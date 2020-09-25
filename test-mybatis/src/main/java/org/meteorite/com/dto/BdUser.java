package org.meteorite.com.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BdUser {
    /**
     * 手机号码
     */
    @CsvBindByName(column = "phone_number")
    private String phoneNumber;
    /**
     * 机构经纪人账号
     */
    @CsvBindByName(column = "org_account")
    private String orgAccount;
    @CsvBindByName(column = "device_id")
    private String registerId;
    /**
     * 经纪人ID
     */
    @CsvBindByName(column = "broker_id")
    private String uid;
    /**
     * 登入 退出 空串(什么场景呢?)
     */
    @CsvBindByName(column = "login_status")
    private String loginStatus;
    /**
     * 条数
     */
    @CsvBindByName(column = "record_num")
    private Integer recordNum;
    /**
     * 用户姓名
     */
    @CsvBindByName(column = "user_name")
    private String userName;
    /**
     * 用户等级
     */
    @CsvBindByName(column = "member_level")
    private String memberLevel;
    /**
     * 用户对应楼盘id
     */
    @CsvBindByName(column = "building_ids")
    private  String buildingIds;
}
