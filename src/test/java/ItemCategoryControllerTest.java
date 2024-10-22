package com.mycompany.obitemservice.controller;

import com.mycompany.obitemservice.model.ItemCategoryModel;
import com.mycompany.obitemservice.repository.ItemCategoryRepository;
import com.mycompany.obitemservice.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemCategoryController.class)
public class ItemCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemCategoryRepository itemCategoryRepository;

    @MockBean
    private ItemRepository itemRepository;

    @Test
    public void testGetAllItemCategories() throws Exception {
        ItemCategoryModel category = new ItemCategoryModel("1", "Tech");
        Mockito.when(itemCategoryRepository.findAll()).thenReturn(Collections.singletonList(category));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Tech"))
                .andDo(print());
    }

    @Test
    public void testSaveItemCategory() throws Exception {
        ItemCategoryModel category = new ItemCategoryModel("2", "Home");
        Mockito.when(itemCategoryRepository.insert(Mockito.any(ItemCategoryModel.class))).thenReturn(category);

        String categoryJson = "{ \"id\": \"2\", \"name\": \"Home\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(print());
    }

    @Test
    public void testGetItemCategory() throws Exception {
        ItemCategoryModel category = new ItemCategoryModel("1", "Tech");
        Mockito.when(itemCategoryRepository.findById("1")).thenReturn(Optional.of(category));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Tech"))
                .andDo(print());
    }
}
