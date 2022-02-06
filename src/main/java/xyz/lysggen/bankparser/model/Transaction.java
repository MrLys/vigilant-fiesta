package xyz.lysggen.bankparser.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity(name ="transaction")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transaction extends DatabaseModel {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String description;
    private String date;
    private double amountIn;
    private double amountOut;
    private String postedDate;
    @ManyToOne
    @JoinColumn
    private Category category;
    @OneToOne()
    @JoinColumn(nullable = false)
    @JsonIgnore
    private BankStatement bankStatement;
    @Transient
    private List<Keyword> keywords;
    @Transient
    private List<Category> possibleCategories;
    @OneToOne
    @JoinColumn(nullable = false)
    private User user;
    public BankStatement getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmountIn() {
        return amountIn;
    }

    public void setAmountIn(double amountIn) {
        this.amountIn = amountIn;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }


    public double getAmountOut() {
        return amountOut;
    }

    public void setAmountOut(double amountOut) {
        this.amountOut = amountOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }
    public List<Category> getPossibleCategories() {
        return possibleCategories;
    }

    public void setPossibleCategories(List<Category> possibleCategories) {
        this.possibleCategories = possibleCategories;
    }


}
