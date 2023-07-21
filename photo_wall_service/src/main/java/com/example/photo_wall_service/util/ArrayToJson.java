package com.example.photo_wall_service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import static com.example.photo_wall_service.config.UploadConfig.SERVICE_HEAD;

/**
 * 拼接json
 */
public class ArrayToJson {
    public static String ArrayToJson(String[] value) throws JsonProcessingException {
        String[] key = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            int j = i + 1;
            key[i] = "photourl" + j;
        }

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < value.length; i++) {
            jsonObject.put(key[i], SERVICE_HEAD + "/img/" + value[i]);
        }

        ObjectMapper mapper = new ObjectMapper();
        Object json = mapper.readValue(jsonObject.toString(), Object.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

}
