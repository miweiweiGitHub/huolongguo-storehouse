package org.meteorite.com.dto.log;


import lombok.Data;
import org.meteorite.com.dto.BaseLogQueryReq;


@Data
public class HouseSourceOperatorLogReq extends BaseLogQueryReq {


    private String projectName;

    private String roomName;




}
