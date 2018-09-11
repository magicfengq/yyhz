package com.yyhz.sc.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yyhz.sc.base.controller.BaseController;
import com.yyhz.utils.stream.config.Configurations;

/**
 * 
* @ClassName: DownloadFileController 
* @Description: 文件下载Controller
* @author lipeng 
* @date 2017-01-18 00:44:40 
* @Copyright：
 */
@Controller
public class DownloadFileController extends BaseController {
	
	/**
     * 返回文件res
     * @param filePath
     * @return
     */
	@RequestMapping(value = "downFileResult")
    public void buildFileResult(HttpServletRequest req,HttpServletResponse resp,String urlPath){
    	OutputStream os = null;
		FileInputStream is = null;
		// 设置响应头，206支持断点续传  
        int http_status=200;
        
        String filePath = Configurations.getFileRepository() + File.separator + urlPath;
        String fileName = urlPath.substring(urlPath.lastIndexOf("/") + 1,urlPath.length());
        
        try{
        	if("".equals(fileName)){
        		filePath = filePath.replace("/", File.separator);
        		fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1);
        	}
        	fileName = fileName.replace("\\", File.separator).replace("/", File.separator);
	        resp.setContentType(getContentType(fileName));
			File file = new File(filePath);
			os = resp.getOutputStream();
			is = new FileInputStream(file);
			long fSize = file.length();
			resp.setHeader("Accept-Ranges", "bytes");
			resp.setHeader("Content-Length", fSize + "");
			if("".equals(fileName)){
				fileName = new String(fileName.split(File.separator)[fileName.split(File.separator).length-1].getBytes("gb2312"), "ISO8859-1");
			}else{
				fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
			}
			resp.setHeader("Content-Disposition", "attachment;filename="+fileName);
			
			//下载起始位置  
            long since=0;
            //下载结束位置  
            long until = fSize-1;
            //获取Range，下载范围  
            String range=req.getHeader("range");
            if(range!=null){
                //剖解range
                range=range.split("=")[1];
                String[] rs=range.split("-");
                since=Integer.parseInt(rs[0]);
                if(rs.length>1){
                    until=Integer.parseInt(rs[1]);
                }
                http_status=206;
            }
            resp.setStatus(http_status);
            //设置响应头  
            resp.setHeader("Accept-Ranges", "bytes");
            resp.setHeader("Content-Range", "bytes "+since+"-"+ until + "/"  + file.length());
            resp.setHeader("Content-Length", "" + (until-since+1));
            //定位到请求位置  
            is.skip(since);
            byte[] buffer=new byte[10240];
            int len;
            boolean full=false;
            long cursor = since;
            //读取，输出流  
            while((len=is.read(buffer))>0){
            	cursor+=len;
                if(cursor-1>until){
                    len=(int) (len-(cursor-until-1));  
                    full=true;
                }
                os.write(buffer, 0, len);
                if(full)
                    break;  
            }  
            //输出  
            os.flush();
		} catch (FileNotFoundException e) {
//			404
			System.out.println("下载文件异常,文件不存在");
			resp.setStatus(404);
		} catch (IOException e) {
			System.out.println("下载文件异常");
			resp.setStatus(500);
		}finally{
			if(null!=is){
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
    }
    
    private String getContentType(String fileName){
    	String contentType = "application/octet-stream";
    	if (fileName.toLowerCase().matches(".*.avi$")) {
			contentType = "video/avi";
		} else if (fileName.toLowerCase().matches(".*.bmp$")) {
			contentType = "application/x-bmp";
		} else if (fileName.toLowerCase().matches(".*.(doc|docx)$")) {
			contentType = "application/msword";
		} else if (fileName.toLowerCase().matches(".*.(jpe|jpeg|jpg)$")) {
			contentType = "image/jpeg";
		} else if (fileName.toLowerCase().matches(".*.mp4$")) {
			contentType = "video/mp4";
		} else if (fileName.toLowerCase().matches(".*.png$")) {
			contentType = "image/png";
		} else if (fileName.toLowerCase().matches(".*.rmvb$")) {
			contentType = "application/vnd.rn-realmedia-vbr";
		} else if (fileName.toLowerCase().matches(".*.mp3$")) {
			contentType = "audio/mp3";
		}
		return contentType;
    }
	
}
