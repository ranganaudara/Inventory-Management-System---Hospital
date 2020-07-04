package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UnitDataShort {
    private final StringProperty location;
    private final StringProperty usability;
    private final StringProperty count;

    public UnitDataShort(String location, String usability, String count) {
        this.location = new SimpleStringProperty(location);
        this.usability = new SimpleStringProperty(usability);
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

    public String getUsability() {
        return usability.get();
    }

    public StringProperty usabilityProperty() {
        return usability;
    }

    public void setUsability(String usability) {
        this.usability.set(usability);
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
