package dashboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UnitData {

    private final StringProperty unit_name;


    public UnitData(String unit_ID, String unit_name) {
        this.unit_ID = new SimpleStringProperty(unit_ID);
        this.unit_name = new SimpleStringProperty(unit_name);

    }

    private final StringProperty unit_ID;

    public String getUnit_ID() {
        return unit_ID.get();
    }

    public StringProperty unit_IDProperty() {
        return unit_ID;
    }

    public void setUnit_ID(String unit_ID) {
        this.unit_ID.set(unit_ID);
    }

    public String getUnit_name() {
        return unit_name.get();
    }

    public StringProperty unit_nameProperty() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name.set(unit_name);
    }

}
