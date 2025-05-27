package vn.com.pharmacity.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.pharmacity.dto.MedicineBranchDto;
import vn.com.pharmacity.entity.MedicineBranch;

public interface MedicineBranchRepository extends DbRepository<MedicineBranch, Long>  {

    List<MedicineBranch> searchAllByCondition(@Param("branchCode") String branchCode, 
               @Param("branchName") String branchName, @Param("phone") String phone, @Param("manager") String manager);

    @Modifying
    void saveData(@Param("form") MedicineBranchDto dto);

    @Modifying
    void updateData(@Param("form") MedicineBranchDto dto);

    @Modifying
    MedicineBranchDto updateDate(@Param("form") MedicineBranch entity);

    List<MedicineBranch> getDataByCondition(@Param("branchCode") String branchCode);

}
