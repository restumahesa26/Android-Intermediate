package com.example.storyapp.ui.liststory

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.storyapp.JsonConverter
import com.example.storyapp.R
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.helper.EspressoIdlingResource
import com.example.storyapp.ui.detail.DetailActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class ListStoryActivityTest {

    private val mockWebServer = MockWebServer()

    private lateinit var scenario: ActivityScenario<ListStoryActivity>

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        scenario = ActivityScenario.launch(ListStoryActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun getListStoryNotNull() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_story_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_user))
            .check(matches(isDisplayed()))
        onView(withText("Robot ngepost"))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_user))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Robot ngepost"))
                )
            )
        onView(withId(R.id.rv_user)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(30))
    }

    @Test
    fun getListStoryNull() {
        val mockResponse = MockResponse()
            .setResponseCode(500)
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_user))
            .check(matches(isDisplayed()))
    }

    @Test
    fun getDetailListStory() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_story_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.rv_user)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(30))

        Intents.init()
        onView(withId(R.id.rv_user)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(DetailActivity::class.java.name))
        onView(withId(R.id.imageDetail)).check(matches(isDisplayed()))
    }
}