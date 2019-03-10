package usbtesttask.data.dto;

import java.math.BigDecimal;

public class TransactionDto {

    private Long transactionId;
    private ClientDto clientDto;
    private String place;
    private BigDecimal amount;
    private String currency;
    private String card;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
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

}
