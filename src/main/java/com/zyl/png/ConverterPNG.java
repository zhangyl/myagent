package com.zyl.png;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author hjf
 * @version 1.0
 * @description
 * @date 2022-05-25 10:45
 */
public class ConverterPNG {

	/**
	 *
	 * @param fileName 要验证的文件
	 * @param inDir    输入目录的名称
	 * @param outDir   输出目录的名称
	 * @return 是否执行失败
	 * @throws IOException
	 */
	public static boolean doTestFile(String fileName, String inDir, String outDir) throws IOException {

		PDDocument document = null;

		try {

			// 加载PDF 文件
			//3.x版本
			document = org.apache.pdfbox.Loader.loadPDF(new File(inDir + '/' + fileName), (String) null);
			//2.x版本
//			document = PDDocument.load(new File(inDir + '/' + fileName), (String) null);
			// 生成前缀
			String outputPrefix = outDir + '/' + fileName + "-";

			// 获取 PDF 页数
			int numPages = document.getNumberOfPages();

			// 当 PDF 页数 小于 1
			if (numPages < 1) {
				throw new IOException("PDF 页数小于 1");
			}

			PDFRenderer renderer = new PDFRenderer(document);
			// 循环处理每页 PDF
			for (int i = 0; i < numPages; i++) {
				// 创建 PNG 名称
				String pngFileName = outputPrefix + (i + 1) + ".png";
				// 将对应 PDF 页面转成 Image 对象
				BufferedImage image = renderer.renderImageWithDPI(i, 50); // 图像 DPI
				// 输出图片
				ImageIO.write(image, "PNG", new File(pngFileName));
			}

		} catch (IOException e) {
			throw e;
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return false;
	}

	public static void main(String[] args) {
		try {
			doTestFile("1.pdf", "/Users/zhangyl/Desktop", "/Users/zhangyl/Desktop/fp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}