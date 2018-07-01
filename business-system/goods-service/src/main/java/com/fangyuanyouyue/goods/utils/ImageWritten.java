package com.fangyuanyouyue.goods.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片添加水印
 * 
 * @author gao
 *
 */

public class ImageWritten {

	protected Logger log = Logger.getLogger(this.getClass());

	// 为了例子简单，暂时用固定的文件名。
	private static final String FILEINPUT = "e:/1230.JPG";
	private static final String FILEMARK = "e:/333.png";
	private static final String FILEDEST1 = "e:/999.jpeg";
	private static final String FILEDEST2 = "e:/图像水印效果.jpg";

	/**
	 * 给图片添加文字水印
	 * 
	 * @param filePath
	 *            需要添加水印的图片的路径
	 * @param markContent
	 *            水印的文字
	 * @param markContentColor
	 *            水印文字的颜色
	 * @param qualNum
	 *            图片质量
	 * @return 布尔类型
	 * @throws Exception
	 */
	public void createStringMark(String filePath, String markContent,
                                 Color markContentColor, float qualNum, String FILEDEST)
			throws Exception {
		log.info("图片地址为:" + filePath);

		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		// int width=theImg.getWidth(null)== -1 ? 640 : theImg.getWidth(null);
		// int height= theImg.getHeight(null)== -1 ? 470 :
		// theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(theImg.getWidth(null),
				theImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(markContentColor);
		g.setBackground(Color.white);
		g.drawImage(theImg, 0, 0, null);
		float alpha = 0.5f; // 透明度
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
				alpha));
		BufferedImage bufferedImage = ImageIO.read(new File(filePath));
		int x = bufferedImage.getWidth();
		int y = bufferedImage.getHeight();
		System.out.println(x);
//		int fontSize = 60;
        //根据图片大小设置字体大小
        int fontLength = markContent.length();
//        int fontSize = x*y/20000;
        int fontSize = x/2/fontLength;
        //如果昵称过长，设置字体大小为图片宽度除以水印长度
        /*if(fontSize*fontLength>x){
            fontSize=x/(fontLength+1)/2;
        }*/
		g.setFont(new Font(null, Font.BOLD, fontSize)); // 字体、字型、字号
		g.drawString(markContent, x - fontLength * (fontSize + 2), y - fontSize); // 画文字
																					// 位置
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(FILEDEST); // 先用一个特定的输出文件名
			// JPEGImageEncoder encoder =JPEGCodec.createJPEGEncoder(out);
			// JPEGEncodeParam param =
			// encoder.getDefaultJPEGEncodeParam(bimage);
			// param.setQuality(qualNum, true);
			// encoder.encode(bimage, param);

			ImageIO.write(bimage, "jpeg", out); // 服务器不支持JPEG包下的类 用ImageIO替换

			out.close();
		} catch (Exception e) {
			throw new Exception();
		}

	} // end of createStringMark

	/**
	 * 给图片添加图像水印
	 * 
	 * @param filePath
	 *            需要添加水印的图片的路径
	 * @param qualNum
	 *            图片质量
	 * @return 布尔类型
	 */
	public boolean createWaterMarkDemo(String filePath, float qualNum) {
		// 要处理的原始图片
		ImageIcon icoInput = new ImageIcon(filePath);
		Image imgInput = icoInput.getImage();
		int width = imgInput.getWidth(null);
		int height = imgInput.getHeight(null);
		BufferedImage buffInput = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 要添加上来的水印
		ImageIcon icoADD = new ImageIcon(FILEMARK);
		Image imgADD = icoADD.getImage();
		int w = imgADD.getWidth(null);
		int h = imgADD.getHeight(null);
		// 绘图
		Graphics2D g = buffInput.createGraphics();
		g.drawImage(imgInput, 250, 420, null);
		// 下面代码的前面五个参数：图片，x坐标，y坐标,图片宽度,图片高度
		g.drawImage(imgADD, 10, 10, w, h, null); // 靠左上绘制，x和y都是10
		g.dispose();
		try {
			FileOutputStream out = new FileOutputStream(FILEDEST2);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder
					.getDefaultJPEGEncodeParam(buffInput);
			param.setQuality(qualNum, true);
			encoder.encode(buffInput, param);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String args[]) throws Exception {
		ImageWritten wmObj = new ImageWritten();
		// 文字水印
		wmObj.createStringMark(FILEINPUT, "小方圆@我的名字就是小吴哈哈啊哈", Color.white, 3,
				"e:/测试1.jpeg");
		// wmObj.createStringMark(FILEINPUT,"小方圆@xxx",Color.white,3,"e:/测试2.jpeg");
		System.out.println("生成文字水印成功。请查看" + "e:/9999.jpeg");

		// 图像水印
		// if (wmObj.createWaterMarkDemo(FILEINPUT ,3)==true)
		// System.out.println("生成图像水印成功。请查看当前目录下的"+FILEDEST2);
		// else
		// System.out.println("生成图像水印失败！");
		// 结束程序
		System.exit(0);
	}
}