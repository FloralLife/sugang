package org.example.sugang.api.response

import org.example.sugang.domain.Enrollment
import java.time.LocalDateTime

data class EnrollmentResponse(
  val id: Long,
  val userId: Long,
  val lecture: LectureResponse,
  val createdAt: LocalDateTime,
  val deletedAt: LocalDateTime?,
)

fun Enrollment.toResponse(): EnrollmentResponse = EnrollmentResponse(
  id = id,
  userId = userId,
  lecture = lecture.toResponse(),
  createdAt = createdAt,
  deletedAt = deletedAt,
)
