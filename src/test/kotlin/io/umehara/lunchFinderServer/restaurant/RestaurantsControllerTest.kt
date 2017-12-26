package io.umehara.lunchFinderServer.restaurant

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@RunWith(SpringRunner::class)
@SpringBootTest
class RestaurantsControllerTest {
    private lateinit var repo: RestaurantRepoStub
    private lateinit var mockController: MockMvc

    @Before
    fun setUp() {
        repo = RestaurantRepoStub()
        val restaurantsController = RestaurantsController(repo)
        mockController = standaloneSetup(restaurantsController).build()
    }

    @Test
    fun indexReturnsRestaurants() {
        val request = mockController.perform(get("/restaurants"))


        //language=json
        val expectedJSON = "[\n  {\"id\": 1, \"name\": \"Pintokona\"},\n  {\"id\": 2, \"name\": \"Momodori\"}\n]\n"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }

    @Test
    fun showReturnsRestaurant() {
        val request = mockController.perform(get("/restaurants/1"))


        //language=json
        val expectedJSON = "{\"id\": 1, \"name\": \"Pintokona\", \"categories\": [{\"id\": 1, \"name\": \"Sushi\"}]}\n"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }

    @Test
    fun createCallsRepoWithCorrectArguments() {
        //language=json
        val requestBody = "{\n  \"name\": \"Green Asia\",\n  \"categoryIds\": [1]\n}"

        val request = mockController.perform(post("/restaurants")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))


        request.andExpect(status().isCreated)
        assertThat(repo.createArgument, equalTo(RestaurantModelNew("Green Asia", listOf(1L))))
    }
}