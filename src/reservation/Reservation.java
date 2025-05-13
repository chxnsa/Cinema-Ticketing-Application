package reservation;


import user.Customer;
import movie.Schedule;

public class Reservation {
    private Customer customer;
    private Schedule schedule;
    private int seatNumber;
    private double totalPrice;

    public Reservation(Customer customer, Schedule schedule, int seatNumber) {
        this.customer = customer;
        this.schedule = schedule;
        this.seatNumber = seatNumber;
        this.totalPrice = schedule.getPrice();
    }

    public void printInfo() {
        System.out.println("\n===============================");
        System.out.println("TIKET BIOSKOP");
        System.out.println("===============================");
        System.out.println("Nama: " + customer.getUsername());
        System.out.println("Film: " + schedule.getMovie().getTitle());
        System.out.println("Tanggal: " + schedule.getDateTime());
        System.out.println("Nomor Kursi: " + seatNumber);
        System.out.println("Harga: Rp " + String.format("%,.0f", totalPrice));
        System.out.println("===============================");
        System.out.println("Terima kasih & selamat menonton!");
    }

}

