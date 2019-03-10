package usbtesttask.data.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private Long clientId;

    @NotNull
    @Column(name = "first_name", nullable = false, columnDefinition = "VARCHAR(128)")
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false, columnDefinition = "VARCHAR(128)")
    private String lastName;

    @NotNull
    @Column(name = "middle_name", nullable = false, columnDefinition = "VARCHAR(128)")
    private String middleName;

    @NotNull
    @Column(name = "inn", nullable = false, columnDefinition = "CHAR(10)")
    private String inn;



    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
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
        return Objects.equals(clientId, ((Client) obj).clientId);
    }

    @Override
    public String toString() {
        return "Client [clientId=" + clientId
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", middleName=" + middleName
                + ", inn=" + inn
                + "]";
    }

}
