package org.example.sugang.infrastructure

import org.example.sugang.common.Constants.MAX_PARTICIPANTS
import org.example.sugang.domain.Lecture
import org.example.sugang.domain.LectureRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class LectureRepositoryImpl(val jpaLectureRepository: JpaLectureRepository) : LectureRepository {
  override fun findAllByDateAndCountLessThan30(date: LocalDate): List<Lecture> {
    return jpaLectureRepository.findAllByDateAndParticipantCountCountLessThan(date, MAX_PARTICIPANTS)
  }

  override fun findById(id: Long): Lecture? {
    return jpaLectureRepository.findByIdOrNull(id)
  }

  override fun save(lecture: Lecture): Lecture {
    return jpaLectureRepository.save(lecture)
  }
}
