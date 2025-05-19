package vn.com.pharmacity.service.impl;

import org.activiti.engine.ActivitiException;
import org.springframework.stereotype.Service;

import vn.com.pharmacity.exception.DetailException;
import vn.com.pharmacity.exception.HandlerCastException;



@Service
public class HandlerCastExceptionImpl implements HandlerCastException{


    @Override
    public void castException(Exception ex, String exceptionConstant)throws DetailException {
        if (ex instanceof ActivitiException) {
            throw new DetailException(ex.getMessage(),true);

        }
        if (ex instanceof DetailException) {
            DetailException detailException = (DetailException) ex;
            String exceptionErrorCode = detailException.getExceptionErrorCode();
            throw new DetailException(exceptionErrorCode,detailException.getParamater(),true);
        } else {
            throw new DetailException(exceptionConstant, true);
        }
    }
    
    @Override
    public void castException(Exception ex, String exceptionConstant,String[] param)throws DetailException {
        if (ex instanceof DetailException) {
            DetailException detailException = (DetailException) ex;
            String exceptionErrorCode = detailException.getExceptionErrorCode();
            throw new DetailException(exceptionErrorCode,detailException.getParamater(),true);
        } else {
            throw new DetailException(exceptionConstant, param, true);
        }
    }

}
