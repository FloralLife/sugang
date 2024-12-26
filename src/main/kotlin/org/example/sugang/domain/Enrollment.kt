package org.example.sugang.domain

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "enrollments")
@EntityListeners(AuditingEntityListener::class)
class Enrollment(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "lecture_id", nullable = false)
  val lecture: Lecture,

  val userId: Long,

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  var createdAt: LocalDateTime = LocalDateTime.now(),

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  var deletedAt: LocalDateTime?,
) {
  fun cancel() {
    deletedAt = LocalDateTime.now()
  }
}
