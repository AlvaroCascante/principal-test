package com.example.principaltest.controllers;

import com.example.principaltest.models.ApiPostRequest;
import com.example.principaltest.models.ApiResponse;
import com.example.principaltest.models.ApiResponseView;
import com.example.principaltest.models.Student;
import com.example.principaltest.services.StudentService;
import com.example.principaltest.utils.JsonViewPageUtil;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

import static com.example.principaltest.utils.Utils.DefaultData.SORT_FIELD;
import static com.example.principaltest.utils.Utils.ResponseTags.RESPONSE_TAG_STUDENT;
import static com.example.principaltest.utils.Utils.ResponseTags.RESPONSE_TAG_STUDENTS;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @JsonView({ApiResponseView.StudentList.class})
    @GetMapping
    public ResponseEntity<ApiResponse> findAll(Pageable pageable) {
        log.debug(String.format("findAll -- Page: %s", pageable.toString()));

        Pageable pageControl = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, SORT_FIELD)));

        return ResponseEntity.ok(
                new ApiResponse(
                        RESPONSE_TAG_STUDENTS,
                        new JsonViewPageUtil<>(studentService.findAll(pageControl), pageControl)));
    }

    @JsonView(ApiResponseView.StudentDetail.class)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable("id") Long id) {
        log.debug(String.format("findById: %s", id.toString()));

        Optional<Student> entity = studentService.findById(id);

        return entity.map(value -> ResponseEntity.ok(new ApiResponse(RESPONSE_TAG_STUDENT, value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    @JsonView(ApiResponseView.StudentDetail.class)
    public ResponseEntity<ApiResponse> create(@RequestBody ApiPostRequest request,
                                              UriComponentsBuilder uriComponentsBuilder,
                                              Principal principal) {
        log.debug(String.format("create %s", request.student().toString()));

        // This line throws a NPE because Principal is null
        Student entity = studentService.createStudent(request.student(), principal.getName());

        URI locationOfNewEntity = uriComponentsBuilder
                .path("/students/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewEntity).build();
    }
}
