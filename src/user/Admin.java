package user;

/**
 * Kelas yang merepresentasikan Admin dalam Sistem Pemesanan Tiket Bioskop
 */
public class Admin extends User {

    // Field tambahan yang hanya dimiliki oleh Admin
    private String role;

    /**
     * Konstruktor untuk Admin
     * @param username nama pengguna
     * @param password kata sandi
     * @param role peran admin
     */
    public Admin(String username, String password, String role) {
        super(username, password); // Panggil konstruktor User
        this.role = role;
    }

    @Override
    public void printInfo() {
        // Menampilkan informasi Admin
        System.out.println("Admin Username: " + this.username);
        System.out.println("Role: " + this.role);
    }

    // Getter dan Setter untuk role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

