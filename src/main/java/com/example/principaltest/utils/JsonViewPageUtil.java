package com.example.principaltest.utils;

import com.example.principaltest.models.ApiResponseView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * This class is used to wrap the Page object with the JsonView annotation.
 * This is useful when you want to expose only certain fields of the Page object.
 * <p>
 * Example:
 * <pre>
 *     <code>
 *         &#64;JsonView({
 *             ApiResponseView.ParameterList.class
 *         })
 *         public ResponseEntity<ApiResponse> findAll(Pageable pageable) {
 *             return ResponseEntity.ok(new ApiResponse(RESPONSE_TAG_USERS, new JsonViewPageUtil<>(repository.findAll(pageable), pageable)));
 *         }
 *     </code>
 * </pre>
 *
 * @param <T> The type of the content of the Page object.
 */

@JsonView({
        ApiResponseView.StudentList.class
})
public class JsonViewPageUtil<T> extends PageImpl<T> {

    public JsonViewPageUtil(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public JsonViewPageUtil(final Page<T> page, final Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalElements());
    }

    public JsonViewPageUtil(List<T> content) {
        super(content);
    }

    @JsonView(ApiResponseView.Always.class)
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @JsonView(ApiResponseView.Always.class)
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @JsonView(ApiResponseView.Always.class)
    public boolean hasNext() {
        return super.hasNext();
    }

    @JsonView(ApiResponseView.Always.class)
    public boolean isLast() {
        return super.isLast();
    }

    @JsonView(ApiResponseView.Always.class)
    public boolean hasContent() {
        return super.hasContent();
    }

    @JsonView(ApiResponseView.Always.class)
    public List<T> getContent() {
        return super.getContent();
    }
}
