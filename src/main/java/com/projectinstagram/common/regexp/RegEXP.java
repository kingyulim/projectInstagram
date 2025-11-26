package com.projectinstagram.common.regexp;

import lombok.Getter;

@Getter
public class RegEXP {
    public static final String NICKNAME = "^[A-Za-z0-9](?:[A-Za-z0-9._]*[A-Za-z0-9])$";
    public static final String NAME = "^[가-힣a-zA-Z]+$";
    public static final String PASSWORD = "^[A-Za-z0-9!@#$%]+$";

}