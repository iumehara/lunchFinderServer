package io.umehara.lunchFinderServer.category

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

@SpringBootTest
class CategoriesControllerTest {
    private lateinit var repo: CategoryRepoSpy
    private lateinit var mockController: MockMvc

    @Before
    fun setUp() {
        repo = CategoryRepoSpy()
        val categoriesController = CategoriesController(repo)
        mockController = standaloneSetup(categoriesController).build()
    }

    @Test
    fun indexReturnsCategories() {
        val request = mockController.perform(get("/categories"))


        //language=json
        val expectedJSON = "[\n  {\"id\": 1, \"name\": \"Curry\"},\n  {\"id\": 2, \"name\": \"Sushi\"}\n]"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }

    @Test
    fun showReturnsCategory() {
        val request = mockController.perform(get("/categories/1"))


        //language=json
        val expectedJSON = "{\n  \"id\": 1, \n  \"name\": \"Pizza\",\n  \"restaurantCount\": 1,\n  \"restaurants\": [\n    {\"id\": 1, \"name\": \"Pizzakaya\", \"categoryIds\": [1]}\n  ]\n}"
        request
                .andExpect(status().isOk)
                .andExpect(content().json(expectedJSON))
    }

    @Test
    fun createCallsRepoWithCorrectArguments() {
        //language=json
        val requestBody = "{\n  \"name\": \"Curry\"\n}"

        val request = mockController.perform(post("/categories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestBody))

        //language=json
        val expectedJSON = "{\"id\": 1}"
        request.andExpect(status().isCreated)
                .andExpect(content().json(expectedJSON))
        assertThat(repo.createArgument, equalTo(CategoryModelNew("Curry")))
    }

    @Test
    fun destroyCallsRepoWithCorrectArguments() {
        val request = mockController.perform(delete("/categories/1"))


        request.andExpect(status().isOk)
        assertThat(repo.destroyArgument, equalTo(1L))
    }
}
