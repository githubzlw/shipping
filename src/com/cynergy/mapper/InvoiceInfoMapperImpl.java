package com.cynergy.mapper;

import com.cynergy.main.DBHelper;
import com.cynergy.pojo.CaseFund;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceInfoMapperImpl implements InvoiceInfoMapper {
    @Override
    public Map<String,CaseFund> getContractMoney(List<String> lstCase) {
        Map<String,CaseFund> lst = new HashMap<>();
        if(lstCase == null || lstCase.isEmpty()){
            return lst;
        }
        Connection connection = DBHelper.getConnectionERP();
        Statement stmt= null;
        ResultSet rst = null;
        String sql = "select  iCaseNo,sum(ISNULL(iimoney,0)) as iimoney,sum(ISNULL(ifmoney,0)) " +
                "as ifmoney from InvoiceInfo " +
                "where iCaseNo in (";
        for(int i=0,size=lstCase.size();i<size;i++){
            sql = sql + "'"+lstCase.get(i)+"'";

            sql = i==size-1 ? sql:sql + ",";
        }
        sql +=") GROUP BY iCaseNo;";
        try{

            stmt = connection.createStatement();
            rst = stmt.executeQuery(sql);
            CaseFund caseFund = null;
            while(rst.next()){
                caseFund = new CaseFund();
                String caseno = rst.getString("iCaseNo");
                caseFund.setCaseno(caseno);
                caseFund.setOrderActualMoney(rst.getString("iimoney"));
                String ifmoney = rst.getString("ifmoney");
                ifmoney = StringUtils.equals(ifmoney,".00") ? "0.00" : ifmoney;
                caseFund.setOrderAmountReceived(ifmoney);
                lst.put(caseno,caseFund);
            }
        }catch(Exception e){

        }finally {
            DBHelper.close(connection,stmt,rst);
        }
        return lst;
    }
}
