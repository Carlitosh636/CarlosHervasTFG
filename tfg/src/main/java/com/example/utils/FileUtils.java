package com.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    private static final ObjectMapper objMapper = new ObjectMapper();
    public static <T> T returnObjectFromInputStream(String path, Class<T> valueClass) throws IOException {
        try(InputStream input = FileUtils.class.getResourceAsStream(path)){
            return objMapper.readValue(input,valueClass);
        }
        catch (IOException e){
            throw new IOException("The diagram file does not exist or couldn't be loaded",e);
        }
    }
}
