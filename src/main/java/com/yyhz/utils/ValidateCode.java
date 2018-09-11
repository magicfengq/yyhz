package com.yyhz.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ValidateCode {
	/** 验证码图片的宽度 */
	private int width;

	/** 验证码图片的高度 */
	private int height;	

	/** 验证码 */
	private String codeStr;

	/** 验证图片 */
	private BufferedImage buffImg;

	private char[] codeSequence = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private Random random = new Random();
	public ValidateCode() {
		this(120, 30, 4);
	}
	
	public ValidateCode(int width, int height, int codeCount) {
		this.width = width;
		this.height = height;
		initCode();
	}
	public String getRandomString() {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < 4; i++) {
			sb.append(codeSequence[(random.nextInt(codeSequence.length))]);
		}
		return sb.toString();
	}
	/**
	 * 生成验证图片和验证码
	 */
	private void initCode() {

		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获得图像对象的画笔
		Graphics2D g = (Graphics2D) (buffImg.getGraphics());

		//将图像填充成白色背景色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		//绘制图像的黑色边框
		g.setColor(Color.BLACK);

		g.drawRect(0, 0, width - 1, height - 1);

		//随机产生干扰点,并用不同的颜色表示，使图象中的认证码不易被其它程序探测到
		

		String strValidate = this.getRandomString(); //生成验证字符串
		g.setFont(new Font("Tahoma", Font.BOLD, 23)); //设置字体
		//画认证码,每个认证码在不同的水平位置
		String[] strAry = new String[4];
		//将验证字符串的文字逐个画到图像中
		for (int i = 0; i < strAry.length; i++) {
			strAry[i] = strValidate.substring(i, i + 1);
			int w = 22;
			//随机生成验证码字符的水平偏移量
			
			//随机生成字体颜色
			g.setColor(this.getRandomColor(225));
			g.drawString(strAry[i], 30 * i + 10, w); //在图像中画文字

		}

		//画干扰线
		for (int i = 0; i < 5; i++) {
			//随机生成干扰线的起始和终止坐标
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xx = random.nextInt(width);
			int yy = random.nextInt(height);
			g.setColor(getRandomColor(180));
			g.drawLine(x, y, xx, yy); //画干扰线
		}
		codeStr = strValidate;

	}
	private Color getRandomColor(int bound) {
		int r = random.nextInt(bound);
		int g = random.nextInt(bound);
		int b = random.nextInt(bound);
		return new Color(r, g, b);
	}
	/**
	 * 获得验证码图片
	 * 
	 * @return 验证码图片
	 */
	public BufferedImage getImage() {
		return buffImg;
	}

	/**
	 * 获得验证码
	 * 
	 * @return 验证码
	 */
	public String getCode() {
		return codeStr;
	}

}
