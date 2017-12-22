package io.umehara.lunchFinderServer

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup


@RunWith(SpringRunner::class)
@SpringBootTest
class RestaurantsControllerTest {
    @Test
    fun get_returnsRestaurants() {
        val restaurantsController = RestaurantsController(RestaurantRepoStub())
        val mockController = standaloneSetup(restaurantsController).build()


        val request = mockController.perform(get("/restaurants"))


        //language=json
        val expectedJSON = "[\n  {\"id\": 1, \"name\": \"Pintokona\"},\n  {\"id\": 2, \"name\": \"Momodori\"}\n]\n"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }
}
