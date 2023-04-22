package com.litarary.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

public class MultiValueMapperUtils {

    public static MultiValueMap<String, String> convert(ObjectMapper objectMapper, Object obj) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> maps = objectMapper.convertValue(obj, new TypeReference<Map<String, String>>() {
        });
        parameters.setAll(maps);

        return parameters;
    }
}
