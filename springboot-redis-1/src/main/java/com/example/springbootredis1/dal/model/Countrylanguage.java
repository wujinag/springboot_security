package com.example.springbootredis1.dal.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Countrylanguage extends Model {

    private static final long serialVersionUID = 1L;

    @TableId("CountryCode")
    private String countrycode;

    @TableField("Language")
    private String language;

    @TableField("IsOfficial")
    private String isofficial;

    @TableField("Percentage")
    private Float percentage;


}
