package org.meteorite.com.dto.log;


import lombok.Data;
import org.meteorite.com.dto.BaseLogQueryReq;


@Data
public class RemoveRecommendBlackListLogReq extends BaseLogQueryReq {

    private String agentName;


    private String operator;



}
