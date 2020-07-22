package com.cynergy.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.cynergy.main.PropertisUtil;
import com.cynergy.main.ReadExcelUtils;
import com.cynergy.main.ReadExcelVO;


public class UploadExcelServlet extends HttpServlet {

	/**
	 * @fieldName serialVersionUID
	 * @fieldType long
	 * @Description
	 */
	private static final long serialVersionUID = 1L;
	private static PropertisUtil propertisUtil = new PropertisUtil("config.properties");
	private static String PATH =propertisUtil.get("excel_path");
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out1 = response.getWriter();
        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = propertisUtil.get("excel_path");
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            //创建目录
            file.mkdir();
        }
        try{
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
             //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8"); 
            //3、判断提交上来的数据是否是上传表单的数据
            if(!ServletFileUpload.isMultipartContent(request)){
                //按照传统方式获取数据
                return;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if(filename==null || filename.trim().equals("")){
                        continue;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    filename = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(filename);                    //重命名文件
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    ReadExcelUtils readUtil = new ReadExcelUtils(PATH+File.separator+filename, 0);
                    List<ReadExcelVO> contents = readUtil.readExcelContent1();
                   if(contents.size()>0){
                    JSONObject jsonobj = new JSONObject(); 
            		jsonobj.put("fileName", filename);
     			     //输出数据
    	 		    out1 = response.getWriter();
    	 		    out1.println(jsonobj);
                   }
                   else{
                    	JSONObject jsonobj = new JSONObject(); 
                		jsonobj.put("fileName", null);
                		out1 = response.getWriter();
        	 		    out1.println(jsonobj);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();                   
        }
			out1.flush();
			out1.close();
    }
}