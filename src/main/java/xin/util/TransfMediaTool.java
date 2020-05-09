package xin.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by snowman on 2017/10/30.
 */
//视频转码工具
public class TransfMediaTool {
    /**
     * 视频转码mp4
     *
     * @param ffmpegPath   转码工具的存放路径
     * @param upFilePath   用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath 格式转换后的的文件保存路径
     * @param coverPath    封面照片保存路径
     * @return
     * @throws Exception
     */
    public void processMP4(String ffmpegPath, String upFilePath, String codcFilePath, String coverPath) {
// 创建一个List集合来保存转换视频文件为mp4格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath); // 添加转换工具路径
//        convert.add("-y");//覆盖同名文件
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath); // 添加要转换格式的视频文件的路径
//        convert.add("-ab");//音频数据流量，一般选择32、64、96、128
//        convert.add("64");
//        convert.add("2");//设置声道数
        convert.add("-ar");//声音的采样频率
        convert.add("22050");
        convert.add("-q:a");
        convert.add("8");
//        convert.add("-r");//帧数
//        convert.add("15");
//
//        convert.add("-b");//视频数据流量，用-b xxxx的指令则使用固定码率，数字随便改，1500以上没效果,类似-qscale
//        convert.add("1149");

        convert.add("-qscale");//<数值> 以<数值>质量为基础的VBR，取值0.01-255，约小质量越好
        convert.add("8");

//        convert.add("-r");
//        convert.add("10");
//        convert.add("-b:a");
//        convert.add("32k");
        convert.add(codcFilePath);


        convert.add("-f");//强迫采用格式
        convert.add("image2");
        convert.add("-t");//设置纪录时间 hh:mm:ss[.xxx]格式的记录时间也支持
        convert.add("0.001");
        convert.add("-s");//设置帧大小
        convert.add("450x450");
        convert.add(coverPath);

/*
* convert.add("-qscale"); // 指定转换的质量 convert.add("6");
* convert.add("-ab"); // 设置音频码率 convert.add("64"); convert.add("-ac");
* // 设置声道数 convert.add("2"); convert.add("-ar"); // 设置声音的采样频率
* convert.add("22050"); convert.add("-r"); // 设置帧频 convert.add("24");
* convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
*/



        try {
            Process videoProcess = new ProcessBuilder(convert).redirectErrorStream(true).start();
            new PrintStream(videoProcess.getInputStream()).start();
            videoProcess.waitFor();


        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void processMoveUp(String qtfaststartPath, String codcFilePath, String moveUpPath) {
        // 创建一个List集合来保存转换视频文件为mp4格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(qtfaststartPath); // 添加转换工具路径
        convert.add(codcFilePath);
        convert.add(moveUpPath);

        try {
            Process videoProcess = new ProcessBuilder(convert).redirectErrorStream(true).start();
            new PrintStream(videoProcess.getInputStream()).start();
            videoProcess.waitFor();


        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}


class PrintStream extends Thread {
    java.io.InputStream __is = null;


    public PrintStream(java.io.InputStream is) {
        __is = is;
    }


    public void run() {
        try {
            while (this != null) {
                int _ch = __is.read();
                if (_ch != -1)
                    System.out.print((char) _ch);
                else
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}