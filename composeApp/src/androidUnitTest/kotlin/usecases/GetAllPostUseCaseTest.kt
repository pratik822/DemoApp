package usecases

import com.pratik.demoapp.domain.model.NewsList
import com.pratik.demoapp.domain.repository.PostRepository
import com.pratik.demoapp.domain.usecase.GetAllPostUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllPostUseCaseTest {
    private val repository = mockk<PostRepository>(relaxed = true)
    private val useCase = GetAllPostUseCase(repository)

    @Test
    fun get_all_post_success() = runTest {
        val category = "technology"
        useCase(category)
        val mockList = listOf(
            NewsList("test", "test", "test", "test", "test", "test", "test"),
            NewsList("test", "test", "test", "test", "test", "test", "test")
        )
        coEvery { repository.getAllPost(any()) } returns mockList
        val result = useCase(category)
        assertEquals(mockList, result)
        coVerify { repository.getAllPost(any()) }


    }
}