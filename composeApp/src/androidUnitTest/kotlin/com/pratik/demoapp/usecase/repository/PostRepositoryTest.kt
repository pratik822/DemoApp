package com.pratik.demoapp.usecase.repository

import com.pratik.demoapp.domain.model.NewsList
import com.pratik.demoapp.domain.repository.PostRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class PostRepositoryTest {
     private val postRepository: PostRepository = mockk();
    val category = "Technology";
     private val mockList = listOf(
         NewsList("test", "test", "test", "test", "test", "test", "test"),

     )

    @Test
    fun `get all post list`()=runTest{
        coEvery { postRepository.getAllPost(category)  } returns mockList
         val result = postRepository.getAllPost(category)
         assertEquals(1,result.size)


    }
 }