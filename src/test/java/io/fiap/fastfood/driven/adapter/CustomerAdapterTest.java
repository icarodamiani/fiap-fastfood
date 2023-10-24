package io.fiap.fastfood.driven.adapter;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.fiap.fastfood.driven.core.domain.customer.mapper.CustomerMapper;
import io.fiap.fastfood.driven.core.domain.model.Customer;
import io.fiap.fastfood.driven.core.domain.model.Identity;
import io.fiap.fastfood.driven.core.entity.CustomerEntity;
import io.fiap.fastfood.driven.core.entity.IdentityEntity;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.DuplicatedKeyException;
import io.fiap.fastfood.driven.repository.CustomerRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith({MockitoExtension.class})
class CustomerAdapterTest {

    @InjectMocks
    private CustomerAdapter adapter;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper mapper;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createCustomer_success() {
        var domainIdentity = Identity.IdentityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var domain = Customer.CustomerBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(domainIdentity).build();

        var entityIdentity = IdentityEntity.IdentityEntityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var entity = CustomerEntity.CustomerEntityBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(entityIdentity).build();

        var response = CustomerEntity.CustomerEntityBuilder.builder()
            .withId(1L)
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(entityIdentity).build();

        var expected = Customer.CustomerBuilder.builder()
            .withId(1L)
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(domainIdentity).build();

        when(mapper.entityFromDomain(domain)).thenReturn(entity);
        when(customerRepository.findByIdentityNumberAndIdentityType(domainIdentity.number(), domainIdentity.type()))
            .thenReturn(Mono.empty());
        when(customerRepository.save(entity)).thenReturn(Mono.just(response));
        when(mapper.domainFromEntity(response)).thenReturn(expected);

        StepVerifier.create(adapter.createCustomer(domain))
            .expectNext(expected)
            .expectComplete()
            .verify();

        verify(mapper, times(1)).entityFromDomain(domain);
        verify(customerRepository, times(1)).findByIdentityNumberAndIdentityType(domainIdentity.number(), domainIdentity.type());
        verify(customerRepository, times(1)).save(entity);
        verify(mapper, times(1)).domainFromEntity(response);
    }

    @Test
    void createCustomer_duplicated() {
        var domainIdentity = Identity.IdentityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var domain = Customer.CustomerBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(domainIdentity).build();

        var entityIdentity = IdentityEntity.IdentityEntityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var entity = CustomerEntity.CustomerEntityBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(entityIdentity).build();

        when(customerRepository.findByIdentityNumberAndIdentityType(domainIdentity.number(), domainIdentity.type()))
            .thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.createCustomer(domain))
            .expectError(DuplicatedKeyException.class)
            .verify();

        verify(customerRepository, times(1)).findByIdentityNumberAndIdentityType(domainIdentity.number(), domainIdentity.type());
        verify(customerRepository, never()).save(entity);
    }

    @Test
    void deleteCustomer_success() {
        long id = 5L;

        doAnswer(i -> Mono.just(id).then()).when(customerRepository).deleteById(id);

        StepVerifier.create(adapter.deleteCustomer(id))
            .expectComplete()
            .verify();

        verify(customerRepository, times(1)).deleteById(id);
    }


    @Test
    void listCustomer_all_success() {
        Pageable unpaged = Pageable.unpaged();

        var entityIdentity = IdentityEntity.IdentityEntityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var response = CustomerEntity.CustomerEntityBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(entityIdentity).build();

        var domainIdentity = Identity.IdentityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var expected = Customer.CustomerBuilder.builder()
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(domainIdentity).build();

        when(customerRepository.findByIdNotNull(unpaged)).thenReturn(Flux.just(response));
        when(mapper.domainFromEntity(response)).thenReturn(expected);

        StepVerifier.create(adapter.listCustomer(unpaged))
            .expectNext(expected)
            .expectComplete()
            .verify();

        verify(customerRepository, times(1)).findByIdNotNull(unpaged);
        verify(mapper, times(1)).domainFromEntity(response);
    }

    @Test
    void updateCustomer_success() throws IOException, JsonPatchException {
        long id = 1L;

        var entityIdentity = IdentityEntity.IdentityEntityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var entity = CustomerEntity.CustomerEntityBuilder.builder()
            .withId(id)
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(entityIdentity).build();

        String json = "[{ \"op\":\"replace\", \"path\":\"/email\", \"value\":\"doe.john@gmail.com\" }]";
        var patch = jsonPatch(json);
        var patched = applyPatch(entity, patch);

        var domainIdentity = Identity.IdentityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var expected = Customer.CustomerBuilder.builder()
            .withId(id)
            .withName("John Doe")
            .withEmail("doe.john@gmail.com")
            .withIdentity(domainIdentity).build();

        when(customerRepository.findById(id)).thenReturn(Mono.just(entity));
        when(customerRepository.save(patched)).thenReturn(Mono.just(patched));
        when(mapper.domainFromEntity(patched)).thenReturn(expected);

        StepVerifier.create(adapter.updateCustomer(id, json))
            .expectNext(expected)
            .expectComplete()
            .verify();

        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).save(patched);
        verify(mapper, times(1)).domainFromEntity(patched);
    }

    @Test
    void updateCustomer_unknown_path() {
        long id = 1L;

        var entityIdentity = IdentityEntity.IdentityEntityBuilder.builder()
            .withType("any")
            .withNumber("00000000").build();

        var entity = CustomerEntity.CustomerEntityBuilder.builder()
            .withId(id)
            .withName("John Doe")
            .withEmail("john.doe@gmail.com")
            .withIdentity(entityIdentity).build();


        String patch = "[{ \"op\":\"replace\", \"path\":\"/unknown\", \"value\":\"any value\" }]";

        when(customerRepository.findById(id)).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.updateCustomer(id, patch))
            .expectError(BadRequestException.class)
            .verify();

        verify(customerRepository, times(1)).findById(id);
    }

    private CustomerEntity applyPatch(CustomerEntity entity, JsonPatch patch) throws IOException, JsonPatchException {
        var patched = patch.apply(objectMapper.convertValue(entity, JsonNode.class));
        return objectMapper.treeToValue(patched, CustomerEntity.class);
    }

    private JsonPatch jsonPatch(String json) throws IOException {
        final InputStream in = new ByteArrayInputStream(json.getBytes());

        return objectMapper.readValue(in, JsonPatch.class);
    }
}
