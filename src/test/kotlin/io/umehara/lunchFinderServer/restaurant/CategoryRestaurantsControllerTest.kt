package io.umehara.lunchFinderServer.restaurant

import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@SpringBootTest
class CategoryRestaurantsControllerTest {
    private lateinit var repo: RestaurantRepoSpy
    private lateinit var mockController: MockMvc

    @Before
    fun setUp() {
        repo = RestaurantRepoSpy()
        val controller = CategoryRestaurantsController(repo)
        mockController = MockMvcBuilders.standaloneSetup(controller).build()
    }

    @Test
    fun indexCallsRepoWithCorrectArguments() {
        mockController.perform(get("/categories/15/restaurants"))

        assertThat(repo.whereArgument, equalTo(15L))
    }

    @Test
    fun indexReturnsRestaurants() {
        val request = mockController.perform(get("/categories/15/restaurants"))


        //language=json
        val expectedJSON = "[\n  {\"id\": 1, \"name\": \"Pizzakaya\", \"nameJp\": \"ピザカヤ\"},\n  {\"id\": 2, \"name\": \"Moti\", \"nameJp\": \"モティ\"}\n]\n"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }

    @Test
    fun removeCallsRepoWithCorrectArguments() {
        val request = mockController.perform(delete("/categories/15/restaurants/4"))

        request.andExpect(status().isOk)
        assertThat(repo.removeCategoryArgumentCategoryId, equalTo(15L))
        assertThat(repo.removeCategoryArgumentId, equalTo(4L))
    }
}
