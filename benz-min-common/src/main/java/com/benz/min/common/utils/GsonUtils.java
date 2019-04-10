package com.benz.min.common.utils;

import com.benz.min.common.annotations.GsonSkipAnnotation;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.pnmins.min.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * @ClassName GsonUtils
 * @Description 序列化
 * @Author qr
 * @Date 2019/3/7 16:28
 **/
public class GsonUtils {

    /**
     * 日志
     */
    private final static Logger logger = LoggerFactory.getLogger(GsonUtils.class);


    /**
     * 序列化排除项策略
     */
    private static ExclusionStrategy serializationExclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            GsonSkipAnnotation skipAnnotation = f.getAnnotation(GsonSkipAnnotation.class);
            return skipAnnotation == null ? false : skipAnnotation.serialize();
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            GsonSkipAnnotation skipAnnotation = clazz.getAnnotation(GsonSkipAnnotation.class);
            return skipAnnotation == null ? false : skipAnnotation.serialize();
        }
    };

    /**
     * 反序列化排除项策略
     */
    private static ExclusionStrategy deserializationExclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            GsonSkipAnnotation skipAnnotation = f.getAnnotation(GsonSkipAnnotation.class);
            return skipAnnotation == null ? false : skipAnnotation.deserialize();
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            GsonSkipAnnotation skipAnnotation = clazz.getAnnotation(GsonSkipAnnotation.class);
            return skipAnnotation == null ? false : skipAnnotation.serialize();
        }
    };

    /**
     * 反序列化Double类型处理器
     */
    private static JsonSerializer<Double> doubleSerializer = new JsonSerializer<Double>() {
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == src.longValue()) {
                return new JsonPrimitive(src.longValue());
            }
            return new JsonPrimitive(src);
        }
    };

    private enum GsonBuilderType {
        SERIALIZE, SERIALIZE_PRETTY, DESERIALIZE;
    }

    /**
     * json字符串转换成对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     * @author feng_wei
     */
    public static <T> T convertToObject(String jsonStr, Class<T> clazz) {
        T t = null;
        if (StringUtil.isEmpty(jsonStr)) {
            return t;
        }
        if ("{}".equalsIgnoreCase(StringUtil.trim(jsonStr))) {
            return t;
        }
        Gson gson = getBuilder(GsonBuilderType.DESERIALIZE).create();
        /*if(String.class.equals(clazz)){
            return (T)jsonStr;
        }*/
        try {
            t = gson.fromJson(jsonStr, clazz);
        } catch (JsonSyntaxException e) {
            logger.error("[GSon转换器],将json字符串转换为对象时出错，目标对象:{},源字符串:{}", clazz, jsonStr, e);
            throw new RuntimeException("[GSon转换器],将json字符串转换为对象时出错,目标对象:" + clazz + ",源字符串:" + jsonStr, e);
        }
        return t;
    }

    public static <T> T convertToObject(Reader reader, Class<T> clazz) {
        Gson gson = getBuilder(GsonBuilderType.DESERIALIZE).create();
        T t = null;
        if (reader == null) {
            return t;
        }
        try {
            t = gson.fromJson(reader, clazz);
        } catch (JsonSyntaxException e) {
            logger.error("[GSon转换器],将json字符串转换为对象时出错，目标对象:{}", clazz, e);
            throw new RuntimeException("[GSon转换器],将json字符串转换为对象时出错,目标对象:" + clazz, e);
        }
        return t;
    }

    /**
     * json字符串转换成对象,兼容Map或其他带有泛型的类型
     * <pre>示例:</pre>
     * <pre>Map&lt;String,DD&gt; aa = GsonUtils.convertToObject(a, <code>new TypeToken&lt;HashMap&lt;String,DD&gt;&gt;(){}</code>)</pre>
     *
     * @param jsonStr
     * @param typeToken
     * @return
     * @author feng_wei
     */
    public static <T> T convertToObject(String jsonStr, TypeToken<T> typeToken) {
//        if(String.class.equals(typeToken.getType())){
//            return (T)jsonStr;
//        }
        T t = null;
        if (StringUtil.isEmpty(jsonStr)) {
            return t;
        }
        if ("{}".equalsIgnoreCase(StringUtil.trim(jsonStr))) {
            return t;
        }
        Gson gson = getBuilder(GsonBuilderType.DESERIALIZE).create();
        try {
            t = gson.fromJson(jsonStr, typeToken.getType());
        } catch (JsonSyntaxException e) {
            logger.error("[GSon转换器],将json字符串转换为对象时出错，目标对象:{},源字符串:{}", typeToken.getType(), jsonStr, e);
            throw new RuntimeException("[GSon转换器],将json字符串转换为对象时出错,目标对象:" + typeToken.getType() + ",源字符串:" + jsonStr, e);
        }
        return t;
    }

    public static <T> T convertToObject(Reader reader, TypeToken<T> typeToken) {
        Gson gson = getBuilder(GsonBuilderType.DESERIALIZE).create();
        T t = null;
        try {
            t = gson.fromJson(reader, typeToken.getType());
        } catch (JsonSyntaxException e) {
            logger.error("[GSon转换器],将json字符串转换为对象时出错，目标对象:{}", typeToken.getType(), e);
            throw new RuntimeException("[GSon转换器],将json字符串转换为对象时出错,目标对象:" + typeToken.getType(), e);
        }
        return t;
    }


    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static void toJson(Object inParam, Appendable writer) {
        Gson gson = getBuilder(GsonBuilderType.SERIALIZE).create();
        gson.toJson(inParam, writer);
    }

    public static void toJson(Object inParam, Type typeOfSrc, Appendable writer) {
        if (typeOfSrc == null) {
            toJson(inParam, writer);
        } else {
            Gson gson = getBuilder(GsonBuilderType.SERIALIZE).create();
            gson.toJson(inParam, typeOfSrc, writer);
        }
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJson(Object inParam, boolean ignoreEmpty) {
        if (inParam == null) {
            if (ignoreEmpty) {
                return "";
            } else {
                return "{}";
            }
        } else {
            Gson gson = getBuilder(GsonBuilderType.SERIALIZE).create();
            return gson.toJson(inParam);
        }
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJson(Object inParam, Type bodyType) {
        return toJson(inParam, bodyType, false);
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJson(Object inParam, Type bodyType, boolean ignoreEmpty) {
        if (inParam == null) {
            if (ignoreEmpty) {
                return "";
            } else {
                return "{}";
            }
        } else {
            Gson gson = getBuilder(GsonBuilderType.SERIALIZE).create();
            return gson.toJson(inParam, bodyType);
        }
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJson(Object inParam, Type bodyType, HashSet<String> deserializationExclusionSet) {
        if (inParam == null) {
            return "{}";
        } else {
            Gson gson = getBuilder(GsonBuilderType.SERIALIZE, deserializationExclusionSet).create();
            return gson.toJson(inParam, bodyType);
        }
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJson(Object inParam) {
        return toJson(inParam, false);
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJson(Object inParam, HashSet<String> deserializationExclusionSet) {
        if (inParam == null) {
            return "{}";
        } else {
            Gson gson = getBuilder(GsonBuilderType.SERIALIZE, deserializationExclusionSet).create();
            return gson.toJson(inParam);
        }
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJsonPretty(Object inParam) {
        if (inParam == null) {
            return "{}";
        } else {
            return getBuilder(GsonBuilderType.SERIALIZE_PRETTY).create().toJson(inParam);
        }
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJsonPretty(Object inParam, HashSet<String> deserializationExclusionSet) {
        if (inParam == null) {
            return "{}";
        } else {
            return getBuilder(GsonBuilderType.SERIALIZE_PRETTY, deserializationExclusionSet).create().toJson(inParam);
        }
    }

    /**
     * 将对象转换为json字符串
     *
     * @param inParam
     * @return
     * @author feng_wei
     */
    public static String toJsonPretty(Object inParam, Type typeOfSrc) {
        if (inParam == null) {
            return "{}";
        } else {
            return getBuilder(GsonBuilderType.SERIALIZE_PRETTY).create().toJson(inParam, typeOfSrc);
        }
    }

    private static GsonBuilder getBuilder(GsonBuilderType gsonBuilderType, HashSet<String> exclusionSet) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        switch (gsonBuilderType) {
            case SERIALIZE: {
                gsonBuilder.addSerializationExclusionStrategy(serializationExclusionStrategy).disableHtmlEscaping();
                if (!CollectionUtils.isEmpty(exclusionSet)) {
                    gsonBuilder.addSerializationExclusionStrategy(new ExclusionWithSetStrategy(exclusionSet));
                }
                break;
            }
            case SERIALIZE_PRETTY: {
                gsonBuilder.addSerializationExclusionStrategy(serializationExclusionStrategy).disableHtmlEscaping().setPrettyPrinting();
                if (!CollectionUtils.isEmpty(exclusionSet)) {
                    gsonBuilder.addSerializationExclusionStrategy(new ExclusionWithSetStrategy(exclusionSet));
                }
                break;
            }
            case DESERIALIZE: {
                gsonBuilder.registerTypeAdapter(Double.class, doubleSerializer).addDeserializationExclusionStrategy(deserializationExclusionStrategy);
                if (!CollectionUtils.isEmpty(exclusionSet)) {
                    gsonBuilder.addDeserializationExclusionStrategy(new ExclusionWithSetStrategy(exclusionSet));
                }
                break;
            }
            default: {
                gsonBuilder.disableHtmlEscaping();
            }
        }

        return gsonBuilder;
    }

    /**
     * 获取
     *
     * @param gsonBuilderType
     * @return
     */
    private static GsonBuilder getBuilder(GsonBuilderType gsonBuilderType) {
        return getBuilder(gsonBuilderType, null);
    }

    private static class ExclusionWithSetStrategy implements ExclusionStrategy {

        private HashSet<String> exclusionSet;

        public ExclusionWithSetStrategy(HashSet<String> exclusionSet) {
            this.exclusionSet = exclusionSet;
        }

        public HashSet<String> getExclusionSet() {
            return exclusionSet;
        }

        public void setExclusionSet(HashSet<String> exclusionSet) {
            this.exclusionSet = exclusionSet;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            if (CollectionUtils.isEmpty(exclusionSet)) {
                return false;
            }
            if (exclusionSet.contains(f.getName())) {
                return true;
            }

            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
