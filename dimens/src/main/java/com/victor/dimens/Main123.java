package com.victor.dimens;

import com.victor.dimens.bean.AbstractSize;
import com.victor.dimens.bean.Hdpi;
import com.victor.dimens.bean.Mdpi;
import com.victor.dimens.bean.Size1440x720;
import com.victor.dimens.bean.Size2880x1440;
import com.victor.dimens.bean.TVMdpi;
import com.victor.dimens.bean.TVhdpi;
import com.victor.dimens.bean.XXXdpi;
import com.victor.dimens.bean.XXdpi;
import com.victor.dimens.bean.Xdpi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Main123 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 设计标注基础尺寸
        int baseWidth = 1080;
        int baseHeight = 1920;

        List<AbstractSize> list = new ArrayList<>();
        list.add(new Mdpi());
        list.add(new Hdpi());
        list.add(new Xdpi());
        list.add(new XXdpi());
        list.add(new XXXdpi());
//        list.add(new Size1440x720());
//        list.add(new Size2880x1440());
//        list.add(new TVhdpi());
//        list.add(new TVMdpi());


        for (int index = 0; index < list.size(); index++) {
            AbstractSize dstSize = list.get(index);
            // 创建文件夹
            File rootFile = new File(dstSize.getFileRoot());
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }
            File txt = new File(rootFile, dstSize.getFileName());
            if (!txt.exists()) {
                try {
                    txt.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            StringBuffer sBuffer = new StringBuffer();

            // head
            sBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
            sBuffer.append("<resources>");
            sBuffer.append("\n\n");

            for (int i = 1; i <= baseHeight; i++) {
                double realHeightPx = i * dstSize.getHeight() * 1.0f / baseHeight;
                double realDP = realHeightPx / dstSize.getDensity();
                // <dimen name="y1">1dip</dimen>
                BigDecimal bg = new BigDecimal(realDP);
                double heightValue = bg.setScale(2, RoundingMode.UP).doubleValue();
                sBuffer.append("<dimen name=\"y" + i + "\">" + heightValue + "dp</dimen>\n");
            }

            sBuffer.append("\n\n");

            for (int i = 1; i <= baseWidth; i++) {
                double realWidthPx = i * dstSize.getWidth() / baseWidth;
                double realDP = realWidthPx / dstSize.getDensity();
                BigDecimal bg = new BigDecimal(realDP);
                double widthValue = bg.setScale(2, RoundingMode.UP).doubleValue();
                // <dimen name="x1">1dip</dimen>
                sBuffer.append("<dimen name=\"x" + i + "\">" + widthValue + "dp</dimen>\n");
            }

            // footer
            sBuffer.append("\n\n");
            sBuffer.append("</resources>");

            byte bytes[] = new byte[2000];
            bytes = sBuffer.toString().getBytes(); // 新加的
            int b = sBuffer.length(); // 改
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(txt);
                fos.write(bytes, 0, b);
                fos.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
            }
        }
    }

}
