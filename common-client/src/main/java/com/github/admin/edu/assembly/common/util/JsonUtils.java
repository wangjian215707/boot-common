package com.github.admin.edu.assembly.common.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.entity.JsonArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-4-3
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        //空值不序列化
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public static <T> T toObject(String json, TypeReference valueTypeRef) {
        try {
            return (T) objectMapper.readValue(json, valueTypeRef);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> String toJson(T entity) {
        try {
            return objectMapper.writeValueAsString(entity);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> String toJsonEntity(JsonEntity<T> jsonEntity) {
        try {
            return objectMapper.writeValueAsString(jsonEntity);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> String toJsonArray(JsonArray<T> jsonArray) {
        try {
            return objectMapper.writeValueAsString(jsonArray);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> JsonEntity<T> jsonEntity(String jsonStr) {
      if(!StringUtils.isEmpty(jsonStr)){
          JsonEntity<T> entity = toObject(jsonStr, new TypeReference<JsonEntity<T>>() {
          });
          return entity;
      }
        return null;
    }

    public static <T> JsonArray<T> jsonArray(String jsonStr) {
        if(!StringUtils.isEmpty(jsonStr)){
            try {
                JsonArray<T> jsonArray = toObject(jsonStr, new TypeReference<JsonArray<T>>() {
                });
                return jsonArray;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> String toJson(List<T> list) {
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toCollection(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取对象
     * @param json
     * @param <T>
     * @return
     */
    public static <T>JsonArray<T> getJsonArray(String json){
        return toCollection(json, new TypeReference<JsonArray<T>>() {
        });
    }

    /**
     * 获取jsonEntity 对象
     * @param json
     * @param <T>
     * @return
     */
    public static <T>JsonEntity<T> getJsonEntity (String json){
        return toCollection(json, new TypeReference<JsonEntity<T>>() {
        });
    }


    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> json2map(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, Map<String, Object>> map = (Map<String, Map<String, Object>>) objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), map2pojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz)
            throws Exception {
        List<Map<String, Object>> list = (List<Map<String, Object>>) objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
                });
        List<T> result = new ArrayList<T>();
        for (Map<String, Object> map : list) {
            result.add(map2pojo(map, clazz));
        }
        return result;
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static <T>Future<JsonArray<T>> getAllAsyncArray(String jsonCode){
        JsonArray<T> jsonArray=null;
        if(!StringUtils.isEmpty(jsonCode)){
            jsonArray=jsonArray(jsonCode);
        }
        if(null==jsonArray){
            jsonArray=new JsonArray<>();
        }
        return new AsyncResult<>(jsonArray);
    }

    public static <T>Future<JsonEntity<T>> getAllAsyncEntity(String jsonCode){
        JsonEntity<T> jsonEntity=null;
        if(!StringUtils.isEmpty(jsonCode)){
            jsonEntity=jsonEntity(jsonCode);
        }
        if(null==jsonEntity){
            jsonEntity=new JsonEntity<>();
        }
        return new AsyncResult<>(jsonEntity);
    }

    public static <T>Future<List<T>> getAllAsyncByJsonArray(String jsonCode){
        List<T> list=null;
        JsonArray<T> jsonArray=jsonArray(jsonCode);
        if(null!=jsonArray){
            list=jsonArray.getDataList();
        }
        if(null==list){
            list=new ArrayList<>();
        }
        return new AsyncResult<>(list);
    }

    public static <T>Future<T> getAllAsyncByJsonEntity(String jsonCode){
        T t=null;
        JsonEntity<T> jsonEntity=jsonEntity(jsonCode);
        if(null!=jsonEntity){
            t=jsonEntity.getData();
        }
        return new AsyncResult<>(t);
    }

}
