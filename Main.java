

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class Faktur {
    protected String noFaktur;
    protected String kodeBarang;
    protected String namaBarang;
    protected double hargaBarang;
    protected int jumlahBeli;
    protected double total;

    // Constructor
    public Faktur(String noFaktur, String kodeBarang, String namaBarang, double hargaBarang, int jumlahBeli) {
        this.noFaktur = noFaktur;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBeli = jumlahBeli;
        this.total = hargaBarang * jumlahBeli; // Menghitung total
    }

    // Method untuk menampilkan informasi faktur
    public void tampilkanInfo(String kasir) {
        System.out.println("");
        System.out.println("+----------------------------------------------------+");
        System.out.println("Terima kasih sudah membeli di Supermarket AmaliMart");

        // Menggunakan method date untuk menampilkan tanggal dan waktu saat ini
        System.out.println("Tanggal dan Waktu: " + getCurrentDateTime());
        System.out.println("+----------------------------------------------------+");
        System.out.println("No. Faktur      : " + noFaktur);
        System.out.println("Kode Barang     : " + kodeBarang);
        System.out.println("Nama Barang     : " + namaBarang);
        System.out.printf("Harga Barang    : %.2f%n", hargaBarang);
        System.out.println("Jumlah Beli     : " + jumlahBeli);
        System.out.printf("TOTAL           : %.2f%n", total);
        System.out.println("+----------------------------------------------------+");
        System.out.println("Kasir           : " + kasir);
        System.out.println("+----------------------------------------------------+");
    }

    // Method untuk mendapatkan tanggal dan waktu saat ini
    public static String getCurrentDateTime() {
          // Menggunakan class SimpleDateFormat untuk memformat objek Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date); // Mengembalikan format tanggal dan waktu
    }
}

class FakturPenjualan extends Faktur {
    // Constructor
    public FakturPenjualan(String noFaktur, String kodeBarang, String namaBarang, double hargaBarang, int jumlahBeli) {
        super(noFaktur, kodeBarang, namaBarang, hargaBarang, jumlahBeli);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Login process
        String kasir = login(scanner);
        if (kasir == null) {
            return; // Exit if login fails
        }

        System.out.println("Selamat Datang di Supermarket AmaliMart");
        
        try {
            // Input dari pengguna
            String noFaktur = mintaInput(scanner, "Masukkan No Faktur (harus angka): ", true);
            String kodeBarang = mintaInput(scanner, "Masukkan Kode Barang: ", false);
            String namaBarang = mintaInput(scanner, "Masukkan Nama Barang: ", false);
            double hargaBarang = Double.parseDouble(mintaInput(scanner, "Masukkan Harga Barang: ", false));
            int jumlahBeli = Integer.parseInt(mintaInput(scanner, "Masukkan Jumlah Beli (harus > 0): ", true));

            // Membuat objek FakturPenjualan
            FakturPenjualan faktur = new FakturPenjualan(noFaktur, kodeBarang, namaBarang, hargaBarang, jumlahBeli);

            // Menampilkan informasi faktur
            faktur.tampilkanInfo(kasir);

        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid. Pastikan harga barang dan jumlah beli adalah angka.");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            scanner.close(); // Menutup scanner
        }
    }

    // Method untuk meminta input dari pengguna dengan validasi
    public static String mintaInput(Scanner scanner, String prompt, boolean isNumeric) {
        String inputan;
        while (true) {
            System.out.print(prompt);
            inputan = scanner.nextLine().trim(); // Menghapus spasi di awal dan akhir
            if (validasiInputan(inputan, isNumeric)) {
                return inputan;
            } else {
                System.out.println("Input tidak valid. Silakan coba lagi.");
            }
        }
    }

    // Method untuk memvalidasi inputan
    public static boolean validasiInputan(String inputan, boolean isNumeric) {
        if (inputan == null || inputan.isEmpty()) {// Menggunakan method string `isEmpty`
            return false; // Input tidak boleh kosong
        }
        if (isNumeric) {
            try {
                if (inputan.equals("0")) {// Menggunakan method string `equals` untuk membandingkan nilai
                    return false; // No faktur tidak boleh 0
                }
                Integer.parseInt(inputan); // Validasi angka
                return true;
            } catch (NumberFormatException e) {
                return false; // Jika bukan angka
            }
        }
        return true; // Jika bukan input numerik, dianggap valid
    }

    // Method untuk login pengguna dengan CAPTCHA
    public static String login(Scanner scanner) {
        String username = "amaliakasir"; // Username baru
        String password = "123"; // Password baru

        while (true) { // Loop hingga login berhasil atau gagal setelah beberapa kali percobaan
            System.out.print("Username: ");
            String inputUsername = scanner.nextLine();
            
            System.out.print("Password: ");
            String inputPassword = scanner.nextLine();

            // Generate and display CAPTCHA
            String captchaGenerated = generateCaptcha();
            System.out.println("Captcha: " + captchaGenerated);
            
            System.out.print("Masukkan Captcha di atas: ");
            String captchaInput = scanner.nextLine();

            // Menggunakan method string `equals` untuk validasi input
            if (inputUsername.equals(username) && inputPassword.equals(password) && captchaInput.equals(captchaGenerated)) {
                System.out.println("Login berhasil!");
                return inputUsername; // Mengembalikan username sebagai nama kasir
            } else {
                System.out.println("Login gagal. Silakan coba lagi.");
                if (!inputUsername.equals(username) || !inputPassword.equals(password)) {
                    System.out.println("Username atau password salah.");
                } else {
                    System.out.println("Captcha salah.");
                }
            }
        }
    }

    // Method untuk menghasilkan CAPTCHA acak sederhana
    public static String generateCaptcha() {
        Random random = new Random();
        int captchaValue = random.nextInt(9999); // Menghasilkan angka acak antara 0 hingga 9999
        return String.format("%04d", captchaValue); // Format menjadi 4 digit dengan leading zero jika perlu
    }
}