package org.example.sugang.domain

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "lectures")
@EntityListeners(AuditingEntityListener::class)
class Lecture(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L,

  var name: String,

  var description: String?,

  val instructor: String,

  var date: LocalDate,

  @OneToOne(mappedBy = "lecture", fetch = FetchType.LAZY)
  val participantCount: LectureParticipantCount?,

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  val createdAt: LocalDateTime = LocalDateTime.now(),
) {
  init {
    validateDate()
  }

  fun initLectureParticipantCount(): LectureParticipantCount {
    return LectureParticipantCount(
      lecture = this,
      count = 0
    )
  }

  fun validateDate() {
    require(date.dayOfWeek == DayOfWeek.SATURDAY) { "특강은 토요일에만 가능합니다." }
  }
}
