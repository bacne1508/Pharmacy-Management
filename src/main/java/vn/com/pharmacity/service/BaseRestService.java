package vn.com.pharmacity.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.pharmacity.exception.DetailException;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.webapp.ResponseVO;

/**
 * <p>
 * BaseRestService.
 * </p>
 *
 * @param <T> type {@link ObjectDataRes}
 * @param <E> type {@link Object}
 * @author Bac
 */
public interface BaseRestService <T extends ObjectDataRes<E>,E extends Object>{
	 
    /**
     * <p>
     * Search.
     * </p>
     *
     * @param commonSearch type {@link MultiValueMap}
     * @param pageable     type {@link Pageable}
     * @return {@link T}
     * @throws DetailException the detail exception
     * @author Bac
     */
    Page<E> search(MultiValueMap<String, String> commonSearch, Pageable pageable);

    
    /**
     * <p>
     * Save.
     * </p>
     *
     * @param objectDto type {@link E}
     * @return {@link ResponseVO}
     * @throws DetailException the detail exception
     * @author Bac
     */
    ResponseVO save(E objectDto);

    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param id type {@link Long}
     * @return {@link ResponseVO}
     * @throws DetailException the detail exception
     * @author Bac
     */
    ResponseVO delete(Long id);

    /**
     * <p>
     * Detail.
     * </p>
     *
     * @param id type {@link Long}
     * @return {@link ResponseVO}
     * @throws DetailException the detail exception
     * @author Bac
     */
    ResponseVO detail(Long id);
}
