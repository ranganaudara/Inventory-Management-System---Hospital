package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.*;

public class EquipmentDataLong {
    private final StringProperty serialNo;
    private final StringProperty invoiceNo;
    private final StringProperty invoiceDate;
    private final StringProperty name;
    private final StringProperty make;
    private final StringProperty model;
    private final StringProperty DOI;
    private final StringProperty localAgent;
    private final StringProperty warranty;
    private final StringProperty location;
    private final StringProperty usability;


    public EquipmentDataLong(
            String serialNo,
            String invoiceNo,
            String invoiceDate,
            String name,
            String make,
            String model,
            String DOI,
            String localAgent,
            String warranty,
            String location,
            String usability) {
        this.serialNo = new SimpleStringProperty(serialNo);
        this.invoiceNo = new SimpleStringProperty(invoiceNo);
        this.invoiceDate = new SimpleStringProperty(invoiceDate);
        this.name = new SimpleStringProperty(name);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.DOI = new SimpleStringProperty(DOI);
        this.localAgent = new SimpleStringProperty(localAgent);
        this.warranty = new SimpleStringProperty(warranty);
        this.location = new SimpleStringProperty(location);
        this.usability = new SimpleStringProperty(usability);
    }


    public String getSerialNo() {
        return serialNo.get();
    }

    public StringProperty serialNoProperty() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo.set(serialNo);
    }

    public String getInvoiceDate() {
        return invoiceDate.get();
    }

    public StringProperty invoiceDateProperty() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate.set(invoiceDate);
    }

    public String getInvoiceNo() {
        return invoiceNo.get();
    }

    public StringProperty invoiceNoProperty() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo.set(invoiceNo);
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

    public String getDOI() {
        return DOI.get();
    }

    public StringProperty DOIProperty() {
        return DOI;
    }

    public void setDOI(String DOI) {
        this.DOI.set(DOI);
    }

    public String getlocalAgent() {
        return localAgent.get();
    }

    public StringProperty localAgentProperty() {
        return localAgent;
    }

    public void setlocalAgent(String localAgent) {
        this.localAgent.set(localAgent);
    }

    public String getWarranty() {
        return warranty.get();
    }

    public StringProperty warrantyProperty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty.set(warranty);
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

    public String getUsable() {
        return usability.get();
    }

    public StringProperty usabilityProperty() {
        return usability;
    }

    public void setUsable(String usability) {
        this.usability.set(usability);
    }
}
