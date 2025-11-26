package com.projectinstagram.common.util;

import lombok.Getter;

/**
 * 루트경로 기준 상대주소 사용.
 * 사용할 디렉토리 명만 지정해서 사용.
 */
public enum ImageUrl {
    BOARD_URL("boardImages"),
    USER_URL("");
    @Getter
    private final String url;

    ImageUrl(String url){
        this.url = url;
    }

}
