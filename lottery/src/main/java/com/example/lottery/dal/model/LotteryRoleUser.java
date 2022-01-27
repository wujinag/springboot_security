package com.example.lottery.dal.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class LotteryRoleUser extends Model {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Integer userId;


}
