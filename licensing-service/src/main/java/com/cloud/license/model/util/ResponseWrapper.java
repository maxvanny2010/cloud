package com.cloud.license.model.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * ResponseWrapper.
 *
 * @author legion
 * @version 5.0
 * @since 16.11.2022
 */
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

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return the metadata
     */
    public Object getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    /**
     * @return the errors
     */
    public List<ErrorMessage> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<ErrorMessage> errors) {
        this.errors = errors;
    }

}
