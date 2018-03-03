package io.umehara.lunchFinderServer.restaurant

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import java.math.BigDecimal

@SpringBootTest
class RestaurantsControllerTest {
    private lateinit var repo: RestaurantRepoSpy
    private lateinit var mockController: MockMvc

    @Before
    fun setUp() {
        repo = RestaurantRepoSpy()
        val restaurantsController = RestaurantsController(repo)
        mockController = standaloneSetup(restaurantsController).build()
    }

    @Test
    fun indexReturnsRestaurants() {
        val request = mockController.perform(get("/restaurants"))


        //language=json
        val expectedJSON = "[\n  {\"id\": 1, \"name\": \"Pizzakaya\", \"nameJp\": \"ピザカヤ\"},\n  {\"id\": 2, \"name\": \"Moti\", \"nameJp\": \"モティ\"}\n]\n"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }

    @Test
    fun showReturnsRestaurant() {
        val request = mockController.perform(get("/restaurants/1"))

        //language=json
        val expectedJSON = "{\n  \"id\": 1, \n  \"name\": \"Pizzakaya\", \n  \"nameJp\": \"ピザカヤ\", \n  \"categories\": [{\"id\": 1, \"name\": \"Pizza\"}],\n  \"website\": \"pizzakaya.com\",\n  \"geolocation\": {\n    \"lat\": 35.662265,\n    \"long\": 139.726658\n  }\n}\n"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }

    @Test
    fun createCallsRepoWithCorrectArguments() {
        //language=json
        val requestBody = "{\n  \"name\": \"Green Asia\",\n  \"nameJp\": \"グリーンアジア\",\n  \"website\": \"www.example.com\",\n  \"geolocation\": {\n    \"lat\": 33.33,\n    \"long\": 33.33\n  },\n  \"categoryIds\": [1]\n}"

        val request = mockController.perform(post("/restaurants")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))

        //language=json
        val expectedJSON = "{\"id\": 1}"
        request.andExpect(status().isCreated)
                .andExpect(content().json(expectedJSON))
        assertThat(repo.createArgument, equalTo(RestaurantModelNew(
                "Green Asia",
                "グリーンアジア",
                "www.example.com",
                Geolocation(BigDecimal.valueOf(33.33),BigDecimal.valueOf(33.33)),
                listOf(1L))))
    }

    @Test
    fun updateCallsRepoWithCorrectArguments() {
        //language=json
        val requestBody = "{\n  \"name\": \"Green Asia\",\n  \"nameJp\": \"グリーンアジア\",\n  \"website\": \"www.example.com\",\n  \"geolocation\": {\n    \"lat\": 33.33,\n    \"long\": 33.33\n  },\n  \"categoryIds\": [1]\n}"

        val request = mockController.perform(put("/restaurants/1")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))


        request.andExpect(status().isOk)
        assertThat(repo.updateArgumentId, equalTo(1L))
        assertThat(repo.updateArgumentRestaurantModelNew, equalTo(
                RestaurantModelNew(
                        "Green Asia",
                        "グリーンアジア",
                        "www.example.com",
                        Geolocation(BigDecimal.valueOf(33.33),BigDecimal.valueOf(33.33)),
                        listOf(1L)
                )))
    }

    @Test
    fun addCategoryCallsRepoWithCorrectArguments() {
        val request = mockController.perform(put("/restaurants/25/categories/2")
                .contentType(APPLICATION_JSON_UTF8))


        request.andExpect(status().isOk)
        assertThat(repo.addCategoryArgumentId, equalTo(25L))
        assertThat(repo.addCategoryArgumentCategoryId, equalTo(2L))
    }

    @Test
    fun destroyCallsRepoWithCorrectArguments() {
        val request = mockController.perform(delete("/restaurants/5"))


        request.andExpect(status().isOk)
        assertThat(repo.destroyArgument, equalTo(5L))
    }
}
