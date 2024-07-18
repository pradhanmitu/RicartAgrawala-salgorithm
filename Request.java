package ricart;

import java.time.LocalTime;

public class Request {
    int id;
    LocalTime timestamp;

    public Request(int id, LocalTime timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
}