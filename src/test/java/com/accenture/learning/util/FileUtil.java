package com.accenture.learning.util;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    public static String readFromFileToString(String filePath) throws IOException {

        File resource = new ClassPathResource(filePath).getFile();
        System.out.println(resource.toPath().toAbsolutePath());
        byte[] byteArray = Files.readAllBytes(resource.toPath());
        return new String(byteArray);
    }
}