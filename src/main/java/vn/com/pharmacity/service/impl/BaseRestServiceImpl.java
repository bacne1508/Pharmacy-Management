package vn.com.pharmacity.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * BaseRestServiceImpl là lớp cơ sở cho các dịch vụ RESTful, cung cấp các phương
 * thức chung để tìm kiếm, lưu trữ và xoá các đối tượng DTO.
 * 
 * @author Bac
 * @date 2025/5/20
 *
 * @param <T>  Kiểu trả về của phương thức search
 * @param <E>  Kiểu của đối tượng DTO
 * @param <ID> Kiểu của ID (thường là Long)
 */
@CoreReadOnlyTx
@Log4j
public abstract class BaseRestServiceImpl<T extends ObjectDataRes<E>, E, ID> implements BaseRestService<T, E> {
    /**
     * Các repository cụ thể sẽ được cung cấp bởi class con (override)
     */
    protected abstract List<E> findAllByCondition(MultiValueMap<String, String> params);

    protected abstract E findById(ID id);

    protected abstract E saveEntity(E entity);

    protected abstract void deleteEntity(ID id);

    @SuppressWarnings("unchecked")
    protected List<E> toDtoList(List<?> entityList) {
        // Default implementation giả định entity và dto giống nhau hoặc
        // override trong class con khi cần convert entity -> dto
        return (List<E>) entityList;
    }

    /**
     * Phương thức để tạo ObjectDataRes từ Page, để class con override trả về đúng kiểu T
     */
    protected abstract T createDataRes(Page<E> page);

    @Override
    public Page<E> search(MultiValueMap<String, String> commonSearch, Pageable pageable) {
        try {
            List<E> fullList = findAllByCondition(commonSearch);
            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), fullList.size());
            List<E> pagedList = fullList.subList(start, end);

            return new PageImpl<>(pagedList, pageable, fullList.size());
        } catch (Exception e) {
            log.error("Error during search operation", e);
            return Page.empty(pageable);
        }
    }

    @Override
    public ResponseVO save(E objectDto) {
        try {
            return ResponseVO.buildSuccess(saveEntity(objectDto));
        } catch (Exception e) {
            log.error("Error saving entity", e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseVO delete(Long id) {
        try {
            deleteEntity((ID) id);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            log.error("Error deleting entity", e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseVO detail(Long id) {
        try {
            E dto = findById((ID) id);
            if (dto == null) {
                return ResponseVO.buildFailure("Entity not found");
            }
            return ResponseVO.buildSuccess(dto);
        } catch (Exception e) {
            log.error("Error retrieving detail", e);
            return ResponseVO.buildFailure(e.getMessage());
        }
    }
}
