package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LocationData {
    private final StringProperty location;
    private final StringProperty count;

    public LocationData(String location, String count) {
        this.location = new SimpleStringProperty(location);
        this.count = new SimpleStringProperty(count);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getCount() {
        return count.get();
    }

    public StringProperty countProperty() {
        return count;
    }

    public void setCount(String count) {
        this.count.set(count);
    }

}
