package vn.com.pharmacity.service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import vn.com.pharmacity.exception.DetailException;
import vn.com.pharmacity.response.ObjectDataRes;

@SuppressWarnings("hiding")
public interface BaseRestService <T extends ObjectDataRes<E>,E extends Object>{
	 
    /**
     * <p>
     * Search.
     * </p>
     *
     * @param commonSearch
     *            type {@link MultiValueMap<String,String>}
     * @param pageable
     *            type {@link Pageable}
     * @return {@link T}
     * @throws DetailException
     *             the detail exception
     * @author Baclv
     */
    T search(MultiValueMap<String, String> commonSearch,Pageable pageable) throws DetailException ;
    
    /**
     * <p>
     * Save.
     * </p>
     *
     * @param objectDto
     *            type {@link E}
     * @return {@link E}
     * @throws DetailException
     *             the detail exception
     * @author Baclv
     */
    E save(E objectDto) throws DetailException ;

    /**
     * <p>
     * Delete.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @throws DetailException
     *             the detail exception
     * @author Baclv
     */
    void delete(Long id) throws DetailException ;

    /**
     * <p>
     * Detail.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return {@link E}
     * @throws DetailException
     *             the detail exception
     * @author Baclv
     */
    E detail(Long id) throws DetailException ;
}
