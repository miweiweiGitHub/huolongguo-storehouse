package org.meteorite.com.base;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.meteorite.com.dto.BdUserPart;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;

/**
 * @author EX_052100260
 * @title: BigDataUtil
 * @projectName huolongguo-storehouse
 * @description: TODO
 * @date 2020-9-24 9:13
 */
@Slf4j
public class BigDataUtil {

    private static final String pkey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJuwCG2rzak2XPsbXou4eLIuZsxWfX4CrMcGpXNJn1wVlKs2pl1P5kUw5Ir8uhcg9vJrBG0eQCRBbW3xsM+56jL3lbXEXvHZWKNSnhikvlt02spJxaP4Zy+GJXUmEQtDLu0Bvlbbmw1qPoN2e5DkjckV7syVN6qnYkqWdWgEI6VBAgMBAAECgYAPhGgSpkEFUInL7VprCqPc/or4atZvLM0TuTHcX8YmY3BB8Fx8iG4nD0x4HeBeVcbHOqtiRNWXx32kq6Y3zgvth4tntLVpMcGYX+6lbNRLDgSXs9Gdp89yYJBCgZl45emjLNejpfaRPkiR7+aDOPlITF/aXLeBTGJRvHun+KABEQJBAP7HU9QEKBOOdIDiCncWgWIqdvmrqoyObsLFr15gv/ytQp+JNAOHRayJtmP3kHg/XuLcbVp0Re5t436HfJcAAIcCQQCcbxkJtQLeSvYqqOBsS4VUEwViPBd27xW69k4YHF18BXa1z4g1lIRMSvmTXDegBP+IgPEoMQ0YIIce7ebee4X3AkBhmtVHjQwZaeLCGVavBsUsaV5JCfX9gPd30Kn9ew0x7OJwIez2SRVtIxjntUj4eDaOrKmMFK1RyXF04MzfQFXzAkBRJ3WWypgNWFgys1+R7u/hOOjvGHuX0Nq2HndPHNAGuhLmqR5hpYWoyrCFGS8mTdF/MF1rW18OqDlQ+1xtCSnrAkEAvxwkCUrThXVIXGrNSe7774K7CCd7R6bKbHDF6YpnGjLnYG/WEGOz9nSkp5xnwUAVCpF/tv9yYWjJTZ74rdH1QQ==";

    public static void main(String[] args) {


//        String phone1 = "GSuN539RwYdoUKMsRavXZDoRPdKTuNQBmsZCr3oN+o/yHIFTcyIknVNQXRTRAAIragtc5vKQB5LEU3yDzbWL0sRFsDGg9NwZVWzpLFo/mbALFgnWZHba7iDNCyRiajqtfc25lTuyMQsv1RAhXUULCxhVf7LJoHq6Gp1vfvA5O8c=";
//        String phone2 = "K/L6XGCyckWerI3WL0rWaBRn/T0gQRtU7heWOH2IzgXpR9iu8XDp3Y6XkJc25PAfJnkyWne+Bmxk9acZVw/sEIKoPYqT+weZq1cpX4V/fWWJ/OSm6q0YpVF1prHN2744re1UwkCpA24LTNVQ8YlQ4T7erccZH68A02mU2hnNmAE=";
//        String name = "JA1Rs6KoLjLyWdN9MkfLpTLmuvLie8fwOY3SjRvJspK3hdfFE+QBSYc9fJnqM7w1Nxj8PBD6FUtnLuFJAjUGToVEbRZbbyKd1EUThf1Pza/WYhK1bEZMI40BWsfTOBCvQ6AJ1Co4jyMibbhWjTM672LokN7x8M1REKNR0t03OMs=";
//        String decryptPhoneNum1 = getDecryptPhoneNum(phone1);
//        String decryptPhoneNum2 = getDecryptPhoneNum(phone2);
//        String decryptUserName = getDecryptUserName(name);
//        log.info("解析参数 phone1 ：{}", decryptPhoneNum1);
//        log.info("解析参数 phone2 ：{}", decryptPhoneNum2);
//        log.info("解析参数 name ：{}", decryptUserName);
        testLocalData();

    }

    private static void testLocalData() {
        Class beanClass = BdUserPart.class;

        InputStreamReader reader = null;
        try {
//            reader = new InputStreamReader(new FileInputStream("C:\\Users\\EX_052100260\\Downloads\\msgtask652_0924082734714.csv"), CharEncoding.UTF_8);


            InputStream skipBomInputStream = getSkipBomInputStream(new FileInputStream("C:\\Users\\EX_052100260\\Downloads\\msgtask1015_1009141034490.csv"));
            reader = new InputStreamReader(skipBomInputStream, CharEncoding.UTF_8);


            List<BdUserPart> listUser = parseCsvToBean(reader, beanClass, ',');
            if (CollectionUtils.isEmpty(listUser)) {
                log.info("文件{}为空，忽略!");
                return;
            }
            for (BdUserPart item : listUser) {
//                log.info(" old item :{}", item);
                if (getDecryptPhoneNum(item.getPhoneNumber()).equals("13510830235")) {
                    log.info("item :{}", item);
                    item.setPhoneNumber(getDecryptPhoneNum(item.getPhoneNumber()));
                    item.setUserName(getDecryptUserName(item.getUserName()));
                }

            }

            listUser.stream().forEach(item -> {
                //13510830235
                //17374415427  18212779545
                if (item.getPhoneNumber().equals("13510830235")) {
                    log.info("item :{}",item);
                }

            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析文件并返回beanList
     *
     * @param beanClass 对应的beanClass
     * @param separator 文件中使用的分割符
     * @return 返回beanList
     */
    private static List<T> parseCsvToBean(InputStreamReader reader, Class<T> beanClass, char separator) {
        List<T> data = new CsvToBeanBuilder(reader)
                .withType(beanClass)
                .withSeparator(separator)
                .build()
                .parse();
        return data;
    }


    private static String getDecryptPhoneNum(String phoneNum) {
        if (StringUtils.isBlank(phoneNum)) {
            return phoneNum;
        }

        return RSAUtil.decrypt(pkey, phoneNum);
    }

    private static String getDecryptUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            return userName;
        }

        return RSAUtil.decrypt(pkey, userName);
    }

    /**
     * 读取流中前面的字符，看是否有bom，如果有bom，将bom头先读掉丢弃
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static InputStream getSkipBomInputStream(InputStream in) throws IOException {
        PushbackInputStream testin = new PushbackInputStream(in);
        int ch = testin.read();
        if (ch != 0xEF) {
            testin.unread(ch);
        } else if ((ch = testin.read()) != 0xBB) {
            testin.unread(ch);
            testin.unread(0xef);
        } else if ((ch = testin.read()) != 0xBF) {
            throw new IOException("错误的UTF-8格式文件");
        } else {
        }
        return testin;
    }

}
