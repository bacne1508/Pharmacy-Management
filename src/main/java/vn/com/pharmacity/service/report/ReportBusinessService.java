package vn.com.pharmacity.service.report;

import org.springframework.stereotype.Service;

import vn.com.pharmacity.dto.ReportBusinessDto;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.BaseRestService;

@Service
public interface ReportBusinessService extends BaseRestService<ObjectDataRes<ReportBusinessDto>, ReportBusinessDto> {

    ReportBusinessDto findById(Long id);

}
