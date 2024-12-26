package org.example.sugang.domain

interface EnrollmentRepository {
  fun findById(id: Long): Enrollment?

  fun findByUserId(userId: Long): List<Enrollment>

  fun save(enrollment: Enrollment): Enrollment
}
