import io.swagger.models.auth.In;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chendongzhao
 * @version 1.0
 * @date 2020/7/7 13:22
 */
public class SublistTest {

    public static void main(String [] args){

        ReentrantLock lock = new ReentrantLock();
        lock.lock();

        Map<String, List<Integer>> myMap = new HashMap<>();
        List<Integer> myList = new ArrayList<>();
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);
        myList.add(6);

        myMap.put("my",myList);

        for(int i = 0; i < 6; i++) {

            List<Integer> tempList = myMap.get("my").subList(0,1);
            System.out.println(tempList.get(0));
        }

        try {
            // 读取图片
            BufferedImage bi1 = ImageIO.read(new File("C:\\Users\\24573\\Desktop\\jian.jpg"));
            BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, 20, 20);
            Graphics2D g2 = bi2.createGraphics();
            g2.setBackground(Color.WHITE);
            g2.fill(new Rectangle(20, 20));
            g2.setClip(shape);
            //设置抗锯齿
            g2.drawImage(bi1, 0, 0, null);
            g2.dispose();
            ImageIO.write(bi2, "jpg", new File("D:/3.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
           RoundHeadImgUtils.ImageResize("D:\\out00010.png","D:/36.jpg",32,32);
           RoundHeadImgUtils.makeCircularImg("D:\\36.jpg","D:/35.jpg",32,32);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
