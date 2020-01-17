package com.example.hw02;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

class Pizza implements Serializable {
    int number;
    List<String>l;

    public int getNumber() {
        return number;
    }

    public List<String> getL() {
        return l;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return number == pizza.number &&
                checked == pizza.checked &&
                l.equals(pizza.l);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, l, checked);
    }

    public boolean isChecked() {
        return checked;
    }

    boolean checked;

    public Pizza(int number, List<String> l, boolean checked) {
        this.number = number;
        this.l = l;
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "number=" + number +
                ", list=" + l +
                ", checked=" + checked +
                '}';
    }
}
