package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class Configure {
    private static final Logger logger = Logger.getLogger(Configure.class.getName());
    private static final String CONFIG_FILE_PATH = "config.json";

    private int maxTicketCapacity;
    private int totalTicketCapacity;
    private int ticketReleaseRate;
    private int ticketRetrievalRate;

    public Configure() {
        // Constructor can be empty or used for initial setup if needed
    }

    /**
     * Prompts the user for configuration inputs.
     *
     * @param scanner The Scanner instance to read user input.
     */
    public void getUserInputForConfiguration(Scanner scanner) {
        try {
            // Get and validate the maximum ticket capacity
            maxTicketCapacity = getValidPositiveInteger(scanner, "Enter maximum ticket capacity: ");

            // Get and validate the total ticket capacity
            totalTicketCapacity = getValidPositiveInteger(scanner, "Enter total ticket capacity: ");

            // Get and validate the ticket release rate
            ticketReleaseRate = getValidPositiveInteger(scanner, "Enter Ticket release rate (ms): ");

            // Get and validate the ticket retrieval rate
            ticketRetrievalRate = getValidPositiveInteger(scanner, "Enter ticket retrieval rate (ms): ");

            logger.info("Configuration input completed.");
        } catch (Exception e) {
            logger.severe("Error during configuration input: " + e.getMessage());
        }
    }

    /**
     * Saves the current configuration to the JSON file.
     */
    public void saveConfigurationToFile() {
        JsonObject configJson = new JsonObject();
        configJson.addProperty("maxTicketCapacity", maxTicketCapacity);
        configJson.addProperty("totalTicketCapacity", totalTicketCapacity);
        configJson.addProperty("ticketReleaseRate", ticketReleaseRate);
        configJson.addProperty("ticketRetrievalRate", ticketRetrievalRate);

        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            Gson gson = new Gson();
            writer.write(gson.toJson(configJson));
            logger.info("Configuration saved to " + CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.severe("Failed to save configuration: " + e.getMessage());
        }
    }

    /**
     * Loads the configuration from the JSON file.
     *
     * @return true if loading was successful, false otherwise.
     */
    public boolean loadConfigurationFromFile() {
        try (FileReader reader = new FileReader(CONFIG_FILE_PATH)) {
            Gson gson = new Gson();
            JsonObject configJson = gson.fromJson(reader, JsonObject.class);

            // Validate that all required fields are present
            if (configJson.has("maxTicketCapacity") && configJson.has("totalTicketCapacity") &&
                    configJson.has("ticketReleaseRate") && configJson.has("ticketRetrievalRate")) {

                maxTicketCapacity = configJson.get("maxTicketCapacity").getAsInt();
                totalTicketCapacity = configJson.get("totalTicketCapacity").getAsInt();
                ticketReleaseRate = configJson.get("ticketReleaseRate").getAsInt();
                ticketRetrievalRate = configJson.get("ticketRetrievalRate").getAsInt();

                logger.info("Configuration loaded from " + CONFIG_FILE_PATH);
                return true;
            } else {
                logger.warning("Configuration file is missing required fields.");
                return false;
            }
        } catch (IOException e) {
            logger.severe("Failed to load configuration: " + e.getMessage());
            return false;
        } catch (JsonParseException | IllegalStateException e) {
            logger.severe("Invalid JSON format in configuration file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Prompts the user for a valid positive integer input.
     *
     * @param scanner The Scanner instance to read user input.
     * @param prompt  The prompt message to display.
     * @return A valid positive integer.
     */
    private int getValidPositiveInteger(Scanner scanner, String prompt) {
        int value = -1;
        while (value <= 0) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                value = Integer.parseInt(input);
                if (value <= 0) {
                    System.out.println("Please enter a positive integer greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }
        return value;
    }

    // Getter methods to access configuration values
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTotalTicketCapacity() {
        return totalTicketCapacity;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getTicketRetrievalRate() {
        return ticketRetrievalRate;
    }
}
