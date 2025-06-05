package vn.com.pharmacity.service.category;

import java.util.Collection;

import vn.com.pharmacity.dto.CommonDto;
import vn.com.pharmacity.dto.MedicineStorageDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

public interface MedicineStorageService extends BaseRestService<ObjectDataRes<MedicineStorageDto>, MedicineStorageDto> {

    Collection<CommonDto> findAll();

}
