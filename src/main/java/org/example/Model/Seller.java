package org.example.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Seller {
    private String name;
    @Id
    private int id;

    public Seller() {
    }

    public Seller(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "name='" + name + '\'' +
                '}';
    }
}
