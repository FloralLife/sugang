package org.example.sugang.application

import jdk.jfr.Description
import org.example.sugang.TestUtils.randomId
import org.example.sugang.domain.Lecture
import org.example.sugang.domain.LectureParticipantCount
import org.example.sugang.domain.LectureParticipantCountRepository
import org.example.sugang.domain.LectureRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.capture
import java.time.LocalDate


@ExtendWith(MockitoExtension::class)
class LectureServiceTest {
  @Mock
  private lateinit var lectureRepository: LectureRepository

  @Mock
  private lateinit var lectureParticipantCountRepository: LectureParticipantCountRepository

  @InjectMocks
  private lateinit var lectureService: LectureService

  @Test
  @Description("id로 특강 조회")
  fun get() {
    val id = randomId()
    val lecture = Lecture(
      id = id,
      name = "특강1",
      description = null,
      instructor = "강연자1",
      date = LocalDate.of(2024, 12, 28),
      participantCount = null
    )

    `when`(lectureRepository.findById(id)).thenReturn(lecture)

    val result = lectureService.get(id)
    assertEquals(id, result.id)
    assertEquals(lecture.name, result.name)
    assertEquals(lecture.description, result.description)
    assertEquals(lecture.instructor, result.instructor)
    assertEquals(lecture.date, result.date)
    assertEquals(lecture.participantCount, result.participantCount)
  }

  @Test
  @Description("id로 특강 조회시 값이 없을 때 에러 반환")
  fun getThenIllegalArgumentException() {
    val id = randomId()

    `when`(lectureRepository.findById(id)).thenReturn(null)

    assertThrows(IllegalArgumentException::class.java) { lectureService.get(id) }
  }

  @Test
  @Description("특강 생성 기능")
  fun insert() {
    val lecture = Lecture(randomId(), "특강1", "", "강연자1", LocalDate.of(2024, 12, 28), null)

    `when`(lectureRepository.save(lecture)).thenReturn(lecture)

    val result = lectureService.insert(lecture)

    verify(lectureParticipantCountRepository).save(any())
    assertEquals(lecture.id, result.id)
  }
}
