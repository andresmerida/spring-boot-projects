package dev.am.basic_demo.domain;

import java.util.Objects;

public class Alert {
    private String id;
    private String message;

    public Alert(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public Alert() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(id, alert.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alert{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
