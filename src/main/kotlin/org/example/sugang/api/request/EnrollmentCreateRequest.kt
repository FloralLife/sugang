package org.example.sugang.api.request

import org.example.sugang.domain.Enrollment
import org.example.sugang.domain.Lecture

data class EnrollmentCreateRequest(
  val userId: Long,
  val lectureId: Long
)

fun EnrollmentCreateRequest.toEntity(lecture: Lecture): Enrollment = Enrollment(
  userId = userId,
  lecture = lecture,
  deletedAt = null
)
