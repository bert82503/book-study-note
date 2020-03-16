package com.java.common;

import java.io.File;
import java.nio.file.Paths;

/**
 * 文件路径辅助方法集。
 *
 * @since 2020-03-17
 */
public final class FilePathUtils {

    private static final String PROJECT_NAME = "the-java-tutorials";
    private static final String DATA_DIR_NAME = "data";

    public static String getFilePath(String fileName) {
        String currentWorkingDirectory = getCurrentWorkingDirectory();
        return currentWorkingDirectory + File.separatorChar + PROJECT_NAME
                + File.separatorChar + DATA_DIR_NAME + File.separatorChar + fileName;
    }

    private static String getCurrentWorkingDirectory() {
//        return System.getProperty("user.dir");

        // Java 7
        return Paths.get("").toAbsolutePath().toString();
    }

    private FilePathUtils() {
        throw new AssertionError("no instance");
    }
}
