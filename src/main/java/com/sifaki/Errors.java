package com.sifaki;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author SStorozhev
 * @since 5/16/2016
 */
@JsonSerialize(using = NameErrorCodeSerializer.class)
public enum Errors {

    UNDEFINED_ERROR(0),
    EVENT_ALREADY_EXIST(1);

    private int errorCode;

    Errors(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}

class NameErrorCodeSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors value, JsonGenerator generator,
                          SerializerProvider provider) throws IOException {
        generator.writeStartObject();
        generator.writeFieldName("errorName");
        generator.writeString(value.toString());
        generator.writeFieldName("errorCode");
        generator.writeNumber(value.getErrorCode());
        generator.writeEndObject();
    }
}
