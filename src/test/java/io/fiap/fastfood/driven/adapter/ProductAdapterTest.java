package io.fiap.fastfood.driven.adapter;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.fiap.fastfood.driven.core.entity.ProductEntity;
import io.fiap.fastfood.driven.core.entity.ProductTypeEntity;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import io.fiap.fastfood.driven.core.exception.domain.model.Product;
import io.fiap.fastfood.driven.core.exception.domain.model.ProductType;
import io.fiap.fastfood.driven.core.exception.domain.product.mapper.ProductMapper;
import io.fiap.fastfood.driven.core.exception.domain.product.mapper.ProductTypeMapper;
import io.fiap.fastfood.driven.repository.ProductRepository;
import io.fiap.fastfood.driven.repository.ProductTypeRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
class ProductAdapterTest {

    @InjectMocks
    private ProductAdapter adapter;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductTypeRepository productTypeRepository;

    @Mock
    private ProductMapper mapper;

    @Mock
    private ProductTypeMapper typeMapper;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createProduct_success() {
        long typeId = 5L;
        var domainType = ProductType.ProductTypeBuilder.builder()
            .withDescription("any")
            .withId(typeId).build();

        var entityType = ProductTypeEntity.ProductTypeEntityBuilder.builder()
            .withDescription("any")
            .withId(typeId).build();

        var domain = Product.ProductBuilder.builder()
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId)
            .withType(domainType).build();

        var entity = ProductEntity.ProductEntityBuilder.builder()
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId).build();

        var response = ProductEntity.ProductEntityBuilder.builder()
            .withId(1L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId).build();

        var expected = Product.ProductBuilder.builder()
            .withId(1L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId).build();


        when(typeMapper.entityFromDomain(domainType)).thenReturn(entityType);
        when(productTypeRepository.save(entityType)).thenReturn(Mono.just(entityType));
        when(mapper.entityFromDomain(domain)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(Mono.just(response));
        when(mapper.domainFromEntity(response)).thenReturn(expected);

        StepVerifier.create(adapter.createProduct(domain))
            .expectNext(expected)
            .expectComplete()
            .verify();

        verify(mapper, times(1)).entityFromDomain(domain);
        verify(productRepository, times(1)).save(entity);
        verify(mapper, times(1)).domainFromEntity(response);
    }

    @Test
    void deleteProduct_success() {
        long id = 5L;

        doAnswer(i -> Mono.just(id).then()).when(productRepository).deleteById(id);

        StepVerifier.create(adapter.deleteProduct(id))
            .expectComplete()
            .verify();

        verify(productRepository, times(1)).deleteById(id);
    }


    @Test
    void listProduct_by_type_success() {
        Long typeId = 10L;
        Pageable unpaged = Pageable.unpaged();

        var response = ProductEntity.ProductEntityBuilder.builder()
            .withId(1L)
            .withAvailability(5)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId).build();

        var expected = Product.ProductBuilder.builder()
            .withId(1L)
            .withAvailability(5)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId).build();

        when(productRepository.findByTypeId(typeId, unpaged)).thenReturn(Flux.just(response));
        when(mapper.domainFromEntity(response)).thenReturn(expected);
        when(productTypeRepository.findById(typeId)).thenReturn(
            Mono.just(
                ProductTypeEntity.ProductTypeEntityBuilder.builder().withId(typeId).withDescription("any").build()
            )
        );

        StepVerifier.create(adapter.listProduct(typeId, unpaged))
            .expectNext(expected)
            .expectComplete()
            .verify();

        verify(productRepository, times(1)).findByTypeId(typeId, unpaged);
        verify(mapper, times(1)).domainFromEntity(response);
    }

    @Test
    void listProduct_all_success() {
        Long typeId = null;
        Pageable unpaged = Pageable.unpaged();

        var response = ProductEntity.ProductEntityBuilder.builder()
            .withId(1L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L).build();

        var expected = Product.ProductBuilder.builder()
            .withId(1L)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(5L).build();

        when(productRepository.findByIdNotNull(unpaged)).thenReturn(Flux.just(response));
        when(productTypeRepository.findById(5L)).thenReturn(
            Mono.just(
                ProductTypeEntity.ProductTypeEntityBuilder.builder().withId(5L).withDescription("any").build()
            )
        );
        when(mapper.domainFromEntity(response)).thenReturn(expected);

        StepVerifier.create(adapter.listProduct(typeId, unpaged))
            .expectNext(expected)
            .expectComplete()
            .verify();

        verify(productRepository, times(1)).findByIdNotNull(unpaged);
        verify(productTypeRepository, times(1)).findById(anyLong());
        verify(mapper, times(1)).domainFromEntity(response);
    }

    @Test
    void updateProduct_success() throws IOException, JsonPatchException {
        long id = 1L;
        long typeId = 5L;

        var entity = ProductEntity.ProductEntityBuilder.builder()
            .withId(id)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId).build();

        String json = "[{ \"op\":\"replace\", \"path\":\"/price\", \"value\":\"21.21\" }]";
        var patch = jsonPatch(json);
        var patched = applyPatch(entity, patch);

        var expected = Product.ProductBuilder.builder()
            .withId(id)
            .withAvailability(10)
            .withPrice(BigDecimal.valueOf(21.21))
            .withDescription("anyProduct")
            .withTypeId(typeId).build();

        when(productRepository.findById(id)).thenReturn(Mono.just(entity));
        when(productRepository.save(patched)).thenReturn(Mono.just(patched));
        when(mapper.domainFromEntity(patched)).thenReturn(expected);

        StepVerifier.create(adapter.updateProduct(id, patch))
            .expectNext(expected)
            .expectComplete()
            .verify();

        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(patched);
        verify(mapper, times(1)).domainFromEntity(patched);
    }

    @Test
    void updateProduct_unknown_path() throws IOException {
        long id = 1L;
        long typeId = 5L;

        var entity = ProductEntity.ProductEntityBuilder.builder()
            .withId(id)
            .withAvailability(10)
            .withPrice(BigDecimal.TEN)
            .withDescription("anyProduct")
            .withTypeId(typeId).build();

        String json = "[{ \"op\":\"replace\", \"path\":\"/unknown\", \"value\":\"any value\" }]";
        var patch = jsonPatch(json);

        when(productRepository.findById(id)).thenReturn(Mono.just(entity));

        StepVerifier.create(adapter.updateProduct(id, patch))
            .expectError(BadRequestException.class)
            .verify();

        verify(productRepository, times(1)).findById(id);
    }

    private ProductEntity applyPatch(ProductEntity entity, JsonPatch patch) throws IOException, JsonPatchException {
        var patched = patch.apply(objectMapper.convertValue(entity, JsonNode.class));
        return objectMapper.treeToValue(patched, ProductEntity.class);
    }

    private JsonPatch jsonPatch(String json) throws IOException {
        final InputStream in = new ByteArrayInputStream(json.getBytes());

        return objectMapper.readValue(in, JsonPatch.class);
    }
}
