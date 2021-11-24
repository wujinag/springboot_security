package com.example.lottery.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultResp<T> implements Serializable {

    private static final long serialVersionUID = 8140875256110329513L;

    private String code;

    private String msg;

    private T result;
}
