package io.umehara.lunchFinderServer

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo

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
    fun createCallsRepoWithCorrectArguments() {
        //language=json
        val requestBody = "{\n  \"name\": \"Green Asia\"\n}"

        val request = mockController.perform(post("/restaurants")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))


        request.andExpect(status().isCreated)
        assertThat(repo.createArgument, equalTo(RestaurantModelNew("Green Asia")))
    }
}
