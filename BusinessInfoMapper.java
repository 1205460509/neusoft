package com.neusoft.hit.backgroundmanagement.mapper;

import com.neusoft.hit.backgroundmanagement.entities.BusinessInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BusinessInfoMapper {

   void insert(BusinessInfo businessInfo);
   void update(BusinessInfo businessInfo);
   void updateValidFlag(BusinessInfo businessInfo);
   List<BusinessInfo> selectList(BusinessInfo businessInfo);
   BusinessInfo selectId(String id);
}
