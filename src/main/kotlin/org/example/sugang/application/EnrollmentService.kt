package org.example.sugang.application

import org.example.sugang.domain.Enrollment
import org.example.sugang.domain.EnrollmentRepository
import org.example.sugang.domain.LectureParticipantCountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EnrollmentService(
  val enrollmentRepository: EnrollmentRepository,
  val lectureParticipantCountRepository: LectureParticipantCountRepository
) {

  fun getAll(userId: Long): List<Enrollment> {
    return enrollmentRepository.findByUserId(userId)
  }

  fun get(id: Long): Enrollment {
    return enrollmentRepository.findById(id) ?: throw IllegalArgumentException("$id 특강 등록 내역이 존재하지 않습니다.")
  }

  @Transactional
  fun enroll(enrollment: Enrollment): Enrollment {
    val participantCount = lectureParticipantCountRepository.findByLectureIdWithLock(enrollment.lecture.id)!!
    participantCount.enroll()
    return enrollmentRepository.save(enrollment)
  }

  @Transactional
  fun cancel(enrollment: Enrollment) {
    enrollment.cancel()
    val participantCount = lectureParticipantCountRepository.findByLectureId(enrollment.lecture.id)
      ?: throw IllegalStateException("${enrollment.lecture.id} 특강에 대한 등록 카운트가 유효하지 않습니다.")
    participantCount.cancel()
  }
}
