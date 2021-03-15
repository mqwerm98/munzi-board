package io.dkargo.munzi.board.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_FORMAT(400,10001,"Invalid Data Format (입력 데이터를 확인하십시오.)"),
    EMAIL_DUPLICATED(405, 10002, "Email Duplicated (이메일 중복)"),

    INTERNAL_SERVER_ERROR(500,10003,"Internal Server Error (서버에서 처리중 에러가 발생하였습니다.)"),
    JSON_PARSING_ERROR(500, 10004,"Json Data Parsing Error (데이터 파싱중 에러가 발생하였습니다.)"),
    CONNECTION_REFUSED_ERROR(500, 10005,"Connection Refused (서버와 연결을 확인하십시오.)"),
    RESOURCE_ACCESS_ERROR(500, 10006, "Network I/O Error (서버와 연결 방식을 확인하십시오.)"),

    USER_NOT_FOUND(404, 20001,"User not found (잘못된 유저 정보 입니다.)"),
    BOARD_NOT_FOUND(404, 20002, "Board not found (잘못된 게시판 정보 입니다.)");

    private int status;
    private int code;
    private String message;

    public static ErrorCode enumOf(int code) {
        return Arrays.stream(ErrorCode.values())
                .filter(t -> t.getCode() ==code)
                .findAny().orElse(null);
    }
}
