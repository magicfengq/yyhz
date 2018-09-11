package com.yyhz.utils.stream.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yyhz.utils.stream.config.Configurations;
import com.yyhz.utils.stream.util.IoUtil;


/**
 * File reserved servlet, mainly reading the request parameter and its file
 * part, stored it.
 */
public class StreamServlet extends HttpServlet {
	private static final long serialVersionUID = -8619685235661387895L;
	/** when the has increased to 10kb, then flush it to the hard-disk. */
	static final int BUFFER_LENGTH = 10240;
	static final String START_FIELD = "start";
	static final String URL_PATH = "urlPath";
	public static final String CONTENT_RANGE_HEADER = "content-range";
	

	@Override
	public void init() throws ServletException {
	}
	
	/**
	 * Lookup where's the position of this file?
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doOptions(req, resp);

		final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
		final String size = req.getParameter(TokenServlet.FILE_SIZE_FIELD);
		final String fileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f = IoUtil.getTokenedFile(token);
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start)
				f.renameTo(IoUtil.getFile(fileName));
		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			try {
				if (success)
					json.put(START_FIELD, start);
				json.put(TokenServlet.SUCCESS, success);
				json.put(TokenServlet.MESSAGE, message);
			} catch (JSONException e) {}
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doOptions(req, resp);
		
		final String token = req.getParameter(TokenServlet.TOKEN_FIELD);
		final String sourceFileName = req.getParameter(TokenServlet.FILE_NAME_FIELD);
		sourceFileName.substring(sourceFileName.lastIndexOf('.'),sourceFileName.length());
		final String fileExtName = sourceFileName.substring(sourceFileName.lastIndexOf('.'),sourceFileName.length());
		final String fileName = IoUtil.getBusinessFileName() + fileExtName;
		final String model = req.getParameter(TokenServlet.MODEL_NAME);
		
		Range range = IoUtil.parseRange(req);
		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		File f = IoUtil.getTokenedFile(model,token);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}
			
			out = new FileOutputStream(f, true);
			content = req.getInputStream();
			int read = 0;
			byte[] bytes = new byte[BUFFER_LENGTH];
			while ((read = content.read(bytes)) != -1)
				out.write(bytes, 0, read);
			start = f.length();
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);
			Path path = null;
			/** rename the file */
			boolean isUploadFinish = false;
			if (range.getSize() == start) {
				/** fix the `renameTo` bug */
//				File dst = IoUtil.getFile(fileName);
//				dst.delete();
				// TODO: f.renameTo(dst); 重命名在Windows平台下可能会失败，stackoverflow建议使用下面这句
				
				try {
					// 先删除
					IoUtil.getFile(fileName).delete();
					
					path = Files.move(f.toPath(), f.toPath().resolveSibling(fileName));
					
					System.out.println("TK: `" + token + "`, NE: `" + fileName + "`");
					
					/** if `STREAM_DELETE_FINISH`, then delete it. */
					if (Configurations.isDeleteFinished()) {
						IoUtil.getFile(fileName).delete();
					}
				} catch (IOException e) {
					success = false;
					message = "Rename file error: " + e.getMessage();
				}
				isUploadFinish = true;				
			}
			try {
				if (success){
					json.put(START_FIELD, start);
					//String path = f.getPath();
					if(isUploadFinish){
						String urlPath = path.toString().substring(Configurations.getFileRepository().length() + 1, path.toString().length());
						urlPath = urlPath.replaceAll("\\\\", "/");	
						json.put(URL_PATH, urlPath);
					}					
				}
				json.put(TokenServlet.SUCCESS, success);
				json.put(TokenServlet.MESSAGE, message);
			} catch (JSONException e) {}
			
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
