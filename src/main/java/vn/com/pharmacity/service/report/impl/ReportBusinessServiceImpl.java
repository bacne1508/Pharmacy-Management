package vn.com.pharmacity.service.report.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import vn.com.pharmacity.annotation.CoreReadOnlyTx;
import vn.com.pharmacity.dto.ReportBusinessDto;
import vn.com.pharmacity.entity.ReportBusiness;
import vn.com.pharmacity.repository.ReportBusinessRepository;
import vn.com.pharmacity.response.ObjectDataRes;
import vn.com.pharmacity.service.impl.BaseRestServiceImpl;
import vn.com.pharmacity.service.report.ReportBusinessService;

/**
 * @author Bac
 * @date 2025/6/13
 */
@CoreReadOnlyTx
@Service
@RequiredArgsConstructor
@Log4j
public class ReportBusinessServiceImpl
extends BaseRestServiceImpl<ObjectDataRes<ReportBusinessDto>, ReportBusinessDto, Long>
implements ReportBusinessService {
    
    private final ReportBusinessRepository reportBusinessRepository;

    @Override
    protected List<ReportBusinessDto> findAllByCondition(MultiValueMap<String, String> params) {
        String fileName = params.getFirst("fileName");

        List<ReportBusiness> entities = reportBusinessRepository.searchAllByCondition(fileName);
        return entities.stream().map(ReportBusinessDto::new).collect(Collectors.toList());
    }

    /**
     * @param params
     * @return
     */
    @Override
    public ReportBusinessDto findById(Long id) {
        // TODO Auto-generated method stub
        return reportBusinessRepository.findById(id);
    }

    @Override
    protected ReportBusinessDto saveEntity(ReportBusinessDto entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void deleteEntity(Long id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected ObjectDataRes<ReportBusinessDto> createDataRes(Page<ReportBusinessDto> page) {
        // TODO Auto-generated method stub
        return null;
    }

}
