package usbtesttask.data.model;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long transactionId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Client client;

    @NotNull
    @Column(name = "place", nullable = false, columnDefinition = "VARCHAR(128)")
    private String place;

    @NotNull
    @Column(name = "amount", nullable = false, columnDefinition = "DECIMAL(14,2)")
    private BigDecimal amount;

    @NotNull
    @Column(name = "currency", nullable = false, columnDefinition = "CHAR(3)")
    private String currency;

    @NotNull
    @Column(name = "card", nullable = false, columnDefinition = "VARCHAR(19)")
    // Max length of credit card number format is 19, should be used without hyphens
    private String card;




    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }



    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(transactionId, ((Transaction) obj).transactionId);
    }

    @Override
    public String toString() {
        return "Transaction [transactionId=" + transactionId
                + ", place=" + place
                + ", amount=" + amount
                + ", currency=" + currency
                + ", card=" + card
                + "]";
    }

}
