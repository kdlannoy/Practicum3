package main;

import java.util.ArrayList;
import java.util.List;
import order.Customer;
import order.OrderProcessor;
import eventbroker.EventBroker;
import order.BlacklistOrderProcessor;

public class Main {

    public static void main(String[] args) {

        String[] names = new String[]{"Jan", "Piet", "Joris", "Corneel"};
        List<Thread> threads = new ArrayList<Thread>();

        BlacklistOrderProcessor orderProcessor = BlacklistOrderProcessor.create();

        EventBroker.getEventBroker().start();

        for (String name : names) {
            Customer customer = new Customer(name);
            Thread t = new OrderThread(customer);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        EventBroker.getEventBroker().stop();
        System.out.println("Aantal orders: " + OrderProcessor.getNumberOfOrders());
    }

    private static class OrderThread extends Thread {

        private Customer customer;
        private int noOrders = 10;

        public OrderThread(Customer customer) {
            this.customer = customer;
        }

        @Override
        public void run() {
            for (int i = 0; i < noOrders; i++) {

                
                customer.buy("item-" + i);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }

        }
    }
}
