package com.example;

import java.io.*;

public class Serializator {
    public static void serialize(Serializable obj, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        try (ObjectOutputStream outputStream
                     = new ObjectOutputStream(
                new FileOutputStream(outputFile))) {
            outputStream.writeObject(obj);
        }
    }
    public static Object deserialize(String inputPath)
            throws IOException, ClassNotFoundException
    {
        File inputFile = new File(inputPath);
        try (ObjectInputStream inputStream
                     = new ObjectInputStream(
                new FileInputStream(inputFile))) {
            return inputStream.readObject();
        }
    }
}
