package pl.telekom;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String message;
    private Date date;

    public Message(String message) {
        this.message = message;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "Message{" +
                "data='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
