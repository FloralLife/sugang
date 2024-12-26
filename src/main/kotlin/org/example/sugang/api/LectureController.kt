package org.example.sugang.api

import org.example.sugang.api.request.LectureCreateRequest
import org.example.sugang.api.request.toEntity
import org.example.sugang.api.response.LectureResponse
import org.example.sugang.api.response.toResponse
import org.example.sugang.application.LectureService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/lectures")
class LectureController(
  val lectureService: LectureService
) {
  @GetMapping("/available")
  fun getAvailableLectures(): List<LectureResponse> {
    return lectureService.getAvailable().map { it.toResponse() }
  }

  @GetMapping("/{id}")
  fun get(@PathVariable id: Long): LectureResponse {
    return lectureService.get(id).toResponse()
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun create(@RequestBody lectureCreateRequest: LectureCreateRequest): LectureResponse {
    return lectureService.insert(lectureCreateRequest.toEntity()).toResponse()
  }
}
