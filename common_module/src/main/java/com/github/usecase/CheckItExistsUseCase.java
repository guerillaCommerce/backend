package com.github.usecase;

import com.github.common.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
public class CheckItExistsUseCase {

    private <T> void checkItExists(T data, String errorMessage) {
        if (data == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, errorMessage);
        }

        if (data instanceof String && ((String) data).isEmpty()) {
            throw new GlobalException(HttpStatus.NOT_FOUND, errorMessage);
        }

        if (data instanceof Collection && ((Collection<?>) data).isEmpty()) {
            throw new GlobalException(HttpStatus.NOT_FOUND, errorMessage);
        }

        if (data instanceof Map && ((Map<?, ?>) data).isEmpty()) {
            throw new GlobalException(HttpStatus.NOT_FOUND, errorMessage);
        }

        // 배열 검사
        if (data.getClass().isArray() && ((Object[]) data).length == 0) {
            throw new GlobalException(HttpStatus.NOT_FOUND, errorMessage);
        }
    }

    public <T> void exec(T data, String errorMessage) {
        this.checkItExists(data, errorMessage);
    }
}
