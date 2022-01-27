package com.example.lottery.dal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Transient;

import java.util.Set;

/**
 * <p>
 * 
 * </p>
 *
 * @author wuj
 * @since 2022-01-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryUser extends Model {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String account;

    private String description;

    private String password;

    private String name;
    
    @Transient
    @TableField(exist = false)
    private Set<String> roles;

}
