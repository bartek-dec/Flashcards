package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class CardReader {

    private final String path = "./src/com/files/";

    private String fileName;
    private Map<String, String> cards;
    private Map<String, Integer> statistics;

    public CardReader() {
        this.cards = new LinkedHashMap<>();
        this.statistics = new HashMap<>();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> getCards() {
        return cards;
    }

    public Map<String, Integer> getStatistics() {
        return statistics;
    }

    public void readFile() {
        if (fileName != null) {
            File file = new File(path + fileName);

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String[] dataSet = scanner.nextLine().trim().split("\\t+");

                    if (dataSet[0] != null && dataSet[1] != null) {
                        cards.put(dataSet[0], dataSet[1]);

                        if (dataSet.length == 3) {
                            try {
                                int errors = Integer.parseInt(dataSet[2]);
                                statistics.put(dataSet[0], errors);
                            } catch (NumberFormatException e) {
                                System.out.println("Can't convert preliminary number of errors");
                            }
                        }
                    }
                }
                System.out.println(cards.size() + " cards have been loaded.\n");
            } catch (FileNotFoundException e) {
                System.out.println("File not found.\n");
            }
        } else {
            System.out.println("File not found.\n");
        }
    }
}
