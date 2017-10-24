package com.chinawiserv.utils.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by sungang on 2017/8/16.
 */
public class Sequence {

    private static Integer DEFAULT_LENGTH = 10;

    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyMMddHHmmss");

    private static final String NUMBER_CHAR = "1234567890";

    private final static int PIX_LEN = NUMBER_CHAR.length();

    private static volatile int pixOne = 0;
    private static volatile int pixTwo = 0;
    private static volatile int pixThree = 0;


    private Sequence(){}

    /**
     * 生成一个uuid字符串
     * <p>Function: uuid</p>
     * <p>Description: </p>
     * @author zhaoxy@thankjava.com
     * @date 2016年1月5日 下午2:11:58
     * @version 1.0
     * @return 返回String数组， 长度为2 下标为:0是去除中划线后的uuidString 下标:1是原始的uuidString
     */
    public static String[] uuid(){
        String uuidStr = UUID.randomUUID().toString();
        String[] uuidStrs = new String[2];
        uuidStrs[0] = uuidStr.replace("-", "");
        uuidStrs[1] = uuidStr;
        return uuidStrs;
    }

    /**
     * 按照时间戳&随机3位数生成唯一seq
     * <p>Function: generateUnique</p>
     * <p>Description: 最大可并发约1000  随机器性能增加而并发数减少</p>
     * @author zhaoxy@thankjava.com
     * @date 2016年1月5日 下午2:53:18
     * @version 1.0
     * @return
     */
    public synchronized static Long generateUnique() {
        String dateStr = SIMPLE_DATE_FORMAT.format(new Date());
        pixThree++;
        if (pixThree == PIX_LEN) {
            pixThree = 0;
            pixTwo++;
            if (pixTwo == PIX_LEN) {
                pixTwo = 0;
                pixOne++;
                if (pixOne == PIX_LEN) {
                    pixOne = 0;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append(dateStr);
        sb.append(NUMBER_CHAR.charAt(pixOne));
        sb.append(NUMBER_CHAR.charAt(pixTwo));
        sb.append(NUMBER_CHAR.charAt(pixThree));
        return Long.valueOf(sb.toString());
    }

    /**
     * 随机生成指定位数字符
     * <p>Function: generateNum</p>
     * <p>Description: </p>
     * @author zhaoxy@thankjava.com
     * @date 2016年1月5日 下午3:05:31
     * @version 1.0
     * @param length
     * @return
     */
    public static String generateChar(int length) {
        length = length <= 0 ? DEFAULT_LENGTH : length;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();// 随机用以下三个随机生成器
        Random randData = new Random();
        int data = 0;
        for (int i = 0; i < length; i++) {
            int index = rand.nextInt(3);
            // 目的是随机选择生成数字，大小写字母
            switch (index) {
                case 0:
                    data = randData.nextInt(10);// 仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data = randData.nextInt(26) + 65;// 保证只会产生65~90之间的整数
                    sb.append((char) data);
                    break;
                case 2:
                    data = randData.nextInt(26) + 97;// 保证只会产生97~122之间的整数
                    sb.append((char) data);
                    break;
            }
        }
        return sb.toString();
    }

    /**
     * 生成指定长度的纯数字
     * <p>Function: generateNum</p>
     * <p>Description: </p>
     * @author zhaoxy@thankjava.com
     * @date 2016年1月5日 下午3:13:50
     * @version 1.0
     * @param length
     * @return
     */
    public static String generateNum(int length) {
        length = length <= 0 ? DEFAULT_LENGTH : length;
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
        String data = sb.toString();
        return data;
    }
}
