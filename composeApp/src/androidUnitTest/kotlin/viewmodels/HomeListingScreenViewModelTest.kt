package viewmodels

import com.pratik.demoapp.core.utils.NewsList
import com.pratik.home_listing_feature.domain.usecase.GetAllPostUseCase
import com.pratik.home_listing_feature.domain.usecase.GetFavoriteNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.GetSaveNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.SaveNewsUseCase
import com.pratik.home_listing_feature.domain.usecase.UpdateFavoriteUseCase
import com.pratik.home_listing_feature.ui.HomeListingScreenViewModel
import com.pratik.home_listing_feature.ui.PostIntent
import com.pratik.home_listing_feature.ui.PostState
import com.pratik.home_listing_feature.utils.NetworkChecker
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
class HomeListingScreenViewModelTest {

    private val getAllPostUseCase: GetAllPostUseCase = mockk()
    private val saveNewsUseCase: SaveNewsUseCase = mockk(relaxed = true)
    private val getSaveNewsUseCase: GetSaveNewsUseCase = mockk()
    private val updateFavoriteUseCase: UpdateFavoriteUseCase = mockk(relaxed = true)
    private val getFavoriteNewsUseCase: GetFavoriteNewsUseCase = mockk()
    private val networkChecker: NetworkChecker= mockk()

    private lateinit var viewModel: HomeListingScreenViewModel

    private val dispatcher = StandardTestDispatcher()


    private val mockkList = listOf(
        NewsList("test", "test", "test", "test", "test", "test", "test")
    )

    @Before
    fun setup() {
        every { networkChecker.isNetworkAvailable() } returns true
        
        viewModel = HomeListingScreenViewModel(
            getAllPostUseCase,
            saveNewsUseCase,
            getSaveNewsUseCase,
            updateFavoriteUseCase,
            getFavoriteNewsUseCase,
            networkChecker,
            dispatcher
        )
    }

    @Test
    fun `LoadPost intent updates state to Success when use case returns data`() = runTest(dispatcher) {
        // GIVEN
        val category = "Tech"
        coEvery { getAllPostUseCase(category) } returns mockkList

        // WHEN
        viewModel.processIntent(PostIntent.LoadPost(category))

        advanceUntilIdle()

        // THEN
        val state = viewModel.allPostState.value

        assertIs<PostState.Success>(state)

        assertEquals(mockkList, state.post)
    }

    @Test
    fun `LoadPost intent updates state to Error when use case throws exception`() = runTest(dispatcher) {
        val category = "Tech"
        coEvery { getAllPostUseCase(category) } throws Exception("Test Exception")
        viewModel.processIntent(PostIntent.LoadPost(category))
        advanceUntilIdle()
        val state = viewModel.allPostState.value
        assertIs<PostState.Error>(state)
        assertEquals("Test Exception", state.message)
    }
}
