package dashboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EquipmentData {
    private final StringProperty ID;
    private final StringProperty name;
    private final StringProperty model;
    private final StringProperty company;
    private final StringProperty usable;
    private final StringProperty un_usable;

    public EquipmentData(String ID, String name, String model, String company, String usable, String un_usable) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.model = new SimpleStringProperty(model);
        this.company = new SimpleStringProperty(company);
        this.usable = new SimpleStringProperty(usable);
        this.un_usable = new SimpleStringProperty(un_usable);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
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

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getCompany() {
        return company.get();
    }

    public StringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public String getUsable() {
        return usable.get();
    }

    public StringProperty usableProperty() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable.set(usable);
    }

    public String getUn_usable() {
        return un_usable.get();
    }

    public StringProperty un_usableProperty() {
        return un_usable;
    }

    public void setUn_usable(String un_usable) {
        this.un_usable.set(un_usable);
    }

}
