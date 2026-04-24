package com.cloud.license.model.util;

import lombok.Getter;
import lombok.Setter;

/**
 * ErrorMessage.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Setter
@Getter
public class ErrorMessage {
    private String message;
    private String code;

    private String detail;


    public ErrorMessage(String message, String code, String detail) {
        super();
        this.message = message;
        this.code = code;
        this.detail = detail;
    }

    public ErrorMessage(String message, String detail) {
        super();
        this.message = message;
        this.detail = detail;
    }

    public ErrorMessage(String message) {
        this(message, "", "");
    }

}
