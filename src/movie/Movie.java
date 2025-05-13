package movie;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String genre;
    private int duration;
    private String description;
    private String director;
    private List<Schedule> schedules;

    public Movie(String title, String genre, int duration, String description, String director) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.description = description;
        this.director = director;
        this.schedules = new ArrayList<>();
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void displayDetails() {
        System.out.println("===== DETAIL FILM =====");
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
    }

    public boolean removeSchedule(int index) {
        if (index >= 0 && index < schedules.size()) {
            schedules.remove(index);
            return true;
        }
        return false;
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

    public String toString() {
        return title + " | Genre: " + genre + " | Durasi: " + duration + " menit";
    }

}