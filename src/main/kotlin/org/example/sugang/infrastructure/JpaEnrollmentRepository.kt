package org.example.sugang.infrastructure

import org.example.sugang.domain.Enrollment
import org.springframework.data.jpa.repository.JpaRepository

interface JpaEnrollmentRepository : JpaRepository<Enrollment, Long> {
  fun findAllByUserId(userId: Long): List<Enrollment>

  fun findByUserIdAndLectureId(userId: Long, lectureId: Long): List<Enrollment>
}
