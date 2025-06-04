package vn.com.pharmacity.service.category;

import java.util.Collection;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineBranchDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

public interface MedicineBranchService extends BaseRestService<ObjectDataRes<MedicineBranchDto>, MedicineBranchDto> {

    Collection<CommonDto> findAll();

}
