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
}
