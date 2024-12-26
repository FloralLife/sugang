package org.example.sugang.infrastructure

import org.example.sugang.domain.Lecture
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface JpaLectureRepository : JpaRepository<Lecture, Long> {
  fun findAllByDateAndParticipantCountCountLessThan(date: LocalDate, count: Int): List<Lecture>
}
