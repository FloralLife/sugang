package org.example.sugang.api.request

import org.example.sugang.domain.Lecture
import java.time.LocalDate

data class LectureCreateRequest(
  val name: String,
  val description: String?,
  val instructor: String,
  val date: LocalDate
)

fun LectureCreateRequest.toEntity(): Lecture = Lecture(
  name = name,
  description = description,
  instructor = instructor,
  date = date,
  participantCount = null
)
