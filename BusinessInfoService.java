package com.neusoft.hit.backgroundmanagement.component.service;

import com.neusoft.hit.backgroundmanagement.entities.BusinessInfo;

import java.util.List;

public interface BusinessInfoService {

    public void insertBusinessInfo(BusinessInfo businessInfo);
    public void updateBusinessInfo(BusinessInfo businessInfo);
    public void updateValidFlag(BusinessInfo businessInfo);
    public List<BusinessInfo> selectBusinessInfo(BusinessInfo businessInfo);
    public BusinessInfo selectId(String id);
}
