package com.java.tutorial.essential.io;

import com.java.common.FilePathUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

/**
 * <a href="https://docs.oracle.com/javase/tutorial/essential/io/scanning.html">Scanning</a>
 *
 * @author feimen
 * @since 2020-03-17
 */
public class Scanning {
    public static void main(String[] args) throws FileNotFoundException {
        // Breaking Input into Tokens
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(
                FilePathUtils.getFilePath("xanadu.txt"))))) {
            // To use a different token separator, invoke useDelimiter(), specifying a regular expression.
            scanner.useDelimiter(System.lineSeparator());
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        }

        // Translating Individual Tokens
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(
                FilePathUtils.getFilePath("usnumbers.txt"))))) {
            scanner.useLocale(Locale.US);
            double sum = 0.0D;
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    sum += scanner.nextDouble();
                } else {
                    // ignore
                    scanner.next();
                }
            }
            System.out.println("sum = " + sum);
        }
    }
}
