package org.example;

import java.util.logging.Logger;

public class Vendor implements Runnable {
    private static final Logger logger = Logger.getLogger(Vendor.class.getName());
    private Ticketpool ticketPool;
    private int releaseAmount;
    private int releaseRate;

    public Vendor(Ticketpool ticketPool, int releaseAmount, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseAmount = releaseAmount;
        this.releaseRate = releaseRate;
    }

    /**

     Simulates a ticket vendor thread that continuously releases tickets from a ticket pool at a
     specified interval until it stops selling tickets. */

    @Override
    public void run() {
        while (ticketPool.isSelling()) {
            boolean success = ticketPool.releaseTickets(releaseAmount);
            if (!success) {
                break;
            }

            // Log that the vendor is waiting for the next ticket release
            logger.info("Vendor waiting for the next ticket release. Current release rate: " + releaseRate + " ms.");

            // Vendor waits for the release rate time before releasing more tickets
            try {
                Thread.sleep(releaseRate);  // Wait for the specified release rate
            } catch (InterruptedException e) {
                logger.warning("Vendor thread interrupted.");
                break;
            }
        }
        logger.info("Vendor has stopped selling tickets.");
    }
}
