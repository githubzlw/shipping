package com.cynergy.mapper;

import java.util.List;

import com.cynergy.main.ReadExcelVO;

public interface ContractItemsMapper {
	/**
	 * 多产品多合同 上传excel后保存
	 * 批量插入到合同产品表
	 * @Title insertBatch
	 * @Description
	 * @param items1 新插入
	 * @param items2 更新
	 * @throws Exception
	 * @return void
	 */
	void addAndUpdate(List<ReadExcelVO> items1,List<ReadExcelVO> items2)throws Exception;
    
	/**
	 * 多产品多合同 上传excel后保存
	 * 批量插入到合同产品表
	 * @Title insertBatch 
	 * @Description
	 * @param items
	 * @throws Exception
	 * @return void
	 */
	void insertBatch(List<ReadExcelVO> items)throws Exception;
	
	
	
	
	
	/**
	 * 当出现多合同单产品时   批量插入到合同产品表
	 * @Title insertBatch 
	 * @Description
	 * @param items
	 * @throws Exception
	 * @return void
	 */
	void insertBatchSingle(List<ReadExcelVO> items)throws Exception;
	
	
	
	
	/**
	 * 当出现多合同单产品时   批量插入到合同产品表
	 * @Title insertBatch 
	 * @Description
	 * @param items
	 * @throws Exception
	 * @return void
	 */
	void insertBatchSingle(int proId)throws Exception;
	
}
