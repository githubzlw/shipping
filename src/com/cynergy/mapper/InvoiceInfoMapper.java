package com.cynergy.mapper;

import com.cynergy.pojo.CaseFund;

import java.util.List;
import java.util.Map;

public interface InvoiceInfoMapper {

    /**
     * 获取项目级的合同金额+实际进账金额
     * @param lstCase
     * @return
     */
    Map<String,CaseFund> getContractMoney(List<String> lstCase);

}
