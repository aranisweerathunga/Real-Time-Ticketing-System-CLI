package org.example;

import java.util.logging.Logger;

public class Ticketpool {
    private static final Logger logger = Logger.getLogger(Ticketpool.class.getName());
    private int availableTickets;
    private final int maxCapacity;
    private boolean isSelling = true;
    private int totalTicketsReleased; // New variable to track total tickets released

    public Ticketpool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.availableTickets = 0;
        this.totalTicketsReleased = 0;
    }

    /**
     * Releases a batch of tickets. The vendor waits until the current batch is sold
     * before releasing a new batch.
     *
     * @param numTickets The number of tickets to release.
     * @return True if tickets were successfully released, false otherwise.
     */
    public synchronized boolean releaseTickets(int numTickets) {
        // Check if we've already released maximum tickets
        if (totalTicketsReleased >= maxCapacity) {
            logger.info("Maximum ticket capacity already reached. Cannot release more tickets.");
            isSelling = false;
            return false;
        }

        while (availableTickets > 0 && isSelling) {
            try {
                logger.info("Vendor is waiting for customers to buy tickets...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warning("Vendor was interrupted while waiting to release tickets.");
                return false;
            }
        }

        if (!isSelling) {
            logger.info("Vendor has stopped selling tickets.");
            return false;
        }

        // Calculate remaining capacity for this batch
        int remainingCapacity = maxCapacity - totalTicketsReleased;

        // Adjust numTickets if it would exceed max capacity
        if (numTickets > remainingCapacity) {
            numTickets = remainingCapacity;
        }

        // Release the tickets
        availableTickets += numTickets;
        totalTicketsReleased += numTickets;
        logger.info("Vendor released " + numTickets + " tickets. Available tickets: " + availableTickets);
        logger.info("Total tickets released so far: " + totalTicketsReleased + " out of " + maxCapacity);

        // If after releasing, max capacity is reached, stop selling
        if (totalTicketsReleased >= maxCapacity) {
            isSelling = false;
            logger.info("Maximum ticket capacity reached. Vendor stops selling tickets.");
        }

        // Notify all waiting customers that new tickets are available
        notifyAll();
        return true;
    }

    /**
     * Gets the total number of tickets released so far.
     *
     * @return The total number of tickets released.
     */
    public synchronized int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    /**
     * Gets the remaining capacity for ticket releases.
     *
     * @return The number of tickets that can still be released.
     */
    public synchronized int getRemainingCapacity() {
        return maxCapacity - totalTicketsReleased;
    }

    // ... rest of the existing methods remain the same ...
    public synchronized boolean retrieveTickets(int numTickets) {
        if (availableTickets >= numTickets) {
            availableTickets -= numTickets;
            logger.info("Customer bought " + numTickets + " tickets. Available tickets: " + availableTickets);
        } else if (availableTickets > 0) {
            logger.info("Customer bought " + availableTickets + " tickets. Available tickets: 0");
            availableTickets = 0;
        } else {
            logger.info("No tickets available for purchase.");
            return false;
        }

        // If all tickets from the current batch are sold, notify the vendor
        if (availableTickets == 0 && isSelling) {
            notifyAll();
        }

        return true;
    }

    public synchronized int getAvailableTickets() {
        return availableTickets;
    }

    public synchronized boolean isSelling() {
        return isSelling;
    }
}