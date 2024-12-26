package org.example.sugang.api

import org.example.sugang.api.request.EnrollmentCreateRequest
import org.example.sugang.api.request.toEntity
import org.example.sugang.api.response.EnrollmentResponse
import org.example.sugang.api.response.toResponse
import org.example.sugang.application.EnrollmentService
import org.example.sugang.application.LectureService
import org.example.sugang.domain.Enrollment
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/enrollments")
class EnrollmentController(
  private val enrollmentService: EnrollmentService,
  private val lectureService: LectureService
) {
  @GetMapping
  fun getAllByUserId(@RequestParam(required = true) userId: Long): List<EnrollmentResponse> {
    return enrollmentService.getAll(userId).map { it.toResponse() }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun enroll(@RequestBody enrollmentCreateRequest: EnrollmentCreateRequest): EnrollmentResponse {
    val lecture = lectureService.get(enrollmentCreateRequest.lectureId)
    return enrollmentService.enroll(enrollmentCreateRequest.toEntity(lecture)).toResponse()
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun cancel(@PathVariable id: Long) {
    val enrollment: Enrollment = enrollmentService.get(id)
    enrollmentService.cancel(enrollment)
  }
}
