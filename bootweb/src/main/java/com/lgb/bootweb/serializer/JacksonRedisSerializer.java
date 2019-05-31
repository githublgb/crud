package com.lgb.bootweb.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonRedisSerializer {
    private ObjectMapper objectMapper;

    public JacksonRedisSerializer()
    {
        this.objectMapper = new ObjectMapper();
    }

    public JacksonRedisSerializer(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz)
    {
        try
        {
            return bytes == null ? null : this.objectMapper.readValue(bytes, 0,
                    bytes.length, clazz);
        } catch (Exception ex) {
            throw new RuntimeException("Could not read JSON: " +
                    ex.getMessage(), ex);
        }
    }

    public <T> T deserialize(byte[] bytes, TypeReference<T> typeReference) {
        try {
            if (bytes != null)
                return this.objectMapper.readValue(bytes, 0, bytes.length,
                        typeReference);
        } catch (Exception ex) {
            throw new RuntimeException("Could not read JSON: " +
                    ex.getMessage(), ex);
        }
        return null;
    }

    public byte[] serialize(Object t)
    {
        try {
            return t == null ? null : this.objectMapper.writeValueAsBytes(t);
        } catch (Exception ex) {
            throw new RuntimeException("Could not write JSON: " +
                    ex.getMessage(), ex);
        }
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        //AssertUtil.isNotNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }
}
