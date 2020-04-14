package com.baizhi.gxy;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
public class SendSms {

    public static void sendMsg() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FqcFRX15KLk17Sux2gP", "TzsCdRDD9L9Vmtm4PN489becPqbI1f");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15566262371");
        request.putQueryParameter("SignName", "小万儿");
        request.putQueryParameter("TemplateCode", "SMS_186948718");
        request.putQueryParameter("TemplateParam", "{\"code\":\"8888\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendMsg();
    }
}
