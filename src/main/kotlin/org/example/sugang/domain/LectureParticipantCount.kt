package org.example.sugang.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.example.sugang.common.Constants.MAX_PARTICIPANTS

@Entity
@Table(name = "lecture_participant_count")
class LectureParticipantCount(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L,

  @OneToOne(cascade = [(CascadeType.ALL)])
  @JoinColumn(name = "lecture_id")
  val lecture: Lecture,

  var count: Int
) {
  fun enroll() {
    check(count < MAX_PARTICIPANTS) { "특강 정원이 가득 찼습니다." }
    count++
  }

  fun cancel() {
    check(count > 0) { "특강 등록 카운트가 유효하지 않습니다."}
    count--
  }
}
