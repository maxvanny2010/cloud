package com.cloud.license.model.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * ResponseWrapper.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Setter
@Getter
@JsonInclude(NON_NULL)
public class ResponseWrapper {
    private Object data;
    private Object metadata;
    private List<ErrorMessage> errors;

    public ResponseWrapper(Object data, Object metadata, List<ErrorMessage> errors) {
        super();
        this.data = data;
        this.metadata = metadata;
        this.errors = errors;
    }

}
