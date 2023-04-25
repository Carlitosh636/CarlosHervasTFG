package com.example.utils;

import com.example.model.DiagramData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsondb.JsonDBTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Serializator {
    private static String dbFilesLocation = "src/main/resources/jsondb";
    private static final File diagramDataDir = new File("diagramData");
    private static String baseScanPackage = "com.example.model";
    private static final JsonDBTemplate dbTemplate = new JsonDBTemplate(dbFilesLocation,baseScanPackage);

    public static void initialize(){
        if(!dbTemplate.collectionExists(DiagramData.class)){
            dbTemplate.createCollection(DiagramData.class);
        }
    }
    public static void serializeAll() throws IOException {
        ObjectMapper objMapper = new ObjectMapper();
        File[] files = diagramDataDir.listFiles();
        int index = 1;
        if(files != null){
            for(File file : files){
                DiagramData diagramData = objMapper.readValue(file,DiagramData.class);
                diagramData.setId(String.valueOf(index));
                index+=1;
                dbTemplate.insert(diagramData);
                dbTemplate.save(diagramData,"diagrams");
            }
        }

    }
    public static void deserialize(){
        System.out.println(dbTemplate.findById("1", DiagramData.class).toString());
    }
}
