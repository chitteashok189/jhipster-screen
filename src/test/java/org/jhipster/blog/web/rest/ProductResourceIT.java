package org.jhipster.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.jhipster.blog.web.rest.TestUtil.sameInstant;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.jhipster.blog.IntegrationTest;
import org.jhipster.blog.domain.Product;
import org.jhipster.blog.domain.enumeration.ProType;
import org.jhipster.blog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final UUID DEFAULT_G_UID = UUID.randomUUID();
    private static final UUID UPDATED_G_UID = UUID.randomUUID();

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRODUCT_PRICE = 1;
    private static final Integer UPDATED_PRODUCT_PRICE = 2;

    private static final ProType DEFAULT_PRODUCT_TYPE = ProType.Vegetables;
    private static final ProType UPDATED_PRODUCT_TYPE = ProType.Fruits;

    private static final Integer DEFAULT_U_OM = 1;
    private static final Integer UPDATED_U_OM = 2;

    private static final String DEFAULT_OTHER_PRODUCT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_PRODUCT_DETAILS = "BBBBBBBBBB";

    private static final Long DEFAULT_PREVIOUS_ENTRY = 1L;
    private static final Long UPDATED_PREVIOUS_ENTRY = 2L;

    private static final String DEFAULT_MANUFACTURER = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_ENTRY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ENTRY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final ZonedDateTime DEFAULT_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .gUID(DEFAULT_G_UID)
            .productCode(DEFAULT_PRODUCT_CODE)
            .productName(DEFAULT_PRODUCT_NAME)
            .productPrice(DEFAULT_PRODUCT_PRICE)
            .productType(DEFAULT_PRODUCT_TYPE)
            .uOM(DEFAULT_U_OM)
            .otherProductDetails(DEFAULT_OTHER_PRODUCT_DETAILS)
            .previousEntry(DEFAULT_PREVIOUS_ENTRY)
            .manufacturer(DEFAULT_MANUFACTURER)
            .productDescription(DEFAULT_PRODUCT_DESCRIPTION)
            .imageFileName(DEFAULT_IMAGE_FILE_NAME)
            .productEntryName(DEFAULT_PRODUCT_ENTRY_NAME)
            .capacity(DEFAULT_CAPACITY)
            .length(DEFAULT_LENGTH)
            .width(DEFAULT_WIDTH)
            .height(DEFAULT_HEIGHT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .gUID(UPDATED_G_UID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productType(UPDATED_PRODUCT_TYPE)
            .uOM(UPDATED_U_OM)
            .otherProductDetails(UPDATED_OTHER_PRODUCT_DETAILS)
            .previousEntry(UPDATED_PREVIOUS_ENTRY)
            .manufacturer(UPDATED_MANUFACTURER)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .imageFileName(UPDATED_IMAGE_FILE_NAME)
            .productEntryName(UPDATED_PRODUCT_ENTRY_NAME)
            .capacity(UPDATED_CAPACITY)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testProduct.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getProductPrice()).isEqualTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testProduct.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testProduct.getuOM()).isEqualTo(DEFAULT_U_OM);
        assertThat(testProduct.getOtherProductDetails()).isEqualTo(DEFAULT_OTHER_PRODUCT_DETAILS);
        assertThat(testProduct.getPreviousEntry()).isEqualTo(DEFAULT_PREVIOUS_ENTRY);
        assertThat(testProduct.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testProduct.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getImageFileName()).isEqualTo(DEFAULT_IMAGE_FILE_NAME);
        assertThat(testProduct.getProductEntryName()).isEqualTo(DEFAULT_PRODUCT_ENTRY_NAME);
        assertThat(testProduct.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testProduct.getLength()).isEqualTo(DEFAULT_LENGTH);
        assertThat(testProduct.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testProduct.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduct.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testProduct.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testProduct.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].gUID").value(hasItem(DEFAULT_G_UID.toString())))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].productPrice").value(hasItem(DEFAULT_PRODUCT_PRICE)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uOM").value(hasItem(DEFAULT_U_OM)))
            .andExpect(jsonPath("$.[*].otherProductDetails").value(hasItem(DEFAULT_OTHER_PRODUCT_DETAILS)))
            .andExpect(jsonPath("$.[*].previousEntry").value(hasItem(DEFAULT_PREVIOUS_ENTRY.intValue())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER)))
            .andExpect(jsonPath("$.[*].productDescription").value(hasItem(DEFAULT_PRODUCT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].imageFileName").value(hasItem(DEFAULT_IMAGE_FILE_NAME)))
            .andExpect(jsonPath("$.[*].productEntryName").value(hasItem(DEFAULT_PRODUCT_ENTRY_NAME)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(sameInstant(DEFAULT_UPDATED_ON))));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.gUID").value(DEFAULT_G_UID.toString()))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.productPrice").value(DEFAULT_PRODUCT_PRICE))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE.toString()))
            .andExpect(jsonPath("$.uOM").value(DEFAULT_U_OM))
            .andExpect(jsonPath("$.otherProductDetails").value(DEFAULT_OTHER_PRODUCT_DETAILS))
            .andExpect(jsonPath("$.previousEntry").value(DEFAULT_PREVIOUS_ENTRY.intValue()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER))
            .andExpect(jsonPath("$.productDescription").value(DEFAULT_PRODUCT_DESCRIPTION))
            .andExpect(jsonPath("$.imageFileName").value(DEFAULT_IMAGE_FILE_NAME))
            .andExpect(jsonPath("$.productEntryName").value(DEFAULT_PRODUCT_ENTRY_NAME))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedOn").value(sameInstant(DEFAULT_UPDATED_ON)));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .gUID(UPDATED_G_UID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productType(UPDATED_PRODUCT_TYPE)
            .uOM(UPDATED_U_OM)
            .otherProductDetails(UPDATED_OTHER_PRODUCT_DETAILS)
            .previousEntry(UPDATED_PREVIOUS_ENTRY)
            .manufacturer(UPDATED_MANUFACTURER)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .imageFileName(UPDATED_IMAGE_FILE_NAME)
            .productEntryName(UPDATED_PRODUCT_ENTRY_NAME)
            .capacity(UPDATED_CAPACITY)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testProduct.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductPrice()).isEqualTo(UPDATED_PRODUCT_PRICE);
        assertThat(testProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProduct.getuOM()).isEqualTo(UPDATED_U_OM);
        assertThat(testProduct.getOtherProductDetails()).isEqualTo(UPDATED_OTHER_PRODUCT_DETAILS);
        assertThat(testProduct.getPreviousEntry()).isEqualTo(UPDATED_PREVIOUS_ENTRY);
        assertThat(testProduct.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testProduct.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getImageFileName()).isEqualTo(UPDATED_IMAGE_FILE_NAME);
        assertThat(testProduct.getProductEntryName()).isEqualTo(UPDATED_PRODUCT_ENTRY_NAME);
        assertThat(testProduct.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testProduct.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testProduct.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProduct.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProduct.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testProduct.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, product.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .productType(UPDATED_PRODUCT_TYPE)
            .uOM(UPDATED_U_OM)
            .imageFileName(UPDATED_IMAGE_FILE_NAME)
            .length(UPDATED_LENGTH)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getgUID()).isEqualTo(DEFAULT_G_UID);
        assertThat(testProduct.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getProductPrice()).isEqualTo(DEFAULT_PRODUCT_PRICE);
        assertThat(testProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProduct.getuOM()).isEqualTo(UPDATED_U_OM);
        assertThat(testProduct.getOtherProductDetails()).isEqualTo(DEFAULT_OTHER_PRODUCT_DETAILS);
        assertThat(testProduct.getPreviousEntry()).isEqualTo(DEFAULT_PREVIOUS_ENTRY);
        assertThat(testProduct.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testProduct.getProductDescription()).isEqualTo(DEFAULT_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getImageFileName()).isEqualTo(UPDATED_IMAGE_FILE_NAME);
        assertThat(testProduct.getProductEntryName()).isEqualTo(DEFAULT_PRODUCT_ENTRY_NAME);
        assertThat(testProduct.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testProduct.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testProduct.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testProduct.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduct.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProduct.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testProduct.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .gUID(UPDATED_G_UID)
            .productCode(UPDATED_PRODUCT_CODE)
            .productName(UPDATED_PRODUCT_NAME)
            .productPrice(UPDATED_PRODUCT_PRICE)
            .productType(UPDATED_PRODUCT_TYPE)
            .uOM(UPDATED_U_OM)
            .otherProductDetails(UPDATED_OTHER_PRODUCT_DETAILS)
            .previousEntry(UPDATED_PREVIOUS_ENTRY)
            .manufacturer(UPDATED_MANUFACTURER)
            .productDescription(UPDATED_PRODUCT_DESCRIPTION)
            .imageFileName(UPDATED_IMAGE_FILE_NAME)
            .productEntryName(UPDATED_PRODUCT_ENTRY_NAME)
            .capacity(UPDATED_CAPACITY)
            .length(UPDATED_LENGTH)
            .width(UPDATED_WIDTH)
            .height(UPDATED_HEIGHT)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedOn(UPDATED_UPDATED_ON);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getgUID()).isEqualTo(UPDATED_G_UID);
        assertThat(testProduct.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductPrice()).isEqualTo(UPDATED_PRODUCT_PRICE);
        assertThat(testProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testProduct.getuOM()).isEqualTo(UPDATED_U_OM);
        assertThat(testProduct.getOtherProductDetails()).isEqualTo(UPDATED_OTHER_PRODUCT_DETAILS);
        assertThat(testProduct.getPreviousEntry()).isEqualTo(UPDATED_PREVIOUS_ENTRY);
        assertThat(testProduct.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testProduct.getProductDescription()).isEqualTo(UPDATED_PRODUCT_DESCRIPTION);
        assertThat(testProduct.getImageFileName()).isEqualTo(UPDATED_IMAGE_FILE_NAME);
        assertThat(testProduct.getProductEntryName()).isEqualTo(UPDATED_PRODUCT_ENTRY_NAME);
        assertThat(testProduct.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testProduct.getLength()).isEqualTo(UPDATED_LENGTH);
        assertThat(testProduct.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProduct.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testProduct.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testProduct.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, product.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(product))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
