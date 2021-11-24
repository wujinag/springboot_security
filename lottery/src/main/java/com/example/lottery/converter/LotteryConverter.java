package com.example.lottery.converter;


import com.example.lottery.controller.vo.LotteryItemVo;
import com.example.lottery.service.dto.DoDrawDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LotteryConverter {

    LotteryItemVo dto2LotteryItemVo(DoDrawDto drawDto);

}
