package org.example.sugang.api.response

import org.example.sugang.domain.Lecture
import java.time.LocalDate
import java.time.LocalDateTime

data class LectureResponse(
  val id: Long,
  val name: String,
  val description: String?,
  val instructor: String,
  val date: LocalDate,
  val participantCount: Int,
  val createdAt: LocalDateTime,
)

fun Lecture.toResponse(): LectureResponse = LectureResponse(
  id = id,
  name = name,
  description = description,
  instructor = instructor,
  date = date,
  participantCount = participantCount?.count ?: 0,
  createdAt = createdAt,
)
