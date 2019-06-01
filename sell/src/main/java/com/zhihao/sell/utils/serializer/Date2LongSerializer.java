package com.zhihao.sell.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * Utility Class Date2LongSerializer, which is used to convert time to its proper representation.
 * Used as annotation.
 */

public class Date2LongSerializer extends JsonSerializer<Date> {

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    jsonGenerator.writeNumber(date.getTime() / 1000);
  }
}
