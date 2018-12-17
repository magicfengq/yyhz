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
import java.util.Base64;
import java.util.Base64.Decoder;
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
			//String realName = DateUtils.toString(new Date(), "yyyyMMddHHmmssSSS") + fileName.substring(fileName.lastIndexOf("."));
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
	//base64字符串转化成图片  
	public static FileInfo saveBase64File(String base64ImgStr, String FilePath)
	{
		FileInfo result = new FileInfo();
		FileOutputStream fos = null;
		try {
			String base64Data =  base64ImgStr.split(",")[1];
			String fileName=getFileExtensions(base64ImgStr.split(",")[0]);
			String realName = DateUtils.toString(new Date(), "yyyyMMddHHmmssSSS") + fileName.substring(fileName.lastIndexOf("."));
			promptMkdir(FilePath);
			String path = FilePath + "/" + realName;
			Decoder decoder = Base64.getDecoder();
			byte[] bytes = decoder.decode(base64Data);
			fos = new FileOutputStream(path);
			fos.write(bytes);
			File temp= new File(path);
			result.setFileName(realName);
			result.setRealName(realName);
			result.setRealPath(path);
			result.setSuffix(fileName.substring(fileName.lastIndexOf(".") + 1));
			result.setFsize(temp.length());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		return result;
		
	}
	public static void main(String[] args) {
		//saveImageFile("http://q.qlogo.cn/qqapp/1106065082/0436468D6EDE4C519C465A67DCC65A6F/40", "d:/");
		saveBase64File("data:image/jpeg;base64,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UuYNeg+5ubNtFhWGPbeBwWc+mDVdGaNpG3Nul+/z1p7QbrktuG3vgUTDICr827uKUdHoHNIjC55x15oZmjVn27ljG4j9KtWdi1zCxVRiPg5OKLdI3l/eN5cKn95n06UvaWFeQ99DWHTEuGmUeYdgA7Y5z+Nfo7/wbx/81e53f8gb/wBv6/OeDRpL3WP7PtxLdRMm2LapIwOQf0xX6Of8G99vJaXfxjjkjaOSNtHDKR90/wDEw4r8z8XpX4Rxaf8A07/9OwP0Xwrv/rRhf+3/AP03M/Saiiiv4hP7GCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/mytwsE6PJyqnkDvV3VZbe9nWaFWVR/CKhkgD1Ys4/sbBpPuiv8ASeT6n+eikiq8Rjk3H5VbkCmqqltw611Hj2DTrvT7OaxmWJliXzox/G2BmuZWNZDuVNi4xjNTTqc8b2sxyaTsgqFpVDH5e9WigpvlL/kVpFk8xV89f7tHnL/dq15S/wCRR5S/5FPmD2hW80f3afEd7YxtzU3lr6UBArZpNmPKw82SY7GztjppqXOd3Yt196bsUUrdi4XW4yip7eJXb5hTbiLY3FTzW0L5kU2gJY/d6+tJ5Df7P51cCjH/ANajaP8AIrTmC6KfkN/s/nUkLMbhfM+6OBU5Qf5FKYEaMlpCzL2x0pcyDmQ2eJVuNy9CKbVhdOc26yN908A5o+yAfxVHMg9oVFuVhmaNVyzDlvSr/hzX77Q9OvLe3jjkjvVMUrEZ2qcc/pVcjble3Q8UQD7NGyx8K3JGetOSTVmP2lncjsoc2v2e2+WK1O5yf4zSXLTJH9oj2xqDtPuKlM5dFXb8q9AOKdAiNGVmXcpOQD2oUkHMixcaTpcGjR3lvfPNfyuFkgJO1BjrjNUadPHiXcvyqBgbRihApPNTFND57jaNu4f496m2LUtlsjnU+X5nI+XPB5HoR24puVk2VHV6lFIMyfhmns3l/wANdP8AFTS7fS9ea8sLe3g0zUCs8ESMS0SE4xz6dawb5PJuljRd0bfdf2qKVfnSkEvdZWiuGk3DdtVRk8dRU0dytjNDfWsn76P+BuhpZoEtJOFin9d5wFHtUF2VlZVUNJngZXCqfrV3iyOdrUdJeSXV3LI+C1wxkY+hJzUazfZWZZBO1vj948X8Az3/ABxUscflIV496WNPLRsSGPPXvn2qt9GLm6nceE/ib4i+H3iqx1rSbvSzqNrakrKEUlI2DLtPv1P41zOt62vi8zXU0e6+1C5a4u5CMeYWYknH4mqNn+7tJACG3Pv6454/rzTgy3d8iXchhWZcZHGB+FcccLRhUdWKXN1drO3ReZt9YbiovYzpoY/tLfKzPGcoM/KBUtjdvarOyyeX5gxV680W3s59is0kez5XJqjHYq0m3Py+hrrjUi1sY+0syHzl3p5jvIWAPJ4qeyihk1KJpcmNSSFFTx2yx3qqwVl28U2OJYbpplO3bwvFLmVrIOfW7LFxJayW4liXaySfdI+lU7m6kKTTL8q7yzKPvZU9qkQG5+fbubOc1YW1jlK/KzXABbI7Z68VPMluNVL7FNbBY2XbEzKo3nf1UHnNamm+F9QvdGuNTtxFPYxr8zt99fp1/pUOoXv9q3izO7eZEBEcDb0oLMiYDMqt1VWIU/hUyk2lyjjUgnqZsS/aIlkVjtYZGR605Ytp+9n2rWtrextJlN5CvltGCNnrjvVEQLJ5kkePJ3/L/erTmuO6IadZQLc3n7xtuBxUnlqKkMun29m63ELPcN9xwcbaOYOZFMKI5JFVtyZIPvVvR9Dl1DTriS3ZYYYRkq/3j9KZBptvbaZHMs7yT79wB/h60TStNcNIzHc3Ug7QfwpOV9guirFduvlugIVAAR61NcSrqDebtjVVH7wN0x61a1ayt7KS3WKaQyTRhmWQYUEjtVCSJYEkRpMs/BAHy4pxdyOa2jL9n5Fpo6yWc1/DqHmZHlE/d/P61+jP/Bvm8kt18YpJGZpJG0cszfeJ/wBP6+9fm3BYtPF5ittZBtBXjAr9JP8Ag3vt5LdPi3vB5GjYJ7/8f9fmni7H/jE8Y/8Ar3/6dgfonhTO/FOFX+P/ANNzP0jooor+JD+yAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAP5vguwfN+FPQtdZVvu02f7tSQLgV/pE5dT/O8jlt0Mytt4UY69aa/7yfK9uNtWnj+WoTFsbIqo6AR+U1L5Df3f1p/zVMv3RTcmBVMLDtR5TelWJc7aj+ajmYEflN6UeU3pUnzUfNT5mBH5TelL5Df3f1p/wA1TL90UnJgV40aNulOdNwqSUcVH81LfUCMxNnpSMpXrUvzUFCxquYCHFOEG+Jv4S1SfZiO1GGUfSp5gCMSNb+WzblXoKPs3+1TouDUlTsBVdChqS0gaQt8vAHPNSSxbqdGGQAD+I7afNoA3Py528Cq5lWZvxx0q9PGLaIBurVTmbyj93Ge9KIAWCoR1H51GYSozjuV69xyasWZFkjTfe3rjHpVzTZLO50ST7SGEuwL9G7/AKUc3KVuZqRM6j3p3lyRIzK2xwvy/wC1VhQoHyfd/h+lAiaU4XtzRzGHtBJFWKJWWGaRkiCqWOQCScjp260phaERruaRWHLY6VK9/NAgj2q2OKu67Y33h2zt9PnmspoNSQXAaPHmR7hnafcZrGUn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Y5Z4xktNtB6Ej8+lSxW8j3C+epEfTdjK/nVcyS1M/e2KltI0sDbY8Z4PPWiMssJ2Dbt4NXPsS3Gsom+WO1z8xjQsfyAzUssNtHdtHDIWt8nlgVbHuDzU+0Vy4qTW5nwDzYdwUbm4ye1Lb2Jjn8r5Czfx56VLcyQLIvl5a3z8xAIxSXjW0bL9l3svcntRzXHd9x+sWVj9gVYbqQ3EZw2F4yOtVgLi4Kxt84Rc5Jpz28kduxXLBju+5/WiK4ZJf4lJXBJqo6LTckjmdk48v5qLfy5JCrSxLKRkKgOfxyOtSyqrtnefm61d1me3uLS3jgWaFVjAlDMGWQ456dPxpOTTsOxXVY2s445pLpJ/NweAY8c9+tMubnyZ2iimZ0x6DirN/qFvLo1vZx2traKs4keRE3SkYI7VXvo4oZ9tnIXicfMXQof1pRk76jlEiPlwopZZkz1LY2sfWrN3pcNtpUd2l1DIXfaIFHzL79KgaBJQu5pjs4AZvlNRyW43+ZtiHYBV+aqaelg5mW7bW7hNKbTo41aKQ5wQM5+tfol/wb+J5Q+LS42lP7HUj3H26vzlSVkG9W2SemK/Rv8A4N/mLn4tM33mOjlj6n/T6/N/FyNuE8X/ANw//TsD9G8JZN8WYRf9fP8A01M/Ruiiiv4pP7UCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/nR+z/AOc0fZj/AJNPkDIRxSuG2V/o1zM/ze5is0OCeKWNNtXLOBZB84yaGtt7tt6IMij2nRlcxVoxVq+0O80q3juLi32wzfcOahji3v8AdwF7f3qFJPYfMQum6m+V7VYkXfJuC7R/doEeR0quYXOiv5X+c0eV7VMyeQv71ZUDchzjGKkVYVgUK6yN/ewaPadCyr5XtVzR2hj1OH+0pD9lwQ23svvTQqntTlgUxuu0bZB82e9TKV1qLmSK85t5Z7g27s0JlzED/dx/jUYixVr7MBJCsaqNpxjpkUG3KXLrtXCjjmjm6E86G20LT/MV4j4ollWZ/u7scVJFPJDGVVto9MVCVxJ8yk7jz70cxfN0HWiT+VPth/ditWxeLUPDkf2/UYbMackjRWkVuu+dDgtk5BJOPfNRaHqcej3Z8+1FxZt96Mk8/rUVxDC9z50DRW8Pml41OSYhnIB9cCsJe87f1+ppGSWqNfx3cR61cw3zXFxeXU0a/vPK2RwqoChOvpXMyxY7VvXmkabpWnRyQagLqS4fMluM4U4PzY/LiqIjjmeQBPlHQUqMlGNokVdZXM8gtB83SrCw28dirR7hNnkml8ryYCQcfNSXgYRdeSM9Olbc13Yw5iM01NPMp3RnJPJx2ohjaW1Q7/mz81TQP9lzt/H3qnKysOMl1IX323ys3NMSK2lb9/K6irDyeZJu6n6U+cpJEPkH/fNLnSNPaRKbQRo/zBvL/gb1HanGB7Zd2xvLbgE1acteLGsnzLGAAMYxjpUk13Jc20dtJLthRt2NtLnFzRKen2y3Lkyfdqa20q31GOdnn8toWKoPUCpZrSEQ8SYY+lMSxBCsV3FR19aXtOocyKUcfkJIzSZWOp9k0dl5ksZWGTiM4q1HbR3Pn+d8q+X8uB3yKddTSXWkwwyTtNFGRsi24Io9psV7RGfCVgUArk9PrU/lpNH/AHfpSXFvwB91jzt9KIZVCbG429att7kEaRxiX7parg8PG4t2lT92wZVC56u33TRC5tryH92HjbrU11eQyCYGIK5YlCCePT8qzcmzSFkrsz9jPASIF+bK8noBw361GqwwzxQ3km1Dj5hU8dqptWVpG3ZJwD2qzYwWtxo8zyTNG0I+UEZNVzWVg5ivDZ79QkS3/wCPMZy7dWHrUlzrUn2WOxjhDWcM27d3Jx/9emXOoSajZwqkartQAOOC3HXFO0e0jnuo47qSS3hDbjJjis3teRGrehJ4YvxY6tJcQy+TJ3R1zUKCNtcuJrrdvumMmQOOeaXUrhku5G+zozq+wDGP3f8Af+vU/hTri4l1B49q5jjG0Pjt2prfm7lq60ZVliERk28xsc4otrfzbdtq1I6bm8rqo6sKfCZIE2qxVa1V7WFdCG6uFhVWkXYowB6VVmAkb7271q4gUIXVd5zyD60XdvO9kk3kLHEzY4FKMguUTBinQQM0LeW2CTzuqcw+Z8qr9W9KV7RcBWbay88d6p1A5kP+y2lpYW8yc30MwZ89CuDkVL4j1G38VSR3Cr9naEY2KMZqCaLzUXuF7+9MC4fd3+lR15iuboR+T5g3DgNzj0pJLfAq3BIrsd602X53bavyr2p+01M+ZFPysdq/Rr/ggDH5f/C2vf8Asf8A9vq/PEafLNAZlX5Bya/RL/ggL934sfTR/wD2+r868Wp34Txf/cP/ANOwP0bwjlfi3Cf9xP8A01M/Raiiiv4rP7YCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/nh1D7PGfLjkMkinJ+Xj8+lV2KsuM1M8e/cpXyyHwF9qYbZh/Ov9FItJH+bjuJJBJbovyN8wBHHWnRBkDHDbmGPpVq6v5iI49y7UUKOOmKbHM8krLGwLgZOancu1iO71G81OKOG4uC8MJ+RTUTSqtwGI2lOAPWnvu3/vP0qSS0jdAVbnGarRaE6vUinP2qbzAuB6Un2c+n5GpI7fyuc9acKnmtsSVXTZwnnbu+9sitC71uS80SOzaO3CxNv3IhDNwRyc+9VzbFjmrmkeFpNcjuikywm1iM3J+9ggY/8epScdGzqjcy/s/yFuOOvNCupUfSl3tcp8qlZA2M9jThat/F1rT1MJbC28kazqZAxVTmmXJja5dlVsEcU77Kf8mlW1OR9cUuZXII4U8wY6HGeTUlrbveNIIUV/LXc53AYH41c1bQ5NGntVkCzJdxBxtPK5Gapi1lsTJFGvlqfmznlvb9aObm2OizT1I7NvtG5VbCrwQamSWa7h8nFvGqHAJQ/MB+NRpb74NzfI3cetTxtsiVduMgc+tDfYSFuEhTbtihVoxlpFGCahE7JukVeJOFqcaeWdWVWkkz8qAcufSmXKva3IRo2WYDLoRyg+lTF9B2ZHt32u0/ezk0sxXcvQ5GPpSzMm48ZOe1NgtneddqZ+tVfqc5PpFjbxNJ9odlVvu4Gf5VWuLRVlbaflycH1FXLqaa2Yf6OD+FVtokkJdWUk5I9DUx3uW+yIosRMaI523nK/LU0sEeBszu75pv2Xmq5k9yAjdBnmmTFXPrUowo+6fyqxZRRzSsrjbxxmp5rFWuZ4tjnkcfWpAmxf8AWY9qsS2WH+98tR/YfNmHzcVXMmMh8trYuzNv3L8o96cB54jk87y51P3Ap5/HpUsqfZ5tv3t3H0pJl+zx+XGu4t/EO1GhBLq0FvJfwyxSHPljzRg8Njn9arWpjhMpkXdu4WnrbMqgHOQOTTltwQc9qFpoyfaaiXZklgj2/LtqqluXkZmk3NmponYzlW+7mpWt1RyyqRu7+tF7GkJcxFaw/bZtqfu3jGST/F7U6z0u41BmkWH5ZAdgI2hsfWnpbeTuuGl8vyRuXHc9KfJqd4bdvMnCR2vAA75od76GqsQRWakY4jdeDzxVm11P7JewpdQpeQqx/djjdx3PtVVYvLHRucc+tHkh436qZAAp9DRutR7Dbt3uEmmDMi+aCJCD04+T+Y/GtaTw1qGnaTDPcQi2sbw/uskbiD0wOv44qDU7xtUFvbiFY7W3TPmDje31/KpG1C61Wwjhaea8htwMFyRJGcfcTnt07VlKUmk1/XoV7u7KU+m/YZ2t4ZFlxyTuHFTXXh+8tNPF26x+S0piz5wPzDnp/kVNqHhm60qeFLvT7ixa7HmIszFpJB689qp3aNayrbtukt2OFGcjf2P48URqc2sWTtuOu7r7PbLAI1Vm4JUhj+lLFpl4lvGs8j/Y5HAUsCADj/61RGGaMPC0kO/OCBjeh9KdJczizW1maeSNW3KW5XP+TWmr2AdqVjHplykcEwdZOrU7TIreS/b7VIVhRSoIGc1GYYQo8xm8z3oiGIm470WurAR4USSrH80W7Kn2pse2QcVPAVWBs96alqfJZl+tO4tRAjBflUUMuUO75c1JHwi/SiVPMWpvqYEa3ckMJiVv3bda/RD/AIIGx+WvxW+mj/8At9X53fZjX6K/8EFBtX4q/wDcI/8Ab6vzzxY/5JXFf9w//TkD9J8If+Stwn/cT/01M/Q6iiiv4wP7bCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/nt1Kx/s+4uLWRsXUePLVOUI9M9aNRi+yXMa3EcVsrR42xktluauPoq6RoovPtWZLibDQsd7Rrjrn6gfnTZWtj4ajiazaa6Z8/aGYHaMj/69f6Fe01P82zNgtfPuefkVTyfWl1G2WOb5ZONuM1orov2vTmkSZGEfXkDOPaqlvPbnT5POj3dlIPOavmvsTqRQ2eb1tqPKqx549ajis3nRZPLIXGSK2PD11I00uLr7OrIVUFC3rWdOZ7adozJvDHlvX3ojJ3sPZEM0T+XlV8tegOafJD5SRxj527tU1oqvM0cr5jAz06VNawtBFI0YWSHsx/w60+Zi1KJikS2YlcfMQGz2pknyxKfOdVA/eKDjcP8AP8qusM2O0sSzHOMdKg+xCeRI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KACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAPwZiJlmLN8xPc8mpCnLe9OEO2l2Gv70P8AM8ZsANDxhjn+VOIxQmXbFHMFzmvif8HfC/xp8MSaP4q0PT9c02TnyrqLcYz03I33kb/aUgj1r5K8T/8ABObx5+zbrlx4g/Z/8bXlirP5s3hrVZg9tc/7KswKMccDzFDAf8tM19tMNtRizaQ5Ga8PNOHsDmElUrR5akdpxfLOPpJa/J3XkfRZLxXmOVxdGhNSpS+KnNKdOXrB6fNWfmfH/wAKf+Cnw0DxdD4R+Nnhe9+HHibhBePE/wDZ0/YNzlo1J6MC6dy4FfWWm6nb6zp8F3Z3EN3a3SCWGaGQSRyoRkMrDggjkEcGsT4ufAfwv8evCkmg+LtFs9ZsZMlFmGJIGxjfHIMNG3+0pB/CvkbVf2b/AIyf8E9NUuNW+EuoXXxC+HqSNNd+Fr7MlzaqTkmNV5Y/7cOGJxujYDNeP9dzbKP98viaH88V+8iv70VpNL+aNnu3FnvfUckz5f7A1g8S/wDl3KTdKb/uTesG+kZ3jslJH3BEoYc02WNlfhsCvG/2WP29/Af7VlktnptxJofiyFD9q0HUCEuUZfvGM9JVHPK/MAPmVa9pWFpvvV9RgM0w2NorEYSanB9U/wAPJ909V1Pjc0yrGZdXlhMdTdOpHdNfiu6fRq6fRlfY396lc5UBfvVZ/s//AGjTEtsOwz3rs5jz9SNIS0Z6Zxk806aBVaNll+q1oaNZWVxJcf2hdNaqsZaEqpbe+R8pxn361DIkUtv8q4dTww71PtLuxVtCqco5/h56UBNx5p4+Y+560oQD71URZkeyo2yGPWrW6OkMa+lCkxleIsSQDilWJv71SNEJflFAs29aq/mPUXyQQM80n2QM2VOGpQu3jPSlC56VHMMjaHZ/FU0yxmBdg+bAzz3p0Vmr/ebFR/Ztkjf3c8UcwXIZI2UU0uzCrCwu7nj5frSmIL2quYmzI0GFFJKNq/rU3agpv7UuZD1Ku9j/APqqUQLIoz/OpfI/2aAmO1PmGSWck1skiwD/AFi7Tx2qnI7W8nlc59cZxVpZ5LXO3+Lg80qQtEnzR7we+elJaB00G28hRNuQw7nHWonjVFPH1qaGLe3yjue9RzJsO00KQrkavh2qS+C3cSrhSR6Co1XEjVMUWGPc3f0FHNrcSlpqR21uIyoZtq/SrA0xpC0kbbo0Heq/l75V3r8jHjmrAMn2trWJv9b05qeZFRknoVhG+/dnbTnR3fc53GrE2nNYyCKRtz47HNM8sK2MnNVzICFl3ClEK4qXb7UhQ0rhcgnTYnFQ8mrhj3dRR5H+zVcwtSJIjtHH5ClFvh9zVMNwpoBY0rsYhQEUnkrT9hpBy+3v9aOYCMxgHgZ/GnGHA3M20e4p09uwX5m2/Sox8ww/zD3ouBC4yx28rng06NQc7m21N5Sj7vTtxTXiUjBUH0p8xPMhhtJHMfl5bdX3v/wRNi8mT4nL/EP7Kz/5O18HxmSOWIKsny/3VJr70/4IrBjP8Tmb+I6X1/7fK/P/ABOl/wAY1iV/g/8ATkD9O8Hf+Svwn/cT/wBNTPuyiiiv5KP7kCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/CPNAOatafpP9oC42SJmJd+ZOOPaoFCvGFUs0mcEn7tf3ZzH+aPKyIRsz89KcyiKrDDKBcAFRg4qP7MrbmkZlUDqKrmZj7RERG+pbaTA+7Vq0jtH0xiJG87GAarwhspt2tIvVP8AnoO1TzXRXtEiOV97Z2M23nAFN81ZPmjXa0nrWtd2F/4ekzcQrbm6XcmPm+Ws+VfPbcevXiiMr7A5WdmfOf7V/wDwTs8IftI3T61aGTwj44t282DXNNXY8kq/dMyqR5mCB8wKuMDDYGD5L4L/AGzfiZ+xJ4ps/CXx+0ybVvDtxJ5Gn+MrFTMJAOnmYA8zA5OQsoGSQ+Qa+5jApNYvjz4faL8TPCt5oev6Taa1pOoJ5c9rcpvjcevsQeQRgggEEGvl8w4d/fPHZXP2Nd72+CflOOz/AMStJb3Ps8r4wtQjludU/rGGWiTf7yn5057q38rvB2tZbjvCPjDS/H3hyz1jRdQtNU0u/jEtvdW0okilU9wR/LqDxWlPbKoU7vmbmvhfxZ+zR8Uf+Cc3ii48U/BuS98Z/D+6l87U/CVwWmntwcZaMLlmwOBIg3gAbldQTXtH7P3/AAUu+Fv7Qbw6e+pN4T8S58t9L1si3fzBwVSQny35yAMhz/dHSjL+KIOqsHma9hX7N+7LzhLaSfb4ls0PNOEKiovMcmk8Thv5or34eVSCu4td9YvdPU99+z7Bvb5h6U9HLDaq06EMTtb7tSRjyz8pK/hX1XMfFRqIrpHlulSPHsXpW74T8DTeKdNv7oXlrarpY86RZ2P74HJwMf7tQHQbk6VBqW22axupSimJifLIz/gax+s03Lk7F8r5UzGBwfu/pTzHuWrlxBGtuPLkX72Dv+9TWgZYGBUt8xCtWntLhdPRFEJh6dTpovLfbu7ZzTlOR/q1b8armAhNvu5pwj2U47s9Me3pQib+uaOZmXtl0G0VL9n96Qw4FHMP2qI2Tf320jP5f+1T1hLN93d+NPiiXztrNs9vSjmD2iGK+5c7f0of6YrSsYvs1w0jLDPD9zbNkYz34qGNIrqdiWjxv3Db9xcdqz5yuZFGirEluplkAyq/w1CAqiq5ifaIimBKjHrTlZnjxTtnmOoU4qW3i2yMCeFFVzB7RFcF1Py/jQFL9evepFLIzH+lEa7nZvnJUZwO9HML2kRnkYP/ANekhCi68s7myufpW14e8N/2pbx7rqzheZ9o85iCoP0FXtY8Of8ACI3jFbmC5YfLlRkEe1Y+3inydS5Rco8yOVjHnMzDqp2gGpEVl/eD/WL0qzeSeazssaxjlsr3NLI8RtlZS3mHqcVrzHOObTFnmj+zP++lTMhPY96pm3axkaOQ7pM8tViORk+YMc/So3GR680lc6PaKxGOlHanbKkZFkX+7gdu9O7J9oiHNFOKKvy5Yn1pxjG2nzB7RELrIvO07aEk3NjvVqG63RbW6dBRHpscjoZrjyo5m27sfdqeZpah7RPYr0SJCHU85xzV3WtKj01laO484MPMI9zwU/D71QLGuBzu98daFK4+cqynfLtFHk5qxJ5aZZsj6CmxxNPETGrNVcxPPHqQjgUB1Vhu53cDHapBD5CfOxEjdExUmnoTdxLOjLuYgqo46cE0c2lzLU0LLU7jSoVSONGDfxMK+4v+CLzM8nxMZsbnOmE46Z/0yvivwCNKt9as215ZLqyfdvS3J3HrjuK+8P8Agkfpmi2kPxBuNGuppo7qSxJgl+9bIDdeWD+BI6n7tfnPiZiF/YOJp2evJr0/iRP1jwbpt8V4Sd1/y80/7hTPsqiiiv5XP7iCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA/DEJazJItxG0nykAIwUhsg8+1NEG20VcKqjnAqeab7ZcTXDJDGzMF2p2wMfrUF793iv7oW9z/NQRoNkfy88dOtRJufcvGD61c1RLFkgOn3TedsXzUY8Bscj86b5KzybZl+6M7gacZXRxSjroV4oltE+8OewFCQFpFKxtuYjMn9wdqmWBnfbF83uafFdyaeSNqsc0SYCajqV5qTL9qkabyBsjJ9Kg2VLLM077z/ABdqbTjZLQHd77jNhp9vcm0Zvl3bhj6UUFd1VuBAIt8+9uh7V5N+0R+wz8Nv2nIZpPEvh23/ALUkXamrWWLa/j9D5gHz47CQMo9K9g8mjGK5MZg8PiqToYmCnF9Gk1+J2ZfmGKwNZYjB1JU5rZxbT+9fkfBGo6X8XP8Aglfqa6ha3mqfFL4L/KlzBOxN5oKdAR18tR2Yfum6MsZKmvsP4EfHjwt+0j4Dg8ReEdUh1LT5PllX7s1pJjJjlQ8o49D1GCCQQT2U1tHewSQzIkkUylHRxuV1PBBHcH0r5B+Mn/BMvWvh54xuvHv7P/iqXwH4ilPmXGiltum33faowVUE5xG6vHk8bAK+U+p4/JZc2Xp1sP1pt+/D/r229V/cbv8Ayvofc/2jlnEUeXNZRw+L6VkrU6nlVjFe7L/p5FWf2lpc+vyd915h2tIqhPnyVYDtirtvIqosK7l3HcMH91EfZf8ACviz4Pf8FPrrwT4xh8E/HrwzcfD3xMMImp+Wf7Nu+cByctsUn+NWePqSyDivsXSdTtdc06C8srmC6s7qNZYZ4ZBJHMjDIZWHBBHII4r3MrznB5jFywz1jpKLVpRfaUXqvy7HzGdcO5hlE1DGwtGWsZJqUJrvGSumvR3XVI7UX2jWPgVrS5sft2pNyLofLt6c1ycd4y6U0Pl55+9mkVmkyrMdtRyDEq7m/d9DivQo4fkvrvqePUxEtrDLGKIM3mLvXbgc96gktFM25Ayr9asXBtw37jdu756VHuwK6Y9zD2hJFp0ksRdV3KvU5qMxZqa2ne3hZT0Y03G1fekrjI/IPrSqmKfRTuA2QfKNp289qSJNtyNymT3qWFgkg3UXErebhRS5g5TV0fVW8NNJcRwWssUimNxPjjPpyOlUDpdwtq159laO3upflcIVizycAmq/l7omheESbzu3yHhfpWidZvX0aHTZZL64s433rGynylOMcHPuawknF8y67m0ZKSs+mxXvJI3tFXy/mX72KoeTu5A4PSrUNz9nmkO3t0IqMvvO7+9zWsTKVmRxw7XBb5aPKUSSHd97pUm3cKPJquYWhEsRCjj9adHHtlV848s7gP73tT8Yoo1CxG0OYN/lwtM7gsGB+UcVPMschXLM2OvPFMoosh9Bs8PD+U3LLjmoxFLHAg3LkdRUyLk04Wnmd/1ov0FYhVWxzQYWfoKkK7OPTilQ7W+tPYCAQsT0p/lqXG7+HjFWXj2JmoBJuPSlzBYku7hZrVY1hVdpzuz1quY2K9Kl2bTuz17elGMCiImrkMUAkVlYbcDNNSKO4gZZN0iZA69D2q3HBIkLSbflPANLbNFBGwkU/Nzj3ouO3QrCNYV7tubdn0PQn8RQi7UUegxS+dJ5x8sfKPWpM7uT170+gEMqbl/xqxaJNHbHbMqj6URw+cG9hUkC7YttTLXQEtbiwCP7M0cyiSSTlZv7lVIobiyLKsvm9T5h6n2qaSPgYanLHtQVGsUARO0A+XdG3Zojgj86+5f+CLzK0nxK+Rt3/Er3SNjMn/H51+lfDVfcn/BFzr8Sv+4X/wC3lfD+JVnw3iX/AIP/AE5A/TvBv/ksMH/3E/8ATUz7oooor+UT+6gooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAPwqjZpJZHbgsR0HYVKXLD1psbtK33ak2N6V/dVkf5hqUu5GSAMcD6DBpUGwfLn8TmlC7gfahRv6UBqOjuHhbKtj8Kax8x9zfeJyad5TelBjYdqNA1IyuSOe9SPHhetMaIy0+S2yvBqg94jAyOppw4rwj40/8FJPg98BdcutI1fxSt5rNi2yex0y2kvJI27qzqPKVh0KlwQeoryZ/wDguh8I1dgPD/xEYA4yLCzwf/JqvncVxdkuGqOlWxMFJbq6dvW17P1Pr8DwFxHjKSrYfB1HF6p8rSafVXtdea0PtDdRXxf/AMP0vhL/ANC78Rv/AAAs/wD5Ko/4fpfCX/oXfiN/4AWf/wAlVy/69ZB/0FR/H/I7P+IY8V/9AU/uX+Z9nkE9KDIzpt9a+L/+H6Xwlz/yLvxG/wDACz/+Sqe//BdP4Q+X8vh34j7vewsv/kup/wBesg/6Co/18g/4hjxV/wBAU/uX+Z9QfGL4E+E/j94Ok0HxdotnrWnPyqyriSB8Y3xuMNG3+0pB7dOK+L/FXgD4j/8ABJjW4fEPhXVNU8c/Baa5/wCJno1yQ0+kBiBvBxhSc8SIFUnCuvKk9cP+C6Xwl/6F34jf+AFn/wDJVV9U/wCC3Xwh8QaZcWMnhHx9qEd5G0DWs2nWTR3IYbTGw+0nIbOCMHg9DXz+cZzw3jWsRSxcadePw1I3uvJ/zR7xd1bax9Xw9w7xll0XhK+AnWws/jpStyvzi7+5NbqUbNO17rQ+sfg78ZfD3x7+Hth4n8L6jHqWk6gmVdeHhcfejkXqrr0Knp9CDXSoxG5exr8lfhV4++J3wS+Ol54w+Cfwk+Jlh4G1aQNdaBdabdX9ndj+IKyRfJgH5DlmT+8ykqf1Z8EazceK/B2k6rc6beaPcalZQ3cthdDbPZPIisYZB2dCSp9wa9/hXiZZpSlGpFxqQ3dpKMv70G0nZ9nqvxPlOOuDHkdaNSjUUqVTWKcoucHu4TUW1zLa6bi/J6GuluoTGMVFKgU1ZHSoZomY9K+tTPgtia68ueKMQv8APtG7AqR7aO3s1Z1+cn7xNU4IdjZXg9zTpxJMcbt2OxqdehXMwkTcflp32eREG6PjHXPWo1jdD0/WpGuZCuDI2MYxigEyNpQrfKuW/lQXfcNvze9OhKiVc5Abhj6CpNQtorJlW1m8yNupIIx+dHUNbEbOyp65OTmrGn65d2i7E/eR/wBwgED9Ki8vMY78dfWkgRom+Vtue/rRo0GoXE7TOzOu1iefeoncH7tTyEk8/N7moHUHp1pxC7FVJANw/Wmm4b+6ufrUgjYxfMu5ewzRaWkfmHzFZR9afmw12EW4UEMy8L1GetJNMJ5N0a7F9KeLcSM7H5djEIPUdqSBGkZty7fandBqIBj73WkK89TRKDHSrGzLTuGoRyxwnMudvQY7U+3ZDN/rHMZ71G0eP4dwbg57VJFdvYqYVVWjb+LFSw5ncme0UXwhU7vMG4N6Cq86rHKyq24KcZ96dFM0cZb70mcA+i0+6tVh2+S29WGWJ7Gp1uVzERlZlxninRncmGTbjoab5belBMmPUDtQTdjE+W4UqNy9SCanutt3IvkqsO3quetOSWNYs+X83XrVeQea/nKpXb2zRcNbakzXsog8tm+UHpio2lZ/f04oAaUbtv3ucelHlt6VXoHM2NPNFGeaUIT2qg1Ficq/DFc+gpLXfPcSLGrNtp0QZW4x+NSQNNbzySRyqu72qHuF2V2TBw2Qw6+1LhmHy9qJGYuzOcsTkn1NCttG7nFP1C7GiVn+VfvV91f8EWsj/hZWev8AxK8/+TlfDOY0GVYlq+5v+CLTbv8AhZX/AHC//byvhPEr/km8T/25/wCnIH6d4Mv/AIzHB/8AcT/01M+6KKKK/lA/u4KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigD8MoITGe1S9q8b+Jf/BQb4L/AAqSRdU+IXh+aePgw6dMdQlB/ulYA5B+uMd8V4h4h/4LJ6N4q1P+zfhf8O/G3j/VF4Cpb+RGxPQgIJZCPqi/1r+yMZxVlGFfLVxEebsnzS/8Bjd/gf53ZdwLn+Njz0MLPl/mkuSP/gU+WP4n2fE/msX6bDjHrSibz5N23b2xXxJ/bX7Z37RR/wBD0zwn8I9Km4WW42NdbO+Q3nyBvfZH+HWra/8ABL34meK4/M8XftJeOr1ph++tbP7QsKZ4YKWuNuCP+ma9+DXCuJsVX/3LBVJLvLlpr5c75n/4CelLg3A4bTMsyo05fyw5qzXr7OLiv/Aj7B8UeMdI8D6Y19rWqabo9mn3ri9uUt4l+rOQK8L+JP8AwVM+B/w38yOTxrb61cpnEOjwSXu/HpIo8r83Fef+Hv8Agix8N/7WW98UeJPHHi+ZQMpeX6xxue+SqeZz7OPxr6B+Fn/BPL4X/DG3juNF+HvhXT2hAMdxqNv9rnx2KyTb35+uaJYriGtrCnSor+9KVSX3RUV/5MXDBcI4fSdaviX/AHIRpR9G5Ocvuij5wP8AwXJ+Fty7Raf4X+Il9eMdsUQsbUeafbFwT/47n2rD174sfHz/AIKJ6gfD/g/w/qnwd+H7KF1LWdRWSO8u0bqsbYVmBH8EWAejyANivvNtPSzAiX5UjG1RGB5KgcYH+FMjjzJy7Y+lTPI8xxK9nj8a5U3vGEFTbXZy5pSs+tmrrQ0p8S5Pgpe2yvLlGqvhlUqOqovuocsYtrpzJ2ep4h+zn/wT2+F/7OHhuG2sfDun65qww0+satax3V5M/cqWUiJf9lMDgZyeT7bClvbeVHIoWBBtCqMBAOgAqxdxImzZ3Azz1qMpk7fX1r6LA4HDYOkqOFgoRXRK3/DnxeZZrjMwrPEY2rKpN7uTb/4b0WiK88YW5kaNT5H8FSocoueuKmdjJbrEx/dr0GKZJEN67a7OY88bQelLIyxyKu3O79KcLV1uFTG4N39KOYCo1sxb/wCvT4YjG1WZIlSRl3H5TjpSKi/3qOcBlFOdfSovm96q4D6KbHyak2VPMBCT5ftTo1yc1I8SuOaABFxjIo5gG9aPLX0pxi302OFRLy1HMA14PM+7+tK9nJ5fanywoJvlYmgxBh/F+dHMAxRtXHpxS04R4FBUAUcwDetMnRVUH1qfytkW400wLLGpz70cwEUTkin0GHbTfK/ziquA7pQG206OzLxNJuxt7etNY+ZBuXhs4xU8wEcq+Yf505BhaaFIqZE+UVVwI5N+w7PxpLER7/3/AN2nyRe9MaHcPvVPMAOFM0nl/cz8v0qQ8iozF5dv/tbv0qbUAsckKp0K8ijmQDMZqP8A5ZN9asXO1cbV602ARxzqH3bT1FHMHUriLzYfvY2nP1qSSy89BJu27f4fWpbqCM3LeWzLH2pk8LB4izEDpn1o5gETpT0bbmnSRgSNj1pAmKOYCs0v7z7tSqcinmFSaBDtquZANo609ZI4vmZdygZpispkL/8ALNug9KVwuNl2jrTXRZIfl61YawWRMhqhaMxDCrz0zSUgIfsrf5NfdH/BFuIxf8LK/wC4X3/6/K+Hrc/L5cgUE/xZ6V9zf8EY02D4kDdu/wCQZz6/8flfDeJUr8N4n/tz/wBOQP07wZ/5LHB/9xP/AE1M+46KKK/lI/u4KKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigD+cf4Zf8EyfgX8NYy1v4G0u9uoU3CbV2fUWlbt8sxZB+CgV7Lovhqx8J6PDDY6bZ6bZwn93b2sCwxL9FUACtBolWRWaPzF2/lU9zeRSzxJu86PunpX9qYPLcLg1y4WlGC/uxS/JH+a2YZxj8fLmx1edR/3pOX5tlcv5nzYxu5xTZf4fc8VYmCiVtq7VycD0FN3FThe/wCldyZ5hDLG0R+ZT+FSXt5Pq0Ma3UhmjhACI3YDpUjzyJH5OA2f4qbt2cenFEtbNlKfKtCND8oXbtj/ALg+6KkYRquQvNFFSHtmQtueUf3f5UEH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ACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigD8QGjyKb5JqfZtpMD1r+4D/McixikK7jU/lKabJHt6UC5SLyTR5Jp2Gz0/WpVTK1PMMhVWjkXb1zUlxcSSSANinhMGkKZbd3ovqBC0JJo2+V171OVyaRkyQPU/nRzANtyttPGzjcPSm3CeZPIyjAZiQPSrN1FGrLt61Cxyx+tF2BHHHtNPpr5IpAW9KoNh0q7qVIGgeORDtkjO4Z9aYWbNPDsw+ap1tYF3JdZv2126ja6fy7lfu7ehqK4yDhjlhwaDz/POKd5QbmjZWQb6kbxZiGPWmeSasbcLikKcUczAg8k0eSaedwPSljBJo5haEfkmjyTU+ymng0cwyPyaTyafISMU15SsnSqAsyXP+gqvmY244qCf97IPn3cVNAu9cMKjnh8yQYbZt9B1qY6BuReSTUijaKUjzDx8uOx705LaQnOz5frVXDlsRyJupnkmpyuGwVo2VPMFrkaqsEbKitubkk9qkg8yO28vK/WpElWFDHt3eZ1PpUfkLGdgHXknNHMA0bdjbs/hUYhyOM47VMECrio9w8snP8WKOYBvk0nk1O4UMvPUU5o02feo5h8pGowKKYS3pTowSaq4haKdso2UXAaLxbTl13BuKdBbD7P5kbcHsaPLH1pPIUDHb60XAQdKKXaB3pQoNADajaIlqm2U2X5aBPYi8rB5okiwflqdEVk+Y4p0QBXK/dHU4qeYOUrCEmjyTU+0NzRso5g5UQeSa+4f+CNCbP8AhZH/AHDP/byviXZX23/wRs+98SP+4Z/7eV8R4jy/4x3Ef9uf+nIn6f4N/wDJYYP/ALif+mpn2/RRRX8sH91BRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQ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QAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAf/9k=", "d:/");
	}

}
