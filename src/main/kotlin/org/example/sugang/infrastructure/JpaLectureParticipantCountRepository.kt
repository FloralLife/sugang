package org.example.sugang.infrastructure

import org.example.sugang.domain.LectureParticipantCount
import org.springframework.data.jpa.repository.JpaRepository

interface JpaLectureParticipantCountRepository : JpaRepository<LectureParticipantCount, Long>
