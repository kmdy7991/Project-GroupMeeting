package com.groupmeeting.global.enums;

import lombok.Getter;

@Getter
public enum  ExceptionReturnCode {
    SUCCESS("1000", "요청에 성공하셨습니다."),

    // 토큰 관련 (1100 ~ 1150)
    NOT_EXIST_BEARER_SUFFIX("1100", "Bearer 접두사가 포함되지 않았습니다."),
    WRONG_JWT_TOKEN("1101", "잘못된 JWT 입니다."),
    EXPIRED_JWT_TOKEN("1102", "만료된 JWT 입니다."),
    EMPTY_AUTH_JWT("1103", "인증 정보가 비어있는 JWT 입니다."),
    EMPTY_USER("1104", "비어있는 유저 정보로 JWT를 생성할 수 없습니다."),
    INVALID_KEY("1105", "잘못된 KEY 입니다"),
    EMPTY_REFRESH("1106", "리프레시 토큰이 존재하지 않습니다."),
    BLACK_LIST_TOKEN("1107", "블랙 리스트에 등록된 토큰 입니다."),
    EMPTY_ACCESS("1108", "액세스 토큰이 존재하지 않습니다."),

    // 클라이언트 에러 (9000 ~ 9010)
    WRONG_PARAMETER("9000", "잘못된 파라미터 입니다."),
    METHOD_NOT_ALLOWED("9001", "허용되지 않은 메소드 입니다."),

    // 서버 에러 (9997 ~ 9999)
    INTERNAL_SERVER_ERROR("9998", "내부 서버 에러 입니다."),
    EXTERNAL_SERVER_ERROR("9999", "외부 서버 에러 입니다.");

    private final String code;
    private final String message;

    ExceptionReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
