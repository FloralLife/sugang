package org.example.sugang.api

import org.example.sugang.TestUtils.randomId
import org.example.sugang.api.request.EnrollmentCreateRequest
import org.example.sugang.application.EnrollmentService
import org.example.sugang.application.LectureService
import org.example.sugang.domain.Enrollment
import org.example.sugang.domain.Lecture
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.context.annotation.Description
import java.time.LocalDate
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class EnrollmentControllerTest {
  @Mock
  private lateinit var enrollmentService: EnrollmentService

  @Mock
  private lateinit var lectureService: LectureService

  @InjectMocks
  private lateinit var enrollmentController: EnrollmentController

  private val lecture = Lecture(randomId(), "특강", "", "강연자", LocalDate.of(2024, 12, 28), null)
  private val enrollment = Enrollment(
    id = randomId(),
    userId = randomId(),
    lecture = lecture,
    deletedAt = null
  )

  @Test
  @Description("유저 아이디로 특강 등록 내역 조회")
  fun getAllByUserId() {
    `when`(enrollmentService.getAll(enrollment.userId)).thenReturn(listOf(enrollment))

    val result = enrollmentController.getAllByUserId(enrollment.userId)

    assertEquals(enrollment.id, result[0].id)
    assertEquals(enrollment.userId, result[0].userId)
    assertEquals(enrollment.lecture.id, result[0].lecture.id)
    assertEquals(enrollment.createdAt, result[0].createdAt)
    assertEquals(enrollment.deletedAt, result[0].deletedAt)
  }

  @Test
  @Description("특강 등록")
  fun enroll() {
    val request = EnrollmentCreateRequest(enrollment.userId, enrollment.lecture.id)

    `when`(lectureService.get(enrollment.lecture.id)).thenReturn(lecture)
    `when`(enrollmentService.enroll(any())).thenReturn(enrollment)

    val result = enrollmentController.enroll(request)

    assertEquals(enrollment.id, result.id)
    assertEquals(enrollment.userId, result.userId)
    assertEquals(enrollment.lecture.id, result.lecture.id)
    assertEquals(enrollment.createdAt, result.createdAt)
    assertEquals(enrollment.deletedAt, result.deletedAt)
  }
}
