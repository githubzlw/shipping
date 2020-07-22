package com.cynergy.mapper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cynergy.main.DBHelper;
import com.cynergy.main.ReadExcelVO;

public class ContractItemMapperImpl implements ContractItemsMapper {

	@Override
	public void insertBatch(List<ReadExcelVO> items) throws Exception {
	    
	        //获取主表id	
			int proId=0;
			if(items!=null&&items.size()>0){
				proId = items.get(0).getProId();
			}
			
			Connection connection = DBHelper.getConnection();	
			try {
				Statement statement = connection.createStatement();
				String sql1 = "select * from items where proId = "+ proId;
				ResultSet res1 = statement.executeQuery(sql1);
				while (res1.next()) {
					int itemId = res1.getInt("id");
					String itemchn = res1.getString("itemchn");	
					String rate = res1.getString("rate");	
					String totalWeight = res1.getString("nw");
					String purchaseAmount = res1.getString("purprice");
					for (ReadExcelVO readExcelVO : items) {
						if(readExcelVO.getItemchn().equals(itemchn)){
							readExcelVO.setItemId(itemId);
							if(StringUtils.isNotBlank(rate)){
								rate= rate.replace("%", "");
								Double purprice = readExcelVO.getAmount();
								Double refundAmount = new BigDecimal(purprice).multiply(new BigDecimal(Double.parseDouble(rate))).divide(new BigDecimal(100), 2).doubleValue();
								readExcelVO.setRefundAmount(refundAmount);								
							}
							if(StringUtils.isNotBlank(totalWeight)){
								int weight = new BigDecimal(readExcelVO.getAmount()).multiply(new BigDecimal(totalWeight)).divide(new BigDecimal(purchaseAmount), 4).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
								readExcelVO.setWeight(weight);
							}
							continue;
						}
					}
				}
				res1.close();
				statement.close();
				
				//删除合同产品关联表，重新插入
				Statement createStatement = connection.createStatement();
				String sqlDel="delete contract_items where proId="+proId;
				createStatement.execute(sqlDel);
				//保存合同产品关联表
				String sql2="INSERT INTO contract_items (purno, itemchn, amount, item_id, proId,refund_amount,quantity,weight) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
				PreparedStatement statement2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
				for (ReadExcelVO readExcelVO : items) {			
					statement2.setString(1,readExcelVO.getPurno());
					statement2.setString(2,readExcelVO.getItemchn());
					statement2.setDouble(3,readExcelVO.getAmount());
					statement2.setInt(4,readExcelVO.getItemId());
					statement2.setInt(5,readExcelVO.getProId());
					if(readExcelVO.getRefundAmount()!=null){
						statement2.setDouble(6,readExcelVO.getRefundAmount());
					}else{
						statement2.setDouble(6,0.0);
					}	
					statement2.setInt(7,readExcelVO.getQuantity());
					statement2.setInt(8,readExcelVO.getWeight());
					statement2.executeUpdate();
				}		
				statement2.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally {
 			DBHelper.returnConnection(connection);
 		}
	}

	@Override
	public void insertBatchSingle(List<ReadExcelVO> items) throws Exception {
		
			
			Connection connection = DBHelper.getConnection();	
			try {				
				String sql2="INSERT INTO contract_items (purno, itemchn, amount, item_id, proId,quantity,weight) VALUES (?, ?, ?, ?, ?,?,?);";
				PreparedStatement statement2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
				for (ReadExcelVO readExcelVO : items) {
					statement2.setString(1,readExcelVO.getPurno());
					statement2.setString(2,readExcelVO.getItemchn());
					statement2.setDouble(3,readExcelVO.getAmount());
					statement2.setInt(4,readExcelVO.getItemId());
					statement2.setInt(5,readExcelVO.getProId());
					statement2.setInt(6,readExcelVO.getQuantity());
					statement2.setInt(7,readExcelVO.getWeight());
					statement2.executeUpdate();
				}		
				statement2.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally {
				DBHelper.returnConnection(connection);
		}
		
	}

	@Override
	public void insertBatchSingle(int proId) throws Exception {
		  
		            List<ReadExcelVO> items = new ArrayList<ReadExcelVO>();								
					Connection connection = DBHelper.getConnection();	
					try {
						Statement statement = connection.createStatement();						
						String sqlCount = "select count(1)as total from contract where proId = "+ proId;
						String sqlCount1 = "select count(1)as total from items where proId = "+ proId;	
						ResultSet resCount = statement.executeQuery(sqlCount);						
						//判断是合同多个还是产品多个
						int contractCount = 0;
						int productCount = 0;
						if(resCount.next()){
							contractCount = resCount.getInt("total");							
						}
						ResultSet resCount1 = statement.executeQuery(sqlCount1);
						if(resCount1.next()){
							productCount = resCount1.getInt("total");							
						}
						String sql = "select * from contract where proId = "+ proId;
						String sql1 = "select * from items where proId = "+ proId;						
						
						
						Statement statement1 = connection.createStatement();
						ResultSet res = statement.executeQuery(sql);
						ResultSet res1 = statement1.executeQuery(sql1);
						
						
						//当产品和合同都为1个的时候
						if(productCount == 1 && contractCount == 1){
							String purno = "";
							if(res.next()){
								purno = res.getString("purno");						
							}							
							if(res1.next()) {
								ReadExcelVO readExcelVO = new ReadExcelVO();
								readExcelVO.setPurno(purno);
								Double purprice = Double.parseDouble(res1.getString("purprice"));
								readExcelVO.setAmount(purprice);
								readExcelVO.setItemchn(res1.getString("itemchn"));
								readExcelVO.setItemId(res1.getInt("id"));
								readExcelVO.setProId(proId);
								readExcelVO.setQuantity(Integer.parseInt(res1.getString("quantity")));
								//更新的时候如果已有退税率，计算退税金额
								if(StringUtils.isNotBlank(res1.getString("rate"))){
									String rate = res1.getString("rate");
									rate= rate.replace("%", "");
									Double refundAmount = new BigDecimal(purprice).multiply(new BigDecimal(Double.parseDouble(rate))).divide(new BigDecimal(100), 2).doubleValue();
									readExcelVO.setRefundAmount(refundAmount);
								}
								if(StringUtils.isNotBlank(res1.getString("nw"))){
									readExcelVO.setWeight((int)Double.parseDouble(res1.getString("nw")));
								}
								items.add(readExcelVO);
							}
						}
						//如果是多合同多产品，未上传excel 则不进行操作
						if((productCount > 1 && contractCount > 1) || productCount == 0 || contractCount == 0){
							return;
						}
						
						//当产品为多个的时候
						if(productCount > 1){
							String purno = "";
							if(res.next()){
								purno = res.getString("purno");						
							}							
							while (res1.next()) {
								ReadExcelVO readExcelVO = new ReadExcelVO();
								readExcelVO.setPurno(purno);	
								Double purprice = Double.parseDouble(res1.getString("purprice"));
								readExcelVO.setAmount(purprice);
								readExcelVO.setItemchn(res1.getString("itemchn"));
								readExcelVO.setItemId(res1.getInt("id"));
								readExcelVO.setProId(proId);
								readExcelVO.setQuantity(Integer.parseInt(res1.getString("quantity")));
								//更新的时候如果已有退税率，计算退税金额
								if(StringUtils.isNotBlank(res1.getString("rate"))){
									String rate = res1.getString("rate");
									rate= rate.replace("%", "");
									Double refundAmount = new BigDecimal(purprice).multiply(new BigDecimal(Double.parseDouble(rate))).divide(new BigDecimal(100), 2).doubleValue();
									readExcelVO.setRefundAmount(refundAmount);
								}
								if(StringUtils.isNotBlank(res1.getString("nw"))){
									readExcelVO.setWeight((int)Double.parseDouble(res1.getString("nw")));
								}
								items.add(readExcelVO);
							}
						}
						//当合同为多个的时候
						if(contractCount > 1){
							String itemchn = "";
							int itemId = 0;
							String rate = "";
							int totalQuantity = 0;             //产品数量
							Double purchaseAmount = 0.0;      //采购总金额
							Double totalWeight = 0.0;         //总重量
							if(res1.next()){
								itemchn = res1.getString("itemchn");
								itemId = res1.getInt("id");
								rate = res1.getString("rate");
								totalQuantity = Integer.parseInt(res1.getString("quantity"));
								purchaseAmount =Double.parseDouble(res1.getString("purprice"));
								totalWeight = Double.parseDouble(res1.getString("nw"));
							}							
							while (res.next()) {
								ReadExcelVO readExcelVO = new ReadExcelVO();
								readExcelVO.setPurno(res.getString("purno"));	
								readExcelVO.setAmount(Double.parseDouble(res.getString("rmb")));
								readExcelVO.setItemchn(itemchn);
								readExcelVO.setItemId(itemId);
								readExcelVO.setProId(proId);
								//更新的时候如果已有退税率，计算退税金额
								if(StringUtils.isNotBlank(rate)){								
									rate= rate.replace("%", "");
									Double refundAmount = new BigDecimal(Double.parseDouble(res.getString("rmb"))).multiply(new BigDecimal(Double.parseDouble(rate))).divide(new BigDecimal(100), 2).doubleValue();
									readExcelVO.setRefundAmount(refundAmount);
								}
								int quantity = new BigDecimal(Double.parseDouble(res.getString("rmb"))).multiply(new BigDecimal(totalQuantity)).divide(new BigDecimal(purchaseAmount), 4).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
								readExcelVO.setQuantity(quantity);
								int weight = new BigDecimal(Double.parseDouble(res.getString("rmb"))).multiply(new BigDecimal(totalWeight)).divide(new BigDecimal(purchaseAmount), 4).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
								readExcelVO.setWeight(weight);
								items.add(readExcelVO);
							}
						}
						
						resCount.close();
						resCount1.close();
						res.close();
						res1.close();
						statement.close();
						
						
						
						//删除合同产品关联表，重新插入
						Statement createStatement = connection.createStatement();
						String sqlDel="delete contract_items where proId="+proId;
						createStatement.execute(sqlDel);						
						//保存合同产品关联表
						String sql2="INSERT INTO contract_items (purno, itemchn, amount, item_id, proId, refund_amount,quantity,weight) VALUES (?, ?, ?, ?, ?, ?, ?,?);";
						PreparedStatement statement2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);
						for (ReadExcelVO readExcelVO : items) {
							statement2.setString(1,readExcelVO.getPurno());
							statement2.setString(2,readExcelVO.getItemchn());
							statement2.setDouble(3,readExcelVO.getAmount());
							statement2.setInt(4,readExcelVO.getItemId());
							statement2.setInt(5,readExcelVO.getProId());
							if(readExcelVO.getRefundAmount()!=null){
								statement2.setDouble(6,readExcelVO.getRefundAmount());
							}else{
								statement2.setDouble(6,0.0);
							}
							statement2.setInt(7,readExcelVO.getQuantity());
							statement2.setInt(8,readExcelVO.getWeight());
							statement2.executeUpdate();
						}		
						statement2.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception();
				}finally {
		 			DBHelper.returnConnection(connection);
		 		}
		
	}


}
