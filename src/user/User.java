package user;

/**
 * Kelas abstrak yang merepresentasikan User dalam Sistem Pemesanan Tiket Bioskop
 */
public abstract class User {
    // Fields
    protected String username;
    protected String password;
    
    /**
     * Konstruktor untuk User
     * @param username nama pengguna
     * @param password kata sandi
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * Metode login untuk mengautentikasi pengguna
     * @param inputUser nama pengguna yang dimasukkan
     * @param inputPass kata sandi yang dimasukkan
     * @return true jika login berhasil, false jika gagal
     */
    public boolean login(String inputUser, String inputPass) {
        // Cek apakah nama pengguna dan kata sandi yang dimasukkan cocok dengan yang ada
        if (this.username.equals(inputUser) && this.password.equals(inputPass)) {
            return true; // Login berhasil jika nama pengguna dan kata sandi cocok
        }
        return false;// Login gagal jika tidak cocok
    }    
    
    /**
     * Metode untuk mencetak informasi pengguna
     */
    public abstract void printInfo();
    
    // Getter dan Setter
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    // Kami tidak menyediakan getter untuk password demi alasan keamanan
    public void setPassword(String password) {
        this.password = password;
    }
}
