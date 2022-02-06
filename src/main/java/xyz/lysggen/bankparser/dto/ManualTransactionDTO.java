package xyz.lysggen.bankparser.dto;

public class ManualTransactionDTO {
    private int id = -1;
    private double amount = Integer.MAX_VALUE;
    private String description = "";
    private int finance = -1;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getFinance() {
        return finance;
    }

    public void setFinance(int finance) {
        this.finance = finance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
