package usbtesttask.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usbtesttask.data.model.Client;
import usbtesttask.data.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Client checkAndSaveClient(Client client) {
        Optional<Client> existingClient = clientRepository.findByAllData(
                client.getFirstName(),
                client.getLastName(),
                client.getMiddleName(),
                client.getInn()
                );
        return existingClient.orElseGet(() -> clientRepository.save(client));
    }

    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public List<Client> saveAllClients(List<Client> clients) {
        return clientRepository.saveAll(clients);
    }

    public Optional<Client> getClientById(long clientId) {
        return clientRepository.findById(clientId);
    }

    @Transactional
    public void deleteClientById(long clientId) {
        clientRepository.deleteById(clientId);
    }

}
