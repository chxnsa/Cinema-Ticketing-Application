package reservation;

import user.Customer;
import movie.Schedule;

/**
 * Class representing a Reservation in the Cinema Ticketing System
 */
public class Reservation {
    // Fields
    private Customer customer;
    private Schedule schedule;
    private int seatNumber;
    private double totalPrice;
    
    /**
     * Constructor for Reservation
     * @param customer the customer making reservation
     * @param schedule the movie schedule
     * @param seatNumber the seat number
     */
    public Reservation(Customer customer, Schedule schedule, int seatNumber) {
        this.customer = customer;
        this.schedule = schedule;
        this.seatNumber = seatNumber;
        this.totalPrice = schedule.getPrice(); // Basic price, might be modified later
    }
    
    /**
     * Print information about this reservation
     */
    public void printInfo() {
        // TODO: Implement printing reservation information
    }
    
    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }
    
    public Schedule getSchedule() {
        return schedule;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}