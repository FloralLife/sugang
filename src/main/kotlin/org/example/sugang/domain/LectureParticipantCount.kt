package org.example.sugang.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "lecture_participant_count")
class LectureParticipantCount(
  @Id
  @OneToOne(cascade = [(CascadeType.ALL)])
  @JoinColumn(name = "lecture_id")
  val lecture: Lecture,
  var count: Int
) {
}
