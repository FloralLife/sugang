package org.example.sugang.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Description
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DuplicateEnrollmentTest @Autowired constructor(
  val mockMvc: MockMvc,
) {
  companion object {
    @Container
    private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.2.0").apply {
      withDatabaseName("sugang-test")
      withReuse(true)
    }

    init {
      mysqlContainer.start()
    }
  }

  @Test
  @Description("한 유저가 한 특강을 5번 신청했을 때 한번만 등록")
  fun duplicateEnrollment() {
    mockMvc.post("/api/lectures") {
      contentType = MediaType.APPLICATION_JSON
      content = "{\"name\": \"특강\", \"instructor\": \"강연자\", \"date\": \"2024-12-28\"}"
    }.andExpect { status { isCreated() } }

    val enrollmentList = List(5) { index -> "{\"userId\" : 1, \"lectureId\" : 1}" }
    enrollmentList.forEach {
      mockMvc.post("/api/enrollments") {
        contentType = MediaType.APPLICATION_JSON
        content = it
      }
    }

    mockMvc.get("/api/enrollments?userId=1")
      .andExpect {
        status { isOk() }
        jsonPath("$.length()", ) { value(1) }
      }
  }
}
