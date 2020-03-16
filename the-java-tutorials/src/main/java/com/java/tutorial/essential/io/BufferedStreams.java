package com.java.tutorial.essential.io;

import com.java.common.FilePathUtils;

import java.io.*;
import java.nio.file.Paths;

/**
 * <a href="https://docs.oracle.com/javase/tutorial/essential/io/buffers.html">Buffered Streams</a>
 *
 * @author feimen
 * @since 2020-03-15
 */
public class BufferedStreams {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(
                FilePathUtils.getFilePath("xanadu.txt")));
             BufferedWriter writer = new BufferedWriter(new FileWriter(
                     FilePathUtils.getFilePath("characteroutput.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
//                if (line.isEmpty()) {
//                    continue;
//                }
                writer.write(line);
                writer.newLine();
            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
