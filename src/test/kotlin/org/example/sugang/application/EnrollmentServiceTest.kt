package org.example.sugang.application

import org.example.sugang.TestUtils.randomId
import org.example.sugang.domain.Enrollment
import org.example.sugang.domain.EnrollmentRepository
import org.example.sugang.domain.Lecture
import org.example.sugang.domain.LectureParticipantCount
import org.example.sugang.domain.LectureParticipantCountRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.context.annotation.Description
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class EnrollmentServiceTest {
  @Mock
  private lateinit var enrollmentRepository: EnrollmentRepository

  @Mock
  private lateinit var lectureParticipantCountRepository: LectureParticipantCountRepository

  @InjectMocks
  private lateinit var enrollmentService: EnrollmentService

  private val lecture = Lecture(randomId(), "특강", "", "강연자", LocalDate.of(2024, 12, 28), null)

  @Test
  @Description("User id로 등록된 내역 조회")
  fun getAll() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    `when`(enrollmentService.getAll(enrollment.userId))
      .thenReturn(listOf(enrollment))

    val result = enrollmentService.getAll(enrollment.userId)

    assertEquals(1, result.size)
    assertEquals(enrollment.userId, result[0].userId)
    assertEquals(enrollment.lecture, result[0].lecture)
    assertEquals(enrollment.deletedAt, result[0].deletedAt)
  }

  @Test
  @Description("id로 특강 등록 내역 조회")
  fun get() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    `when`(enrollmentRepository.findById(enrollment.userId)).thenReturn(enrollment)

    val result = enrollmentService.get(enrollment.userId)

    assertEquals(enrollment.id, result.id)
    assertEquals(enrollment.userId, result.userId)
    assertEquals(enrollment.lecture, result.lecture)
    assertEquals(enrollment.deletedAt, result.deletedAt)
  }

  @Test
  @Description("존재하지 않는 특강 내역 조회시 에러 발생")
  fun getThenIllegalArgumentException() {
    val id = randomId()

    `when`(enrollmentRepository.findById(id)).thenReturn(null)

    assertThrows(IllegalArgumentException::class.java) { enrollmentService.get(id) }
  }

  @Test
  @Description("특강 등록하면 카운트도 증가")
  fun enroll() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    val participantCount = LectureParticipantCount(randomId(), lecture, 0)
    `when`(enrollmentRepository.findByUserIdAndLectureId(enrollment.userId, enrollment.lecture.id))
      .thenReturn(listOf())
    `when`(lectureParticipantCountRepository.findByLectureId(lecture.id))
      .thenReturn(participantCount)

    enrollmentService.enroll(enrollment)

    verify(enrollmentRepository).save(enrollment)
    assertEquals(1, participantCount.count)
  }

  @Test
  @Description("한 유저가 같은 특강에 두번 등록하면 에러 발생")
  fun enrollDuplicatedThenIllegalStateException() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    val participantCount = LectureParticipantCount(randomId(), lecture, 0)

    `when`(enrollmentRepository.findByUserIdAndLectureId(enrollment.userId, enrollment.lecture.id))
      .thenReturn(listOf(enrollment))

    assertThrows(IllegalStateException::class.java) { enrollmentService.enroll(enrollment) }
    verify(enrollmentRepository, never()).save(enrollment)
  }

  @Test
  @Description("특강에 등록된 내역이 30개 이상이면 에러 발생")
  fun enrollMoreThan30ThenIllegalStateException() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    val participantCount = LectureParticipantCount(randomId(), lecture, 30)

    `when`(enrollmentRepository.findByUserIdAndLectureId(enrollment.userId, enrollment.lecture.id))
      .thenReturn(listOf())
    `when`(lectureParticipantCountRepository.findByLectureId(lecture.id))
      .thenReturn(participantCount)

    assertThrows(IllegalStateException::class.java) { enrollmentService.enroll(enrollment) }
    verify(enrollmentRepository, never()).save(enrollment)
    assertEquals(30, participantCount.count)
  }



  @Test
  @Description("취소하면 count도 감소")
  fun cancel() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    val participantCount = LectureParticipantCount(randomId(), lecture, 1)

    `when`(lectureParticipantCountRepository.findByLectureId(lecture.id))
      .thenReturn(participantCount)

    enrollmentService.cancel(enrollment)

    assertEquals(0, participantCount.count)
    assertNotNull(enrollment.deletedAt)
  }

  @Test
  @Description("취소시 특강 등록 카운트가 없는 경우 (유효하지 않은 상황)")
  fun cancelWithoutParticipantCountThenIllegalStateException() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    `when`(lectureParticipantCountRepository.findByLectureId(lecture.id))
      .thenReturn(null)

    assertThrows(IllegalStateException::class.java) { enrollmentService.cancel(enrollment) }
  }

  @Test
  @Description("취소시 특강 등록 카운트가 0 인 경우 (유효하지 않은 상황)")
  fun cancelWith0ParticipantCountThenIllegalStateException() {
    val enrollment = Enrollment(
      id = randomId(),
      userId = randomId(),
      lecture = lecture,
      deletedAt = null
    )

    val participantCount = LectureParticipantCount(randomId(), lecture, 0)

    `when`(lectureParticipantCountRepository.findByLectureId(lecture.id))
      .thenReturn(participantCount)

    assertThrows(IllegalStateException::class.java) { enrollmentService.cancel(enrollment) }
  }
}
