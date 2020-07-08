package org.meteorite.com.dto.log;


import lombok.Data;
import org.meteorite.com.dto.BaseLogQueryReq;

@Data
public class BmpFaceLogReq extends BaseLogQueryReq {

    private String businessType;

}
