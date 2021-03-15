package io.dkargo.munzi.board.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.munzi.board.error.ErrorCode;
import io.dkargo.munzi.board.error.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MvcResult;

@Service
public class TestService {

    @Autowired
    private ObjectMapper objectMapper;

    public ErrorDTO getError(MvcResult result) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), ErrorDTO.class);
    }

    public boolean isSuccess(MvcResult result) {
        return result.getResponse().getStatus() == HttpStatus.OK.value();
    }

    public boolean isErrorCode(MvcResult result, ErrorCode errorCode) throws Exception {
        ErrorDTO error = getError(result);
        return error.getCode() == errorCode.getCode();
    }

    public boolean isBadRequest(MvcResult result) throws Exception {
        ErrorDTO error = getError(result);
        return error.getCode() == ErrorCode.INVALID_FORMAT.getCode();
    }

}


