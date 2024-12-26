package org.example.sugang.application

import org.example.sugang.domain.Lecture
import org.example.sugang.domain.LectureParticipantCountRepository
import org.example.sugang.domain.LectureRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class LectureService(
  val lectureRepository: LectureRepository,
  val lectureParticipantCountRepository: LectureParticipantCountRepository
) {
  fun getAvailable(): List<Lecture> {
    return lectureRepository.findAllByDateAndCountLessThan30(LocalDate.now())
  }

  fun get(id: Long): Lecture {
    return lectureRepository.findById(id) ?: throw IllegalArgumentException("$id 특강이 존재하지 않습니다.")
  }

  @Transactional
  fun insert(lecture: Lecture): Lecture {
    val result = lectureRepository.save(lecture)
    lectureParticipantCountRepository.save(result.initLectureParticipantCount())
    return result
  }
}
