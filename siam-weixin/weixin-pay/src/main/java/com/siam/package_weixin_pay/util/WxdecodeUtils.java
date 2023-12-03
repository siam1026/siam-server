package com.siam.package_weixin_pay.util;

import com.siam.package_weixin_basic.util.WxMD5Util;
import jodd.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

/**
 * @Description TODO
 * @Author WP
 * @Date 2019/11/18 10:22
 * @Param
 * @Return
 **/
@Slf4j
@Component
public class WxdecodeUtils {
	private static final String KEY_ALGORITHM = "AES";
	private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
	private static Key key;
	private static Cipher cipher;

	public static String decryptData(String encryptDataB64, String sessionKeyB64, String ivB64) throws UnsupportedEncodingException {
		byte[] data =  Base64.decode(encryptDataB64.getBytes("UTF-8"));
		byte[] key = Base64.decode(sessionKeyB64.getBytes("UTF-8"));
		byte[] ib = Base64.decode(ivB64.getBytes("UTF-8"));
		return new String(decryptOfDiyIV(data, key, ib));
	}

	/**
	 * 解密方法
	 *
	 * @param encryptedData 要解密的字符串
	 * @param keyBytes 解密密钥
	 * @param ivs 自定义对称解密算法初始向量 iv
	 * @return 解密后的字节数组
	 */
	private static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
		byte[] encryptedText = null;
		init(keyBytes);
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
			encryptedText = cipher.doFinal(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	private static void init(byte[] keyBytes) {
		// 如果密钥不足16位，那么就补足.  这个if中的内容很重要
		int base = 16;
		if (keyBytes.length % base != 0) {
			int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
			byte[] temp = new byte[groups * base];
			Arrays.fill(temp, (byte) 0);
			System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
			keyBytes = temp;
		}
		// 初始化
		Security.addProvider(new BouncyCastleProvider());
		// 转化成JAVA的密钥格式
		key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
		try {
			// 初始化cipher
			cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start 微信退款报文解密 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
	private String algorithm = "AES";

	private String algorithmModePadding = "AES/ECB/PKCS7Padding";

	private String key_second;

	SecretKeySpec secretKey;

	private boolean initialized = false;

	/**
	 * AES解密
	 * @param base64Data
	 * @return
	 * @throws Exception
	 */
	public String decryptData(String base64Data) throws Exception {
		log.debug("\n进入decryptData方法");

		String keyParam = "2er2e528d876e0ef66ca5344debb3eac";
		this.key_second = keyParam;
		// 转化成JAVA的密钥格式
		secretKey = new SecretKeySpec(WxMD5Util.MD5Encode(keyParam, "UTF-8").toLowerCase().getBytes(), algorithm);

		initialize();

		// 获取解码器实例，"BC"指定Java使用BouncyCastle库里的加/解密算法。
		Cipher cipher = Cipher.getInstance(algorithmModePadding, "BC");
		// 使用秘钥并指定为解密模式初始化解码器
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		// cipher.doFinal(byte[] b)在单部分操作中加密或解密数据，或完成多部分操作。 根据此秘钥的初始化方式，对数据进行加密或解密。
		return new String(cipher.doFinal(Base64.decode(base64Data)));
	}


	/**
	 * 安全提供者列表中注册解密算法提供者，这个加载过程还挺慢的，有时候要好几秒，只需要加载一次就能一直使用。
	 */
	private void initialize() {
		if (initialized) {
			return;
		}

		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

	/**
	 * 构造方法(容器初始化时从配置文件中获取key，在全局中维护一个唯一的SecretKeySpec)
	 * @param keyParam
	 */
	public WxdecodeUtils(@Value("${wxpay.mchKey:2er2e528d876e0ef66ca5344debb3eac}") String keyParam) {
		log.debug("\n实例化时获取到的key：" + keyParam);
		keyParam = "2er2e528d876e0ef66ca5344debb3eac";
		this.key_second = keyParam;
		// 转化成JAVA的密钥格式
		secretKey = new SecretKeySpec(WxMD5Util.MD5Encode(keyParam, "UTF-8").toLowerCase().getBytes(), algorithm);
	}
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ End ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
}