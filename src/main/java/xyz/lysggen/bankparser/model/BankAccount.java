package xyz.lysggen.bankparser.model;

import javax.persistence.*;

@Entity
public class BankAccount extends DatabaseModel {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String accountNumber;
    private String name;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
