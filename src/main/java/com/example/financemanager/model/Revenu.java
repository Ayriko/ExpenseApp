package com.example.financemanager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Revenu {
    private final LocalDate period;
    private final float total;
    private final float salary;
    private final float help;
    private final float selfEmployed;
    private final float passif;
    private final float other;

    private final static String PRICE_FORMAT = "%.2f €";

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy");

    public Revenu(LocalDate period, float salary, float help, float selfEmployed, float passif, float other) {
        this.period = period;
        this.total = salary + help + selfEmployed + passif + other;
        this.salary = salary;
        this.help = help;
        this.selfEmployed = selfEmployed;
        this.passif = passif;
        this.other = other;
    }

    public StringProperty periodProperty() {
        return new SimpleStringProperty(period.format(DATE_FORMAT));
    }

    public StringProperty totalProperty() {
        return formatAmount(total);
    }

    private SimpleStringProperty formatAmount(float amount) {
        return new SimpleStringProperty(String.format(PRICE_FORMAT, amount));
    }

    public StringProperty salaryProperty() {
        return formatAmount(salary);
    }

    public StringProperty helpProperty() {
        return formatAmount(help);
    }

    public StringProperty selfEmployedProperty() {
        return formatAmount(selfEmployed);
    }

    public StringProperty passifProperty() {
        return formatAmount(passif);
    }

    public StringProperty otherProperty() {
        return formatAmount(other);
    }

    public LocalDate getPeriod() {
        return period;
    }

    public float getTotal() {
        return total;
    }

    public float getSalary() {
        return salary;
    }

    public float getHelp() {
        return help;
    }

    public float getSelfEmployed() {
        return selfEmployed;
    }

    public float getPassif() {
        return passif;
    }

    public float getOther() {
        return other;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "date=" + period +
                ", total=" + total +
                ", salary=" + salary +
                ", help=" + help +
                ", selfEmployed=" + selfEmployed +
                ", passif=" + passif +
                ", other=" + other +
                '}';
    }

    public int compareTo(Revenu revenu) {
        return -this.period.compareTo(revenu.period);
    }
}
