package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class CardWriter {

    private final String filePath = "./src/com/files/";
    private final String logPath = "./src/com/logs/";
    private final StringBuilder builder;

    private Map<String, String> cards;
    private List<String> logs;
    private Map<String, Integer> statistics;
    private String fileName;
    private String logName;

    public CardWriter() {
        builder = new StringBuilder();
    }

    public void setCards(Map<String, String> cards) {
        this.cards = cards;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public void setStatistics(Map<String, Integer> statistics) {
        this.statistics = statistics;
    }

    public void writeCards() {
        if (fileName != null) {
            try (PrintWriter writer = new PrintWriter(filePath + fileName)) {
                if (cards != null) {
                    cards.entrySet().stream()
                            .forEach(entry -> {
                                builder.append(entry.getKey());
                                builder.append("\t");
                                builder.append(entry.getValue());
                                if (statistics != null) {
                                    if (statistics.containsKey(entry.getKey())) {
                                        builder.append("\t");
                                        builder.append(statistics.get(entry.getKey()));
                                    }
                                }
                                writer.println(builder.toString());
                                builder.setLength(0);
                            });
                    System.out.println(cards.size() + " cards have been saved.\n");
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.\n");
            }
        } else {
            System.out.println("File not found.\n");
        }
    }

    public void saveLogs() {
        if (logName != null) {
            try (PrintWriter writer = new PrintWriter(logPath + logName)) {
                if (logs != null) {
                    int index = 1;
                    for (String element : logs) {
                        builder.append(index);
                        builder.append(")");
                        builder.append("\t");
                        builder.append(element);
                        writer.println(builder.toString());
                        builder.setLength(0);
                        index++;
                    }
                    System.out.println("The log has been saved\n");
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.\n");
            }
        } else {
            System.out.println("File not found.\n");
        }
    }
}
