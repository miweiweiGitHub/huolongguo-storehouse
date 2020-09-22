package org.meteorite.com.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PersonDto {

    private String name;

    private String address;

}
