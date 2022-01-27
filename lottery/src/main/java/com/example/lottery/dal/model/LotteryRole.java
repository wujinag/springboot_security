package com.example.lottery.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuj
 * @since 2022-01-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryRole extends Model {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String description;

    private Long createdTime;

    private String name;

    private String role;


}
