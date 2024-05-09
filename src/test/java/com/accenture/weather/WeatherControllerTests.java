package com.accenture.weather;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


//@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTests
{
//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
    private WeatherService service;

    @Test
    public void testGetForecast() throws Exception
    {
        // Mock service behavior
        //YourResource mockResource = new YourResource(1L, "Mock Resource");
        //when(yourService.getResourceById(1L)).thenReturn(mockResource);

//        mockMvc.perform(MockMvcRequestBuilders.get("/weather"))
//                        .andExpect(status().isOk());

        //mockMvc.perform(get)

        // Perform GET request
//        mockMvc.perform(get("/weather"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.name", is("Mock Resource")));
    }
}
