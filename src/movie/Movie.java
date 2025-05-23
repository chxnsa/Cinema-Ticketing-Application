package movie;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String genre;
    private int duration;
    private String description;
    private String director;
    private String imagePath;
    private List<Schedule> schedules;

    public Movie(String title, String genre, int duration, String description, String director) {
        this(title, genre, duration, description, director, "");
    }

    public Movie(String title, String genre, int duration, String description, String director, String imagePath) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.director = director;
        this.imagePath = imagePath;
        this.schedules = new ArrayList<>();
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
        System.out.println("Jadwal untuk film " + schedule.getTitle() + " berhasil ditambahkan");
    }

    public void displayDetails() {
        System.out.println("================== " + title.toUpperCase() + " =================");

        System.out.println("Judul: " + title);
        System.out.println("Genre: " + genre);
        System.out.println("Durasi: " + duration + " menit");
        System.out.println("Sutradara: " + (director.isEmpty() ? "-" : director));
        System.out.println("Deskripsi: " + (description.isEmpty() ? "-" : description));

        System.out.println("\nJadwal Tayang:");
        if (schedules.isEmpty()) {
            System.out.println("- Belum ada jadwal tayang untuk film ini");
        } else {
            for (int i = 0; i < schedules.size(); i++) {
                System.out.println((i + 1) + ". " + schedules.get(i));
            }
        }

        System.out.println("===========================================================");
        System.out.println("");
    }

    public static boolean isValidTitle(String title) {
        return title != null && !title.trim().isEmpty();
    }

    public static boolean isValidDuration(int duration) {
        return duration > 0;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getGenre() {
        return genre;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String toString() {
        return title + " | Genre: " + genre + " | Durasi: " + duration + " menit";
    }
}
