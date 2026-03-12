package viewmodels

import com.pratik.demoapp.domain.model.NewsList
import com.pratik.demoapp.domain.usecase.GetAllPostUseCase
import com.pratik.demoapp.presentation.ui.HomeListingScreenViewModel
import com.pratik.demoapp.presentation.ui.PostIntent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs // Use this for cleaner type checking

@OptIn(ExperimentalCoroutinesApi::class)
class HomeListingScreenViewModelTest {

    private val getAllPostUseCase: GetAllPostUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeListingScreenViewModel

    // Move mock data to a helper or keep it as a private property
    private val mockkList = listOf(
        NewsList("test", "test", "test", "test", "test", "test", "test")
    )

    @Before
    fun setup() {
        // Initialize once here; no need to repeat inside the test method
        viewModel = HomeListingScreenViewModel(getAllPostUseCase, testDispatcher)
    }

    @Test
    fun `loadPost intent updates state to Success when use case returns data`() = runTest(testDispatcher) {
        // GIVEN
        val category = "Tech"
        coEvery { getAllPostUseCase(category) } returns mockkList

        // WHEN
        viewModel.processIntent(PostIntent.loadPost(category))

        // Execute pending coroutines
        advanceUntilIdle()

        // THEN
        val state = viewModel.allPostState.value

        // 1. assertIs is cleaner than assertTrue + manual casting
        // It also provides better error messages if it fails
        assertIs<PostState.Success>(state)

        // 2. Because of assertIs, 'state' is now smart-cast to PostState.Success
        assertEquals(mockkList, state.post)
    }

    @Test
    fun `loadPost intent updates state to Error when use case throws exception`() = runTest(testDispatcher) {
        val category = "Tech"
        coEvery { getAllPostUseCase(category) } throws Exception("Test Exception")
        viewModel.processIntent(PostIntent.loadPost(category))
        advanceUntilIdle()
        val state = viewModel.allPostState.value
        assertIs<PostState.Error>(state)
        assertEquals("Test Exception", state.message)


    }
}