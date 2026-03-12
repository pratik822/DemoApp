package com.pratik.demoapp.usecase

import com.pratik.demoapp.domain.repository.PostRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class GetAllPostUseCaseTest {
 @Test
 fun get_all_post_success() = runTest{
  val postRepository = mockk<PostRepository>()

 }
}
