package xyz.lysggen.bankparser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity(name ="finance")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Finance extends DatabaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private boolean isShared;
    private String name;
    private String description;
    @OneToMany
    private List<User> users;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
