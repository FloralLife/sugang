package org.example.sugang.api

import jdk.jfr.Description
import org.example.sugang.TestUtils.randomId
import org.example.sugang.api.request.LectureCreateRequest
import org.example.sugang.api.request.toEntity
import org.example.sugang.application.LectureService
import org.example.sugang.domain.Lecture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.eq
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class LectureControllerTest {
  @Mock
  private lateinit var lectureService: LectureService

  @InjectMocks
  private lateinit var lectureController: LectureController

  @Test
  @Description("신청 가능한 특강 목록 조회 테스트")
  fun getAvailable() {

    val lectures = listOf(Lecture(randomId(), "특강1", "", "강연자", LocalDate.of(2024, 12, 28), null))

    `when`(lectureService.getAvailable()).thenReturn(lectures)

    val result = lectureController.getAvailableLectures()

    assertEquals(lectures.size, result.size)
    assertEquals(lectures[0].id, result[0].id)
    assertEquals(0, result[0].participantCount)
  }

  @Test
  @Description("Id로 특강 조회 테스트")
  fun get() {
    val lecture = Lecture(randomId(), "특강1", "", "강연자", LocalDate.of(2024, 12, 28), null)

    `when`(lectureService.get(lecture.id)).thenReturn(lecture)

    val result = lectureController.get(lecture.id)
    assertEquals(lecture.id, result.id)
    assertEquals(0, result.participantCount)
  }

  @Test
  @Description("특강 생성 테스트")
  fun insert() {
    val request = LectureCreateRequest("특강1", "", "강연자", LocalDate.of(2024, 12, 28))
    val lecture = request.toEntity()

    `when`(lectureService.insert(eq(lecture))).thenReturn(lecture)

    val result = lectureController.create(request)

    assertEquals(request.name, result.name)
    assertEquals(request.description, result.description)
    assertEquals(request.instructor, result.instructor)
    assertEquals(request.date, result.date)
  }

  @Test
  @Description("특강 생성시 날짜가 토요일이 아닌 경우 에러 발생")
  fun insertNotSaturdayThenIllegalArgumentException() {
    val request = LectureCreateRequest("특강1", "", "강연자", LocalDate.of(2024, 12, 27))

    assertThrows(IllegalArgumentException::class.java) { lectureController.create(request) }
  }
}
