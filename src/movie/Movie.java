// package movie;

// import java.util.ArrayList;
// import java.util.List;

// public class Movie {
//     private String title;
//     private String genre;
//     private int duration;
//     private List<Schedule> schedules;

//     public Movie(String title, String genre, int duration) {
//         this.title = title;
//         this.genre = genre;
//         this.duration = duration;
//         this.schedules = new ArrayList<>();
//     }

//     public List<Schedule> getSchedules() {
//         return schedules;
//     }

//     public void addSchedule(Schedule schedule) {
//         // TODO: Implement adding schedule logic
//     }

//     @Override
//     public String toString() {
//         // TODO: Implement toString method
//         return null;
//     }

//     // Getters and Setters
//     public String getTitle() {
//         return title;
//     }

//     public void setTitle(String title) {
//         this.title = title;
//     }

//     public String getGenre() {
//         return genre;
//     }

//     public void setGenre(String genre) {
//         this.genre = genre;
//     }

//     public int getDuration() {
//         return duration;
//     }

//     public void setDuration(int duration) {
//         this.duration = duration;
//     }
// }

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private String genre;
    private int duration; // in minutes
    private List<Schedule> schedules;

    public Movie(String title, String genre, int duration) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.schedules = new ArrayList<>();
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    // Add a schedule
    public void addSchedule(Schedule schedule) {
        if (schedule != null) {
            schedules.add(schedule);
        }
    }

    @Override
    public String toString() {
        return title + " (" + genre + ", " + duration + " mins)";
    }
}
