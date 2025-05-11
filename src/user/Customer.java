package user;

/**
 * Kelas yang merepresentasikan Customer dalam Sistem Pemesanan Tiket Bioskop
 */
public class Customer extends User {

    // Field tambahan yang hanya dimiliki oleh Customer
    private String email;

    /**
     * Konstruktor untuk Customer
     * @param username nama pengguna
     * @param password kata sandi
     * @param email alamat email customer
     */
    public Customer(String username, String password, String email) {
        super(username, password); // Panggil konstruktor User
        this.email = email;
    }

    @Override
    public void printInfo() {
        // Menampilkan informasi Customer
        System.out.println("Customer Username: " + this.username);
        System.out.println("Email: " + this.email);
    }

    // Getter dan Setter untuk email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

