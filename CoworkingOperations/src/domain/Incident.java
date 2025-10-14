package domain;

import java.time.LocalDateTime;

public class Incident {
    private int id;
    private String description;
    private String reportedBy;
    private LocalDateTime reportedAt;
    private String status;

    public Incident() {}

    public Incident(String description, String reportedBy, LocalDateTime reportedAt, String status) {
        this.description = description;
        this.reportedBy = reportedBy;
        this.reportedAt = reportedAt;
        this.status = status;
    }

    public Incident(int id, String description, String reportedBy, LocalDateTime reportedAt, String status) {
        this.id = id;
        this.description = description;
        this.reportedBy = reportedBy;
        this.reportedAt = reportedAt;
        this.status = status;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReportedBy() { return reportedBy; }
    public void setReportedBy(String reportedBy) { this.reportedBy = reportedBy; }

    public LocalDateTime getReportedAt() { return reportedAt; }
    public void setReportedAt(LocalDateTime reportedAt) { this.reportedAt = reportedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Incident{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", reportedBy='" + reportedBy + '\'' +
                ", reportedAt=" + reportedAt +
                ", status='" + status + '\'' +
                '}';
    }
}
