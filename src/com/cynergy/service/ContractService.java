package com.cynergy.service;

import com.cynergy.pojo.ContractWrap;

import java.util.List;

public interface ContractService {

    /**
     * 更新
     * @param contract
     * @return
     */
    int updateContract(ContractWrap contract);

    /**
     * 插入
     * @param contract
     * @return
     */
    int addContract(ContractWrap contract);

    /**
     * 获取
     * @return
     */
    List<ContractWrap> contracts();

}
