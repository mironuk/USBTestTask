package usbtesttask.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import usbtesttask.TestBase;
import usbtesttask.data.dto.ClientDto;
import usbtesttask.data.model.Client;
import usbtesttask.data.repository.ClientRepository;

public class ClientServiceTest extends TestBase {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Before
    public void init() {
        clientRepository.deleteAllNotCascaded();
    }

    @Test
    public void testSaveClient() {
        ClientDto clientDto = generateClientDto1();
        Client client = createClientFromClientDto(clientDto);

        client = clientService.saveClient(client);

        long clientId = client.getClientId();

        Optional<Client> optionalClient = clientService.getClientById(clientId);
        assertTrue(optionalClient.isPresent());

        Client actualClient = optionalClient.get();

        assertEquals(clientDto.getFirstName(), actualClient.getFirstName());
        assertEquals(clientDto.getLastName(), actualClient.getLastName());
        assertEquals(clientDto.getMiddleName(), actualClient.getMiddleName());
        assertEquals(clientDto.getInn(), actualClient.getInn());
    }

    @Test
    public void testSaveAllClients() {
        List<Client> clients = new ArrayList<>();

        ClientDto clientDto1 = generateClientDto1();
        Client client1 = createClientFromClientDto(clientDto1);
        clients.add(client1);

        ClientDto clientDto2 = generateClientDto2();
        Client client2 = createClientFromClientDto(clientDto2);
        clients.add(client2);

        ClientDto clientDto3 = generateClientDto3();
        Client client3 = createClientFromClientDto(clientDto3);
        clients.add(client3);

        clients = clientService.saveAllClients(clients);

        long clientId1 = clients.get(0).getClientId();
        long clientId2 = clients.get(1).getClientId();
        long clientId3 = clients.get(2).getClientId();

        Optional<Client> optionalClient1 = clientService.getClientById(clientId1);
        assertTrue(optionalClient1.isPresent());

        Client actualClient1 = optionalClient1.get();

        assertEquals(clientDto1.getFirstName(), actualClient1.getFirstName());
        assertEquals(clientDto1.getLastName(), actualClient1.getLastName());
        assertEquals(clientDto1.getMiddleName(), actualClient1.getMiddleName());
        assertEquals(clientDto1.getInn(), actualClient1.getInn());

        Optional<Client> optionalClient2 = clientService.getClientById(clientId2);
        assertTrue(optionalClient2.isPresent());

        Client actualClient2 = optionalClient2.get();

        assertEquals(clientDto2.getFirstName(), actualClient2.getFirstName());
        assertEquals(clientDto2.getLastName(), actualClient2.getLastName());
        assertEquals(clientDto2.getMiddleName(), actualClient2.getMiddleName());
        assertEquals(clientDto2.getInn(), actualClient2.getInn());

        Optional<Client> optionalClient3 = clientService.getClientById(clientId3);
        assertTrue(optionalClient3.isPresent());

        Client actualClient3 = optionalClient3.get();

        assertEquals(clientDto3.getFirstName(), actualClient3.getFirstName());
        assertEquals(clientDto3.getLastName(), actualClient3.getLastName());
        assertEquals(clientDto3.getMiddleName(), actualClient3.getMiddleName());
        assertEquals(clientDto3.getInn(), actualClient3.getInn());

    }

    @Test
    public void testCheckAndSaveClient() {
        ClientDto clientDto = generateClientDto1();
        Client client = createClientFromClientDto(clientDto);

        client = clientService.checkAndSaveClient(client);
        long clientId = client.getClientId();

        Optional<Client> optionalClient = clientService.getClientById(clientId);
        assertTrue(optionalClient.isPresent());

        Client actualClient = optionalClient.get();

        assertEquals(clientDto.getFirstName(), actualClient.getFirstName());
        assertEquals(clientDto.getLastName(), actualClient.getLastName());
        assertEquals(clientDto.getMiddleName(), actualClient.getMiddleName());
        assertEquals(clientDto.getInn(), actualClient.getInn());

        Client sameClient = createClientFromClientDto(clientDto);
        sameClient = clientService.checkAndSaveClient(client);

        long sameClientId = sameClient.getClientId();

        assertEquals(clientId, sameClientId);

        Optional<Client> optionalSameClient = clientService.getClientById(sameClientId);
        assertTrue(optionalSameClient.isPresent());

        Client actualSameClient = optionalSameClient.get();

        assertEquals(clientDto.getFirstName(), actualSameClient.getFirstName());
        assertEquals(clientDto.getLastName(), actualSameClient.getLastName());
        assertEquals(clientDto.getMiddleName(), actualSameClient.getMiddleName());
        assertEquals(clientDto.getInn(), actualSameClient.getInn());
    }

    @Test
    public void testDeleteClientById() {
        ClientDto clientDto = generateClientDto1();
        Client client = createClientFromClientDto(clientDto);

        client = clientService.saveClient(client);

        long clientId = client.getClientId();

        Optional<Client> optionalClient = clientService.getClientById(clientId);
        assertTrue(optionalClient.isPresent());

        clientService.deleteClientById(clientId);

        optionalClient = clientService.getClientById(clientId);
        assertFalse(optionalClient.isPresent());
    }

}
