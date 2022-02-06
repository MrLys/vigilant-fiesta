package xyz.lysggen.bankparser.model;

import javax.persistence.*;

@Entity
public class Keyword extends DatabaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String value;
    @OneToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
