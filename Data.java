package aliyun;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class Data {
	// Endpoint以杭州为例，其它Region请按实际情况填写。
	public static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
	// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
	public static String accessKeyId = "STS.NUX37hyXq6yzwZg4P5iqh7cyg";
	public static String accessKeySecret = "4U9Y47HTU5CTgH7GgB1ZWSnDM9LiHmodQCa5icNT2Mky";
	public static String bucketName = "wecloud-res";
	public static String securityToken = "CAIShAN1q6Ft5B2yfSjIr5bteI3clIdQgbuRdXzW0FBgZf5E2KbShTz2IH1LfHZgAuEesPw1m2xT6/cTlr0qEsIVGxybMpcutsoGqFP5JtKZ4pQJPQLwNdv3d1KIAjvXgeUFCoeQFaEME5XAQlTAkTAJK9KYERypQ12iN7CQlJdjda55dwKkbD1AdtU0Qwx5s501OGf2P/SgOQKI523LFxhQpxZbg2Fy4rjdusqH8UjygVn31uIyrYb8KYTGCs53J8VbUsyp5OVsarGjoClL8Epj/qBskIlZ/SvGptiHH2RJ5xKPP4iz9cZ0fit4fbQdEaxJp+Tn774a3NbejIPq0R1AEPhIWiDEPuCazdDDBfmiO6ReF9PyMG+f1cuUZNun8VFmQwpCbV4aJYp+dSMuUUJ9E2GCEML9pgCWODXEYrOezaQ73aByy1jV5teQLzCNOe7Ejn5HasRtNx1wbUFKhjGwI7V1eghNYk5hAK2OUYJwYRZRpKXlpwTOUTd6yXVarykzhQceaE91GoABAc/Lm6v11LP9Yf5/bd6i+nf7HizcW3ZHjfN9+x5/SNVkDw5CXhJdtxF7fUEI8svRx/RmUxiNB/BQ3zmASg9Nq3Ad65fvEYUXOlvG66ilWlcPc56dNywcmfTSymCtbkn20vuGi8bG2zYXk8RfYnZpdLWgB1ImTQP0OhgLkyW3+uQ\\u003d";
	// 图片上传路径
	public static String objectName = "counselor/1018615876819107/content/ccc3.png";
	// 图片本地路径
	public static String localFile = "D:\\3.png";

	// 获取权限码路径
	public static String getAccess = "https://ccut.campusphere.net/wec-counselor-sign-apps/stu/sign/getStsAccess";

	/**
	 * {"appVersion":"8.1.11","systemName":"android","model":"MI
	 * 9","lon":0,"systemVersion":"10","deviceId":"设备号","userId":学号,"lat":0}的加密值
	 */
	public static String cpdailyExtension = "kQDkr/pNQMdvv2ITpF4MDg8CNYEeTt0h/wnPA+r6EtMYsWjvHihlxWV/gdfSzUbosGnrEHnJQMiBRA/RO/v2pzirtMIWOBr9Ph5QXmLrt/ngN2gij4JaPXSQpGvPDsNMqxFHVa8dAAH9b5vav7LSy/AusZqXSa9YrtG1G0cLYuJyoCq8OfZyyIfHEChUnIrSCvLdw0wnuBtmLPss8I5d4t/3MF3Y1GjNvej+GW6prUV1Jbdtr8Z4qw==";
	/**
	 * cookie
	 */
	public static String cookie = "acw_tc=2f624a3515997253376138675e2b11ed991fe5bc28b3be4490bb23b5fabd7b; MOD_AUTH_CAS=ST-iap:1018615876819107:ST:8fd3e626-266d-4ced-84dc-d4e4a2d38d65:20200910155656";

	/**
	 * Cpdaily-Extension需要根据实际情况
	 * 
	 * @return
	 */
	public static Map<String, String> getSubHeaders() {
		Map<String, String> map = getHeaders();
		map.put("CpdailyStandAlone", "0");
		map.put("extension", "1");
		map.put("Cpdaily-Extension", cpdailyExtension);
		return map;
	}

	/**
	 * Cookie需要根据实际情况
	 * 
	 * @return
	 */
	public static Map<String, String> getHeaders() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("tenantId", "ccut");
		map.put("User-Agent",
				"Mozilla/5.0 (Linux; Android 10; MI 9 Build/QKQ1.190825.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 okhttp/3.8.1");
		map.put("Content-Type", "application/json;charset=utf-8");
		map.put("Host", "ccut.campusphere.net");
		map.put("Connection", "Keep-Alive");
		map.put("Accept-Encoding", "gzip");
		map.put("Cookie", cookie);
		return map;
	}

	public static void main(String[] args) {
		String sendPost = HttpUtil.sendPost(Data.getAccess, "{}", Data.getHeaders());
		JSONObject jsonObject = JSONObject.fromObject(sendPost).getJSONObject("datas");
		Data.accessKeyId = jsonObject.getString("accessKeyId");
		Data.accessKeySecret = jsonObject.getString("accessKeySecret");
		Data.securityToken = jsonObject.getString("securityToken");
		Data.endpoint = jsonObject.getString("endPoint");

		Data.bucketName = jsonObject.getString("bucket");
//		Data.objectName=jsonObject.getString("fileName");
//		System.out.println(Data.accessKeyId);
//		System.out.println(Data.accessKeySecret);
//		System.out.println(Data.securityToken);
//		System.out.println(Data.endpoint);
		PutObjectProgressListener2.upload();

	}
}
