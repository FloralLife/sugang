package org.example.sugang.infrastructure

import org.example.sugang.domain.LectureParticipantCount
import org.example.sugang.domain.LectureParticipantCountRepository
import org.springframework.stereotype.Repository

@Repository
class LectureParticipantCountRepositoryImpl(
  private val jpaLectureParticipantCountRepository: JpaLectureParticipantCountRepository
) : LectureParticipantCountRepository {
  override fun findByLectureId(lectureId: Long): LectureParticipantCount? {
    return jpaLectureParticipantCountRepository.findByLectureId(lectureId)
  }

  override fun save(lectureParticipantCount: LectureParticipantCount): LectureParticipantCount {
    return jpaLectureParticipantCountRepository.save(lectureParticipantCount)
  }
}
