package usbtesttask.data.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import usbtesttask.data.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Modifying
    @Transactional
    @Query("delete from Client")
    void deleteAllNotCascaded();

    @Query("from Client c where c.firstName = :firstName and c.lastName = :lastName and c.middleName = :middleName and c.inn = :inn")
    Optional<Client> findByAllData(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            @Param("inn") String inn
            );

}
