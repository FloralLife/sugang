package org.example.sugang.domain

interface LectureParticipantCountRepository {
  fun findByLectureId(lectureId: Long): LectureParticipantCount?

  fun save(lectureParticipantCount: LectureParticipantCount): LectureParticipantCount
}
