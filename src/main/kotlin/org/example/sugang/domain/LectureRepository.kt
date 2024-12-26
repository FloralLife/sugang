package org.example.sugang.domain

import java.time.LocalDate

interface LectureRepository {
  fun findAllByDateAndCountLessThan30(date: LocalDate): List<Lecture>

  fun findById(id: Long): Lecture?

  fun save(lecture: Lecture): Lecture
}
