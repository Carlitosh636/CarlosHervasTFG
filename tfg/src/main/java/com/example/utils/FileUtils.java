package com.example.utils;

import com.example.model.DiagramData;
import com.example.model.GeneratorData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static DiagramData returnDiagramDataFromInputStream(String path) throws IOException {
        try(InputStream input = FileUtils.class.getResourceAsStream(path)){
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(input,DiagramData.class);
        }
        catch (IOException e){
            throw new IOException("The diagram file does not exist or couldn't be loaded",e);
        }
    }
    public static GeneratorData returnGeneratorDataFromInputStream(String path) throws IOException {
        try(InputStream input = FileUtils.class.getResourceAsStream(path)){
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(input,GeneratorData.class);
        }
        catch (IOException e){
            throw new IOException("The diagram file does not exist or couldn't be loaded",e);
        }
    }
}
