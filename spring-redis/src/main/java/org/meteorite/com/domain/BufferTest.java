package org.meteorite.com.domain;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Scanner;

/**
 * @author EX_052100260
 * @title: BufferTest
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-11-24 16:39
 */
public class BufferTest {

    public static void main(String[] args) throws Exception {

        transfer();
    }

    public static void reader() throws Exception{
        File file = new File("E://消息组件（hdb-msg-push）-V1.3.1.0-20201105-发版说明书.txt");
        long len = file.length();
        byte[] ds = new byte[(int) len];
        MappedByteBuffer mappedByteBuffer = new FileInputStream(file).getChannel()
                .map(FileChannel.MapMode.READ_ONLY, 0, len);
        for (int offset = 0; offset < len; offset++) {
            byte b = mappedByteBuffer.get();
            ds[offset] = b;
        }
        Scanner scan = new Scanner(new ByteArrayInputStream(ds)).useDelimiter(" ");
        while (scan.hasNext()) {
            System.out.print(scan.next() + " ");
        }
    }



    public static void transfer() throws Exception {
        String files[]=new String[1];
        files[0]="E://消息组件（hdb-msg-push）-V1.3.1.0-20201105-发版说明书.txt";
        catFiles(Channels.newChannel(System.out), files);
    }

    private static void catFiles(WritableByteChannel target, String[] files)
            throws Exception {
        for (int i = 0; i < files.length; i++) {
            FileInputStream fis = new FileInputStream(files[i]);
            FileChannel channel = fis.getChannel();
            channel.transferTo(0, channel.size(), target);
            channel.close();
            fis.close();
        }
    }

}
