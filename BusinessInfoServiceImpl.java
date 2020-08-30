package com.neusoft.hit.backgroundmanagement.component.service.impl;

import com.neusoft.hit.backgroundmanagement.component.service.BusinessInfoService;
import com.neusoft.hit.backgroundmanagement.entities.BusinessInfo;
import com.neusoft.hit.backgroundmanagement.mapper.BusinessInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class BusinessInfoServiceImpl implements BusinessInfoService {
    @Resource
    private BusinessInfoMapper businessInfoMapper;

    @Override
    public void insertBusinessInfo(BusinessInfo businessInfo) {
        businessInfoMapper.insert(businessInfo);
    }

    @Override
    public void updateBusinessInfo(BusinessInfo businessInfo) {
        businessInfoMapper.update(businessInfo);
    }

    @Override
    public void updateValidFlag(BusinessInfo businessInfo) {
        businessInfoMapper.updateValidFlag(businessInfo);
    }

    @Override
    public List<BusinessInfo> selectBusinessInfo(BusinessInfo businessInfo) {
        return businessInfoMapper.selectList(businessInfo);
    }

    @Override
    public BusinessInfo selectId(String id) {
        return businessInfoMapper.selectId(id);
    }
}
