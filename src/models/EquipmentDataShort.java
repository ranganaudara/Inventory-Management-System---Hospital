package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EquipmentDataShort {

    private final StringProperty name;
    private final StringProperty make;
    private final StringProperty model;
    private final StringProperty localAgent;
    private final StringProperty usability;
    private final StringProperty count;

    public EquipmentDataShort(
            String name,
            String make,
            String model,
            String localAgent,
            String usability,
            String count) {
        this.name = new SimpleStringProperty(name);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.localAgent = new SimpleStringProperty(localAgent);
        this.usability = new SimpleStringProperty(usability);
        this.count = new SimpleStringProperty(count);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMake() {
        return make.get();
    }

    public StringProperty makeProperty() {
        return make;
    }

    public void setMake(String make) {
        this.make.set(make);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getLocalAgent() {
        return localAgent.get();
    }

    public StringProperty localAgentProperty() {
        return localAgent;
    }

    public void setLocalAgent(String localAgent) {
        this.localAgent.set(localAgent);
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
