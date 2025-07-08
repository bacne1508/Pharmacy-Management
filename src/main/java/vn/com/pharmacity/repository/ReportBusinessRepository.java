package vn.com.pharmacity.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.ReportBusinessDto;
import vn.com.pharmacity.entity.ReportBusiness;

/*
 * @Author: Bac
 * @Date: 2025-06-13
 */
public interface ReportBusinessRepository extends DbRepository<ReportBusiness, Long> {

    /**
     * Save a ReportBusiness entity.
     *
     * @param report the ReportBusiness entity to save
     */
    @Modifying
    void saveReport(@Param("report") ReportBusiness report);

    /**
     * Search all ReportBusiness entities by fileName condition.
     *
     * @param fileName the name of the file to search for
     * @return a list of ReportBusiness entities matching the condition
     */
    List<ReportBusiness> searchAllByCondition(@Param("fileName") String fileName);

    /**
     * Find a ReportBusiness entity by its ID.
     *
     * @param id the ID of the ReportBusiness entity to find
     * @return the ReportBusinessDto corresponding to the given ID, or null if not
     *         found
     */
    ReportBusinessDto findById(@Param("id") Long id);

}
