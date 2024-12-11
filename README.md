# RealTimeTicketingSystemCLI
# Ticket Booking System

A Java-based concurrent ticket booking system that simulates ticket sales between vendors and customers. This system allows for configuration of ticket capacity and transaction rates, with support for multiple concurrent customers.

## System Overview

The system consists of the following main components:
- Ticket Pool: Manages the available tickets and their distribution
- Vendor: Releases tickets into the pool at configured intervals
- Customers: Attempt to purchase tickets from the pool
- Configuration System: Handles system parameters and persistence

## Prerequisites

- Java JDK 8 or higher
- Maven (for dependency management)
- Google Gson library for JSON handling

## Getting Started

1. Clone the repository to your local machine
2. Ensure you have the required dependencies in your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.9</version>
    </dependency>
</dependencies>
```

## Configuration

The system can be configured either through the interactive console or by loading a previous configuration from `config.json`. The configuration parameters include:

- Maximum Ticket Capacity: Maximum number of tickets available at any time
- Total Ticket Capacity: Total number of tickets that can be released
- Ticket Release Rate: Time interval (in milliseconds) between ticket releases
- Ticket Retrieval Rate: Time interval (in milliseconds) between customer purchase attempts

## Running the System

1. Compile and run the `Main` class
2. You will be presented with the following menu:
   ```
   --- Ticket Booking System Menu ---
   1. Configure System
   2. Start System
   3. Stop System
   ```

### Option 1: Configure System
- If a configuration file exists, you'll be asked if you want to load it
- If you choose not to load existing configuration or if no configuration exists, you'll be prompted to enter new parameters
- The system will automatically start after configuration

### Option 2: Start System
- Loads existing configuration if available
- If no configuration exists, prompts for new configuration
- Starts the ticket booking simulation with configured parameters

### Option 3: Stop System
- Terminates the program

## System Components

### Ticketpool
- Manages the ticket inventory
- Handles synchronization between vendor and customers
- Tracks total tickets released and available tickets
- Implements thread-safe operations for ticket release and retrieval

### Vendor
- Runs on a separate thread
- Releases tickets at specified intervals
- Monitors maximum capacity limits
- Logs ticket release operations

### Customer
- Multiple customers can run concurrently
- Attempts to purchase tickets at specified intervals
- Continues until no more tickets are available or selling stops
- Logs purchase operations

### Configure
- Handles reading and writing configuration to JSON file
- Validates user inputs
- Maintains configuration persistence between runs

## Logging

The system uses Java's built-in logging system to track operations:
- Ticket releases and purchases
- Configuration changes
- Thread status updates
- Error conditions

Logs are output to the console by default.

## Error Handling

The system includes robust error handling for:
- Invalid user inputs
- Configuration file errors
- Thread interruptions
- Synchronization issues

## Best Practices

1. Always configure the system before starting ticket sales
2. Use reasonable values for release and retrieval rates to avoid system overload
3. Monitor the logs to track system behavior
4. Ensure proper shutdown using the menu option rather than force-closing

## Troubleshooting

Common issues and solutions:

1. **Configuration Loading Fails**
   - Verify config.json exists and has proper format
   - Check file permissions
   - Try creating a new configuration

2. **System Appears Stuck**
   - Check if release/retrieval rates are set too high
   - Verify thread status in logs
   - Ensure capacity values are appropriate

3. **Unexpected Termination**
   - Check logs for interruption messages
   - Verify sufficient system resources
   - Ensure proper exception handling

## Contributing

To extend or modify the system:
1. Follow the existing thread-safe patterns
2. Maintain proper synchronization
3. Add appropriate logging
4. Update configuration handling for new parameters
5. Document changes in this README

## License

This project is available for open use and modification. Please maintain appropriate attribution to original authors.
