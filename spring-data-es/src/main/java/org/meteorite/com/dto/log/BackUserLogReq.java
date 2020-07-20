package org.meteorite.com.dto.log;


import lombok.Data;
import org.meteorite.com.dto.BaseLogQueryReq;


@Data
public class BackUserLogReq extends BaseLogQueryReq {




    private String account;


    private String backstageType;



}
