package io.dkargo.munzi.board.error;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorDTO {

    private int code;
    private String message;
    private String timestamp;

    public ErrorDTO(DkargoException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
        this.timestamp = LocalDateTime.now().toString();
    }

    public static ErrorDTO BAD_REQUEST_ERROR() {
        return new ErrorDTO(ErrorCode.INVALID_FORMAT.getCode(), ErrorCode.INVALID_FORMAT.getMessage(), LocalDateTime.now().toString());
    }

    public ResponseEntity<ErrorDTO> toResponse() {
        return ResponseEntity
                .status(ErrorCode.enumOf(this.code).getStatus())
                .body(this);
    }
}
