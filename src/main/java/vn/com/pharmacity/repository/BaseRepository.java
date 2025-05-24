package vn.com.pharmacity.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.MultiValueMap;

/**
 * BaseRepository chuẩn hóa cho các repository sử dụng MirageSQL + Sparwings
 * @param <E> Entity
 * @param <ID> Kiểu ID (thường là Long)
 */
@NoRepositoryBean
public interface BaseRepository<E, ID extends Serializable> extends DbRepository<E, ID> {
    
    /**
     * Tìm kiếm bản ghi theo code và name
     * 
     * @param code mã
     * @param name tên
     * @return danh sách bản ghi tìm thấy
     */
    default boolean isSoftDeleted(E entity) {
        return false;
    }
    
    Page<E> search(MultiValueMap<String, String> params, Pageable pageable);
    
    // --- Thêm method findById trả về Optional<E> ---
    default Optional<E> findById(ID id) {
        E entity = findOneById(id);
        return Optional.ofNullable(entity);
    }
    
    // Phương thức lấy entity theo id (mirageSQL có thể đã có method tương tự)
    E findOneById(ID id);

    // --- Thêm method deleteById ---
    default void deleteById(ID id) {
        E entity = findOneById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    // Xoá entity
    void delete(E entity);
}
