package xyz.lysggen.bankparser.model;

import javax.persistence.*;

@Entity
public class Category extends DatabaseModel {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;
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

}
