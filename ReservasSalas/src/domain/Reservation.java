package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private int id;
    private Integer roomId;
    private LocalDateTime date;
    private LocalDateTime start;
    private LocalDateTime end;

    // Constructor

    public Reservation() {
    }

    public Reservation(int id, int roomId, LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.roomId = roomId;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    // Setters y Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public boolean overLapsWith(Reservation other){
        if (!this.roomId.equals(other.roomId)) return false;
        if (!this.date.equals(other.date)) return false;
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", roomId=" + roomId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }
}
