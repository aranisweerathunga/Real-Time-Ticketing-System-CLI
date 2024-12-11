package org.example;

import java.io.File;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final String CONFIG_FILE_PATH = "config.json";
    private static boolean isRunning = true; // Flag to control the menu loop

    /**

     The main entry point of the Ticket Booking System program.
     This method logs a message to indicate the program has started, then enters a menu loop where the user can configure the system, start the system, or stop it.
     The program uses a Scanner to read user input and a switch statement to handle the different choices.
     If an unexpected error occurs, the program logs a severe message with the error message. */

    public static void main(String[] args) {
        logger.info("Starting the Ticket Booking System...");

        try (Scanner scanner = new Scanner(System.in)) {
            Configure config = null; // Holds the loaded or created configuration

            while (isRunning) {
                System.out.println("\n--- Ticket Booking System Menu ---");
                System.out.println("1. Configure System");
                System.out.println("2. Start System");
                System.out.println("3. Stop System");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1": // Configure System and immediately start
                        config = handleConfiguration(scanner);
                        startTicketBooking(config);
                        isRunning = false; // Exit the menu loop after starting the system
                        break;

                    case "2": // Start Ticket Booking
                        if (config == null) {
                            config = loadConfigurationOrPrompt(scanner);
                        }
                        startTicketBooking(config);
                        break;

                    case "3": // Stop System
                        isRunning = false;
                        System.out.println("System stopped. Exiting...");
                        logger.info("System stopped by user.");
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            logger.severe("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static Configure handleConfiguration(Scanner scanner) {
        Configure config;
        if (new File(CONFIG_FILE_PATH).exists()) {
            System.out.println("Configuration file exists.");
            if (askUserToLoadPreviousConfiguration(scanner)) {
                config = new Configure();
                if (!config.loadConfigurationFromFile()) {
                    System.out.println("Failed to load previous configuration. Enter new configuration.");
                    config.getUserInputForConfiguration(scanner);
                    config.saveConfigurationToFile();
                }
            } else {
                config = new Configure();
                config.getUserInputForConfiguration(scanner);
                config.saveConfigurationToFile();
            }
        } else {
            System.out.println("No configuration file found. Enter new configuration.");
            config = new Configure();
            config.getUserInputForConfiguration(scanner);
            config.saveConfigurationToFile();
        }
        return config;
    }

    private static Configure loadConfigurationOrPrompt(Scanner scanner) {
        Configure config = new Configure();
        if (new File(CONFIG_FILE_PATH).exists()) {
            System.out.println("Loading configuration from file...");
            if (!config.loadConfigurationFromFile()) {
                System.out.println("Failed to load configuration. Please configure the system first.");
                config.getUserInputForConfiguration(scanner);
                config.saveConfigurationToFile();
            }
        } else {
            System.out.println("No configuration found. Please configure the system.");
            config.getUserInputForConfiguration(scanner);
            config.saveConfigurationToFile();
        }
        return config;
    }

    private static void startTicketBooking(Configure config) {
        Ticketpool ticketPool = new Ticketpool(config.getMaxTicketCapacity());

        Vendor vendor = new Vendor(ticketPool, config.getTotalTicketCapacity(), config.getTicketReleaseRate());
        Thread vendorThread = new Thread(vendor, "VendorThread");

        Customer customer1 = new Customer(ticketPool, 1, config.getTicketRetrievalRate());
        Customer customer2 = new Customer(ticketPool, 1, config.getTicketRetrievalRate());
        Thread customerThread1 = new Thread(customer1, "CustomerThread1");
        Thread customerThread2 = new Thread(customer2, "CustomerThread2");

        logger.info("Starting VendorThread...");
        vendorThread.start();

        logger.info("Starting CustomerThread1...");
        customerThread1.start();

        logger.info("Starting CustomerThread2...");
        customerThread2.start();

        try {
            vendorThread.join();
            logger.info("VendorThread has finished.");

            customerThread1.join();
            logger.info("CustomerThread1 has finished.");

            customerThread2.join();
            logger.info("CustomerThread2 has finished.");
        } catch (InterruptedException e) {
            logger.warning("Main thread interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }

        logger.info("Ticket booking process completed.");
    }

    private static boolean askUserToLoadPreviousConfiguration(Scanner scanner) {
        while (true) {
            System.out.print("Do you want to load previous configuration data? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                return true;
            } else if (response.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid response! Please enter 'yes' or 'no'.");
            }
        }
    }
}
