package com.yyhz.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.yyhz.sc.data.model.FileInfo;

public class FileTool {
	public static FileInfo saveImageFile(String fileURL, String FilePath) {
		FileInfo result = null;
		HttpURLConnection httpConn = null;
		InputStream inputStream = null;
		OutputStream out = null; 
		
        try {
	
	        URL url = new URL(fileURL);
	        httpConn = (HttpURLConnection) url.openConnection();
	        
	        //设置请求方式为"GET"  
	        httpConn.setRequestMethod("GET");
	        //超时响应时间为5秒  
	        httpConn.setConnectTimeout(5 * 1000);
	 
	        int responseCode = httpConn.getResponseCode();
	 
	        // always check HTTP response code first
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {
	                // extracts file name from header field
	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } else {
	                // extracts file name from URL
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	            
	            fileName = StringUtils.appendIfMissing(fileName, getFileExtensions(contentType));
	 
	            System.out.println("Content-Type = " + contentType);
	            System.out.println("Content-Disposition = " + disposition);
	            System.out.println("Content-Length = " + contentLength);
	            System.out.println("fileName = " + fileName);
	 
				String fname = fileName.substring(0, fileName.indexOf("."));
				System.out.println(fname);
				System.out.println(fileName);
				String realName = DateUtils.toString(new Date(), "yyyyMMddHHmmssSSS") + fileName.substring(fileName.lastIndexOf("."));
				promptMkdir(FilePath);
				String path = FilePath + "/" + realName;

	            // opens input stream from the HTTP connection
	            inputStream = httpConn.getInputStream();
				out = new FileOutputStream(path); // 建立一个上传文件的输出流

				IOUtils.copy(inputStream, out);

				result = new FileInfo();

				result.setFileName(fname);
				result.setRealName(realName);
				result.setRealPath(path);
				result.setSuffix(fileName.substring(fileName.lastIndexOf(".") + 1));
				result.setFsize(contentLength);

	            System.out.println("File downloaded");
	        } else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	        }

        }catch (Exception ex) {
        	ex.printStackTrace();
        }finally {
        	if(httpConn != null) {
            	httpConn.disconnect();    		
        	}
        	IOUtils.closeQuietly(inputStream);
        	IOUtils.closeQuietly(out);
        }
        
		return result;
	}

	
	public static FileInfo saveFile(MultipartFile file, String FilePath) {
		FileInfo result = new FileInfo();
		try {
			String fileName = file.getOriginalFilename();// yyyyMMddHHmmssSSS
			String fname = fileName.substring(0, fileName.indexOf("."));
			System.out.println(fname);
			System.out.println(fileName);
			
			String realName = DateUtils.toString(new Date(), "HHmmss")+UUIDUtil.getUUID() + fileName.substring(fileName.lastIndexOf("."));
			InputStream in = file.getInputStream();// 把文件读入

			promptMkdir(FilePath);
			String path = FilePath + "/" + realName;
			OutputStream out = new FileOutputStream(path); // 建立一个上传文件的输出流
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			out.close();
			in.close();
			result.setFileName(fname);
			result.setRealName(realName);
			result.setRealPath(path);
			result.setSuffix(fileName.substring(fileName.lastIndexOf(".") + 1));
			result.setFsize(file.getSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param file
	 *            上传的文件
	 * @param filePath
	 *            文件路径
	 * @param targetW
	 *            目标宽
	 * @param targetH
	 *            目标高
	 * @return 文件名
	 */
	public static FileInfo saveFile(MultipartFile file, String filePath, int targetW, int targetH) {
		FileInfo result = new FileInfo();
		try {
			String fileName = file.getOriginalFilename();// yyyyMMddHHmmssSSS
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String realName = DateUtils.toString(new Date(), "yyyyMMddHHmmssSSS")
					+ fileName.substring(fileName.lastIndexOf("."));
			InputStream in = file.getInputStream();// 把文件读入

			promptMkdir(filePath);
			String path = filePath + "/" + realName;
			BufferedImage image = ImageIO.read(in);
			if (targetW > 0 && targetH > 0) {
				image = resize(image, targetW, targetH);
			}
			//
			result.setFileName(fileName);
			result.setHeight(image.getHeight());
			result.setRealName(realName);
			result.setRealPath(path);
			result.setSuffix(fileName.substring(fileName.lastIndexOf(".")));
			result.setWidth(image.getWidth());
			//
			OutputStream out = new FileOutputStream(path);
			ImageIO.write(image, suffix, out);
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * @category 立刻创建文件夹的方法 1个参数 path表示服务器路径
	 */
	public static boolean promptMkdir(String path) {
		File myFilePath = new File(path.toString());
		try {
			if (myFilePath.isDirectory()) {
				// System.out.println("此文件夹以存在
				// "+CurrentTime.currentTime("yyyy-MM-dd", 0));
				return false;
			} else {
				myFilePath.mkdirs();
				System.out.println("新建目录成功 " + path + "  " + DateUtils.toString(new Date(), "yyyy-MM-dd HH:mm"));
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 改变图片的尺寸
	 * 
	 * @param source
	 *            源文件
	 * @param targetW
	 *            目标长
	 * @param targetH
	 *            目标宽
	 */
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH) throws IOException {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) {
			// handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			// 固定宽高，宽高一定要比原图片大
			target = new BufferedImage(targetW, targetH, type);
			// target = new BufferedImage(800, 600, type);
		}

		Graphics2D g = target.createGraphics();

		// 写入背景
		// g.drawImage(ImageIO.read(new File("ok/blank.png")), 0, 0, null);

		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(sPath);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
 
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0fM" : "%.2fM", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0fK" : "%.2fK", f);
        } else
            return String.format("%d B", size);
	}
	
	private static String getFileExtensions(String contentType)
	{
		if(StringUtils.contains(contentType, "image/bmp")) {
			return ".bmp";
		}else if(StringUtils.contains(contentType, "image/gif")) {
			return ".gif";
		}else if(StringUtils.contains(contentType, "image/jpeg")) {
			return ".jpg";
		}else if(StringUtils.contains(contentType, "image/png")) {
			return ".png";
		}
		return "";
	}
//	public static FileInfo saveFile(MultipartFile file, String FilePath, int degree) {
//		FileInfo result = new FileInfo();
//		try {
//			String fileName = file.getOriginalFilename();// yyyyMMddHHmmssSSS
//			String fname = fileName.substring(0, fileName.indexOf("."));
//			System.out.println(fname);
//			System.out.println(fileName);
//			String realName = DateUtils.toString(new Date(), "yyyyMMddHHmmssSSS")
//					+ fileName.substring(fileName.lastIndexOf("."));
//			InputStream in = ImageCut.rotateImg(ImageIO.read(file.getInputStream()), degree, Color.WHITE);// 把文件读入
//			promptMkdir(FilePath);
//			String path = FilePath + "/" + realName;
//			OutputStream out = new FileOutputStream(path); // 建立一个上传文件的输出流
//			int bytesRead = 0;
//			byte[] buffer = new byte[8192];
//			while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
//				out.write(buffer, 0, bytesRead);
//			}
//			out.close();
//			in.close();
//			//
//			result.setFileName(fname);
//			result.setRealName(realName);
//			result.setRealPath(path);
//			result.setSuffix(fileName.substring(fileName.lastIndexOf(".") + 1));
//			result.setFsize(file.getSize());
//			//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	public static void main(String[] args) {
		saveImageFile("http://q.qlogo.cn/qqapp/1106065082/0436468D6EDE4C519C465A67DCC65A6F/40", "d:/");
	}

}
