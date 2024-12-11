package org.example;

import java.util.logging.Logger;

public class Customer implements Runnable {
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    private Ticketpool ticketPool;
    private int retrievalAmount;
    private int retrievalRate;

    public Customer(Ticketpool ticketPool, int retrievalAmount, int retrievalRate) {
        this.ticketPool = ticketPool;
        this.retrievalAmount = retrievalAmount;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        while (ticketPool.isSelling() || ticketPool.getAvailableTickets() > 0) {
            boolean success = ticketPool.retrieveTickets(retrievalAmount);
            if (!success && !ticketPool.isSelling()) {
                break;
            }
            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                logger.warning("Customer thread interrupted.");
                break;
            }
        }
        logger.info("Customer has stopped buying tickets.");
    }
}
