package daddyok.weapp.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

import org.apache.commons.codec.binary.Base64;

import daddyok.weapp.dao.UserInfoDao;
import daddyok.weapp.utils.AESUtil;

public class UserInfoDeal {
	/**
	 * 解密用户敏感数据
	 */
	public void decodeUserInfo(String encryptedData,String session_key,String iv){
	    try {
	        byte[] resultByte = AESUtil.instance.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(session_key), Base64.decodeBase64(iv));
	        if(null != resultByte && resultByte.length > 0){
	            String userInfo = new String(resultByte, "UTF-8");
	            System.out.println("decrypt_info>>>>>>>"+userInfo);
	            UserInfoDao userDao = new UserInfoDao();
	            userDao.ParseUserInfo(userInfo);
	            
	        }
	    } catch (InvalidAlgorithmParameterException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	}
}
