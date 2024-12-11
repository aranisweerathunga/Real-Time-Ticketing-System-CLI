# RealTimeTicketingSystemCLI
# Ticket Booking System

A Java-based concurrent ticket booking system that simulates ticket distribution between vendors and customers. The system features configurable capacity management, multi-threaded operations, and persistent configuration storage.

## Features

- Concurrent ticket sales processing
- Configurable ticket capacity and release rates
- Multiple simultaneous customer support
- Persistent configuration storage
- Thread-safe operations
- Comprehensive logging system

## Prerequisites

- Java JDK 8 or higher
- Maven
- Gson library for JSON processing

## Installation

1. Clone the repository:
```bash
git clone https://github.com/aranisweerathunga/ticket-booking-system.git
cd ticket-booking-system
```

2. Add the following dependency to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.8.9</version>
    </dependency>
</dependencies>
```

3. Build the project:
```bash
mvn clean install
```

## Usage

1. Run the main class:
```bash
java -cp target/ticket-booking-system-1.0.jar org.example.Main
```

2. Use the interactive menu to:
   - Configure the system
   - Start ticket booking operations
   - Stop the system

### Configuration Options

The system prompts for the following parameters:
- Maximum ticket capacity
- Total ticket capacity
- Ticket release rate (milliseconds)
- Ticket retrieval rate (milliseconds)

### Menu Options

```
--- Ticket Booking System Menu ---
1. Configure System
2. Start System
3. Stop System
```

- Option 1: Set up system parameters
- Option 2: Begin ticket booking simulation
- Option 3: Terminate the program

## System Components

### Ticketpool
Manages the central ticket repository with thread-safe operations:
- Ticket release management
- Available ticket tracking
- Synchronized ticket distribution

### Vendor
Handles ticket release operations:
- Controlled ticket release intervals
- Capacity monitoring
- Operation logging

### Customer
Simulates ticket purchasing behavior:
- Concurrent purchase attempts
- Configurable purchase rates
- Purchase verification

### Configure
Manages system configuration:
- JSON-based configuration storage
- Parameter validation
- Configuration persistence

## Configuration File

The system uses `config.json` for storing configuration:

```json
{
    "maxTicketCapacity": 100,
    "totalTicketCapacity": 1000,
    "ticketReleaseRate": 1000,
    "ticketRetrievalRate": 500
}
```

## Logging

The system implements Java's logging framework to track:
- Ticket operations
- Thread activities
- Configuration changes
- Error events

## Error Handling

The system includes comprehensive error handling for:
- Invalid configurations
- Thread interruptions
- File operations
- User input validation

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Java Concurrent Utilities
- Google Gson Library
- Java Logging Framework

## Author

Arani Weerathunga

## Support

For support, please open an issue in the GitHub repository.
