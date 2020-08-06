package org.meteorite.com.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_log_menu")
public class SystemLogMenu {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("log_type")
    private Integer logType;

    @TableField("log_source")
    private String logSource;

    @TableField("field_name")
    private String fieldName;

    @TableField("dict_name")
    private String dictName;

    @TableField("remark")
    private String remark;

    @TableField("is_del")
    private Integer isDel;



}
