package org.example.sugang.infrastructure

import org.example.sugang.domain.Enrollment
import org.example.sugang.domain.EnrollmentRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class EnrollmentRepositoryImpl(
  private val jpaEnrollmentRepository: JpaEnrollmentRepository
) : EnrollmentRepository {
  override fun findById(id: Long): Enrollment? {
    return jpaEnrollmentRepository.findByIdOrNull(id)
  }

  override fun findByUserId(userId: Long): List<Enrollment> {
    return jpaEnrollmentRepository.findAllByUserId(userId)
  }

  override fun findByUserIdAndLectureId(userId: Long, lectureId: Long): List<Enrollment> {
    return jpaEnrollmentRepository.findByUserIdAndLectureId(userId, lectureId)
  }

  override fun save(enrollment: Enrollment): Enrollment {
    return jpaEnrollmentRepository.save(enrollment)
  }
}
