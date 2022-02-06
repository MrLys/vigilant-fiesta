package xyz.lysggen.bankparser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity(name ="manualtransaction")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ManualTransaction extends DatabaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private double amount;
    private LocalDateTime date;
    @OneToOne
    @JoinColumn(nullable = false)
    private Finance finance;
    private String description;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime localDateTime) {
        this.date = localDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
