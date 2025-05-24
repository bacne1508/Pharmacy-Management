package vn.com.pharmacity.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * Base REST controller that provides common CRUD operations.
 *
 *@author Bac
 *@date 2025/5/20
 *
 * @param <T> the type of response object
 * @param <E> the type of entity object
 */
@RequiredArgsConstructor
public abstract class BaseRestController<T extends ObjectDataRes<E>, E> {

    protected final BaseRestService<T, E> baseService;

    @GetMapping("/all")
    public ResponseEntity<ResponseVO> getAll(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<E> result = baseService.search(params, pageable);
            if (result == null || result.getContent().isEmpty()) {
                return ResponseEntity.ok(ResponseVO.buildFailure("No data"));
            }
            Map<String, Object> response = new HashMap<>();
            response.put("users", result.getContent());
            response.put("currentPage", result.getNumber());
            response.put("totalItems", result.getTotalElements());
            response.put("totalPages", result.getTotalPages());
            
            return ResponseEntity.ok(ResponseVO.buildSuccess(response));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseVO> add(@RequestBody E dto) {
        try {
            ResponseVO result = baseService.save(dto);
            return ResponseEntity.ok(ResponseVO.buildSuccess(result));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<ResponseVO> edit(@RequestBody E dto) {
        try {
            ResponseVO result = baseService.save(dto);
            return ResponseEntity.ok(ResponseVO.buildSuccess(result));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<ResponseVO> delete(@RequestParam("id") Long id) {
        try {
            baseService.delete(id);
            return ResponseEntity.ok(ResponseVO.buildSuccess("Deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<ResponseVO> detail(@RequestParam("id") Long id) {
        try {
            ResponseVO result = baseService.detail(id);
            return ResponseEntity.ok(ResponseVO.buildSuccess(result));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseVO.buildFailure(e.getMessage()));
        }
    }
}
