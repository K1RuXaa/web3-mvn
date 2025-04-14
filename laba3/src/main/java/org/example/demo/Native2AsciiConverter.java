package org.example.demo;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class Native2AsciiConverter {
    public static void main(String[] args) throws IOException {
        Path srcDir = Paths.get("src/main/resource/localization");
        Path destDir = Paths.get("target/generated-resources/localization");

        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }

        Files.walk(srcDir)
                .filter(path -> path.toString().endsWith(".properties"))
                .forEach(path -> {
                    try {
                        Properties props = new Properties();
                        try (Reader reader = new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8)) {
                            props.load(reader);
                        }

                        Path relativePath = srcDir.relativize(path);
                        Path outputFile = destDir.resolve(relativePath);
                        Files.createDirectories(outputFile.getParent());

                        try (PrintWriter writer = new PrintWriter(
                                new OutputStreamWriter(Files.newOutputStream(outputFile), StandardCharsets.ISO_8859_1))) {
                            writer.println("# Converted by Native2AsciiConverter");
                            for (String key : props.stringPropertyNames()) {
                                String value = props.getProperty(key);
                                writer.println(escape(key) + "=" + escape(value));
                            }
                        }

                        System.out.println("Converted: " + path + " -> " + outputFile);
                    } catch (IOException e) {
                        System.err.println("Error converting file: " + path);
                        e.printStackTrace();
                    }
                });
    }

    private static String escape(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c > 127) {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                switch (c) {
                    case '\\': sb.append("\\\\"); break;
                    case '\t': sb.append("\\t"); break;
                    case '\n': sb.append("\\n"); break;
                    case '\r': sb.append("\\r"); break;
                    case '\f': sb.append("\\f"); break;
                    case '=': sb.append("\\="); break;
                    case ':': sb.append("\\:"); break;
                    case '#': sb.append("\\#"); break;
                    case '!': sb.append("\\!"); break;
                    default: sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
