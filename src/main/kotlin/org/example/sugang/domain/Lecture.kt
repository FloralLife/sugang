package org.example.sugang.domain

import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
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

  val startedAt: LocalDateTime,

  @CreatedDate
  val createdAt: LocalDateTime = LocalDateTime.MIN,
) {
}
