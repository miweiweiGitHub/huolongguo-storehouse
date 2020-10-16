package org.meteorite.com.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author EX_052100260
 * @title: User
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-18 16:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLocal {

    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "age")
    private String age;
    @CsvBindByName(column = "phone")
    private String phone;
    @CsvBindByName(column = "country")
    private String country;


}
