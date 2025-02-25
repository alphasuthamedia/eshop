package id.ac.ui.cs.advprog.eshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void listProductPage() throws Exception {
        Product dummy = new Product();
        dummy.setProductName("DUMMY_NAME");
        dummy.setProductQuantity(1);

        when(productService.findAll()).thenReturn(Arrays.asList(dummy));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"));
    }

    @Test
    void createProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }


    @Test
    void createProductPost() throws Exception {
        Product product = new Product();
        product.setProductID("DUMMY_ID");

        when(productService.create(product)).thenReturn(product);

        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product)
                        .formField("productName", "aku suka eskrim")
                        .formField("productQuantity", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));
    }

    @Test
    void createWrongProductPost() throws Exception {
        Product product = new Product();
        product.setProductID("DUMMY_ID");
        mockMvc.perform(post("/product/create")
                        .flashAttr("product", product)
                        .formField("productName", "aku tidak suka kamu :)")
                        .formField("productQuantity", "-1"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void testDeleteProductPost() throws Exception {
        Product product = new Product();
        product.setProductID("DUMMY_ID");
        product.setProductName("DUMMY_NAME");
        product.setProductQuantity(1);

        when(productService.findAll()).thenReturn(Arrays.asList(product));

        HashMap map = new HashMap();
        map.put("ID", "DUMMY_ID");

//        Product productObject = Product.builder()
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);


        mockMvc.perform(delete("/product/delete")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void testEditWrongProductPage() throws Exception {
        Product product = new Product();
        product.setProductName("DUMMY");
        product.setProductQuantity(10);
        product.setProductID("DUMMY_ID");

        when(productService.getItemByID("DUMMY_ID")).thenReturn(product);

        mockMvc.perform(get("http://localhost:8080/product/edit?id=NOT_DUMMY_ID"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = new Product();
        product.setProductName("DUMMY");
        product.setProductQuantity(10);
        product.setProductID("DUMMY_ID");

        when(productService.getItemByID("DUMMY_ID")).thenReturn(product);

        mockMvc.perform(get("http://localhost:8080/product/edit?id=DUMMY_ID")
                        .flashAttr("product", product))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("EditProduct"));
    }

    @Test
    void testEditProductPost() throws Exception {
        Product product = new Product();
        product.setProductName("DUMMY");
        product.setProductQuantity(10);

        mockMvc.perform(post("/product/edit")
                        .flashAttr("product", product)
                        .formField("productName", "NOT A DUMMY, KDDING")
                        .formField("productQuantity", "6464"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

    }
}