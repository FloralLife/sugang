package org.example.sugang.infrastructure

import jakarta.persistence.LockModeType
import org.example.sugang.domain.LectureParticipantCount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface JpaLectureParticipantCountRepository : JpaRepository<LectureParticipantCount, Long> {
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT l FROM LectureParticipantCount l WHERE l.lecture.id = :lectureId")
  fun findByLectureIdWithLock(lectureId: Long): LectureParticipantCount?
}

