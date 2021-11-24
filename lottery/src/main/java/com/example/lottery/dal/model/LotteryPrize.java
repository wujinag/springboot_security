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
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LotteryPrize extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 活动ID
     */
    private Integer lotteryId;

    /**
     * 奖品名称
     */
    private String prizeName;

    /**
     * 奖品类型， -1-谢谢参与、1-普通奖品、2-唯一性奖品
     */
    private Integer prizeType;

    /**
     * 总库存
     */
    private Integer totalStock;

    /**
     * 可用库存
     */
    private Integer validStock;

    /**
     * 备注
     */
    private String remark;


}
