package com.mx.goldnurse.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberToCarrierMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.regex.Pattern;

public class PhoneAddess {

//	public static String getPhoneTr(String phone) {
////		String addess = "";
////		try {
////			String str = getPhoneAddess(phone);
////			if (StringUtils.isEmpty(str)) {
////				return "";
////			}
////			Document doc = Jsoup.parse(str);
////			String ss = doc.getElementsByClass("tdc2").get(1).toString();
////			if (StringUtils.isEmpty(ss)) {
////				return "";
////			}
////			String[] sdsdsf = stripHtml(ss.replace("<!-- <td></td> -->", "").replace("\r\n", "").replace("\n", "").replace(" ", "")).split("&nbsp;");
////			if (sdsdsf.length == 0) {
////				return "";
////			}
////			addess += sdsdsf[0];
////			if (sdsdsf.length > 1) {
////				addess += "-" + sdsdsf[1];
////			} else {
////				addess += "-" + sdsdsf[0];
////			}
////			addess += "-" + "未知";
////		} catch (MalformedURLException e) {
////			log.error("异常信息：", e);
////			return "";
////		}
////		return addess;
////	}


//	public static String stripHtml(String content) {
//		// <p>段落替换为换行
//		content = content.replaceAll("<p .*?>", "\r\n");
//		// <br><br/>替换为换行
//		content = content.replaceAll("<br\\s*/?>", "\r\n");
//		// 去掉其它的<>之间的东西
//		content = content.replaceAll("\\<.*?>", "");
//		// 还原HTML
//		// content = HTMLDecoder.decode(content);
//		return content;
//	}

//	public static String getPhoneAddess(String mobileNumber) throws MalformedURLException {
////		String urlString = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel="  + mobileNumber;
//		String urlString = "http://www.ip138.com:8080/search.asp?action=mobile&mobile=" + mobileNumber;
//		StringBuffer sb = new StringBuffer();
//		BufferedReader buffer;
//		URL url = new URL(urlString);
////        String province = "";
//		try {
//			//获取URL地址中的页面内容
//			InputStream in = url.openStream();
//			// 解决乱码问题
//			buffer = new BufferedReader(new InputStreamReader(in, "gb2312"));
//			String line = null;
//			//一行一行的读取数据
//			while ((line = buffer.readLine()) != null) {
//				sb.append(line);
//			}
//			in.close();
//			buffer.close();
////          Document doc = Jsoup.parse(sb.toString());
//			System.out.println(sb.toString());
//			return sb.toString();
//		} catch (Exception e) {
//			log.error("异常信息：", e);
//			return "";
//		}
//		//从JSONObject对象中读取城市名称
////      return province/*jsonObject.getString("cityname")*/;
//	}

//	/**
////	 * 通过百度网站获取手机号码归属地信息(省份-城市-未知/运营商)
////	 *
////	 * @url http://mobsec-dianhua.baidu.com/dianhua_api/open/location
////	 * @param phone 手机号码
////	 * @return
////	 */
////	public static String getPhoneAddress(String phone){
////		String phoneaddress = "未知-未知-未知";
////		try {
////			if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(phone.trim()) || phone.length() != 11){
////				return "未知-未知-未知";
////			}
////			String phoneString = HttpRequest.sendGet("http://mobsec-dianhua.baidu.com/dianhua_api/open/location", "tel=" + phone);
////			if(StringUtils.isEmpty(phoneString)){
////				return "未知-未知-未知";
////			}
////			JSONObject jsonObject = new JSONObject(phoneString);
////			JSONObject responseHeader = (JSONObject)jsonObject.get("responseHeader");
////			if(responseHeader == null){
////				return "未知-未知-未知";
////			}
////			Integer status = Integer.parseInt(responseHeader.get("status").toString());
////			if(status == null || status != 200){
////				return "未知-未知-未知";
////			}
////			JSONObject response = (JSONObject)jsonObject.get("response");
////			if(response == null){
////				return "未知-未知-未知";
////			}
////			JSONObject phoneAddress = (JSONObject) response.get(phone);
////			if(phoneAddress == null){
////				return "未知-未知-未知";
////			}
////			JSONObject phoneAddressDetail = (JSONObject)phoneAddress.get("detail");
////			if(phoneAddressDetail == null){
////				return "未知-未知-未知";
////			}
////			phoneaddress = "";
////			String province = phoneAddressDetail.getString("province");
////			if(StringUtils.isEmpty(province)){
////				phoneaddress += "未知";
////			}
////			if(StringUtils.isNotEmpty(province)){
////				phoneaddress += province;
////			}
////			//获取运营商名称
////			//String operator = phoneAddressDetail.getString("operator");
////			JSONArray area = phoneAddressDetail.getJSONArray("area");
////			if(area == null){
////				phoneaddress += "-未知-未知";
////			}
////			if(area != null){
////				JSONObject citys = (JSONObject) (area.get(0));
////				if(citys == null){
////					phoneaddress += "-未知-未知";
////				}
////				if(citys != null){
////					String city = citys.getString("city");
////					if(StringUtils.isEmpty(city)){
////						phoneaddress += "-未知-未知";
////					}
////					if(StringUtils.isNotEmpty(city)){
////						phoneaddress += ("-" + city + "-未知");
////					}
////				}
////			}
////		} catch (Exception e) {
////			log.error("异常信息：", e);
////			return "未知-未知-未知";
////		}
////		return phoneaddress;
////	}

//	private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//
//	private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();
//
//	private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();
//
//	private static Pattern phoneReg = Pattern.compile("\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\d{1,14}$");

//	/**
//	 * 根据国家代码和手机号  判断手机号是否有效
//	 * @param phoneNumber
//	 * @param countryCode
//	 * @return
//	 */
//	public static boolean checkPhoneNumber(String phoneNumber, String countryCode){
//		int ccode = Integer.valueOf(countryCode);
//		long phone = Long.valueOf(phoneNumber);
//		Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
//		pn.setCountryCode(ccode);
//		pn.setNationalNumber(phone);
//		return phoneNumberUtil.isValidNumber(pn);
//	}
//
//	/**
//	 * 根据国家代码和手机号  判断手机运营商
//	 * @param phoneNumber
//	 * @param countryCode
//	 * @return
//	 */
//	public static String getCarrier(String phoneNumber, String countryCode){
//		int ccode = Integer.valueOf(countryCode);
//		long phone = Long.valueOf(phoneNumber);
//		Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
//		pn.setCountryCode(ccode);
//		pn.setNationalNumber(phone);
//		//返回结果只有英文，自己转成成中文
//		String carrierEn = carrierMapper.getNameForNumber(pn, Locale.ENGLISH);
//		String carrierZh = "";
//		carrierZh += geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
//		switch (carrierEn) {
//			case "China Mobile":
//				carrierZh += "移动";
//				break;
//			case "China Unicom":
//				carrierZh += "联通";
//				break;
//			case "China Telecom":
//				carrierZh += "电信";
//				break;
//			default:
//				break;
//		}
//		return carrierZh;
//	}

//	/**
//	 *
//	 * 根据国家代码和手机号  手机归属地
//	 * @date 2015-7-13 上午11:33:18
//	 * @param @param phoneNumber 手机号码
//	 * @param @param countryCode 国家代码
//	 * @param @return    参数
//	 * @throws
//	 */
//	public static String getGeo(String phoneNumber, String countryCode) {
//		int ccode = Integer.valueOf(countryCode);
//		long phone = Long.valueOf(phoneNumber);
//		Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
//		pn.setCountryCode(ccode);
//		pn.setNationalNumber(phone);
//		String phoneAddress = geocoder.getDescriptionForNumber(pn, Locale.CHINA);
//		if(StringUtils.isEmpty(phoneAddress)){
//			return "未知-未知-未知";
//		}
//		System.out.println(phoneAddress);
////		String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
////		Matcher m = Pattern.compile(regex).matcher(getGeo(phoneAddress, "86"));
////		String province = null, city = null, county = null, town = null, village = null;
////		List<Map<String,String>> table = new ArrayList<>();
////		Map<String,String> row = null;
////		while(m.find()) {
////			row = new LinkedHashMap<>();
////			province = m.group("province");
////			row.put("province", province == null?"":province.trim());
////			city = m.group("city");
////			row.put("city", city == null?"":city.trim());
////			county = m.group("county");
////			row.put("county", county == null?"":county.trim());
////			town = m.group("town");
////			row.put("town", town == null?"":town.trim());
////			village = m.group("village");
////			row.put("village", village == null?"":village.trim());
////			table.add(row);
////		}
//		return "";
//	}

	/*public static JSONObject getPhoneNumberInfo(String phoneNumber) throws Exception {
	    //正则校验
	    Matcher matcher = phoneReg.matcher(phoneNumber);
	    if (!matcher.find()) {
	      	throw new Exception("The phone number maybe like:" + "+[National Number][Phone number], but got " + phoneNumber);
	    }
 		Phonenumber.PhoneNumber referencePhonenumber;
	    try {
	      	String language = "CN";
	      	referencePhonenumber = phoneNumberUtil.parse(phoneNumber, language);
	    } catch (NumberParseException e) {
	      	throw new Exception(e.getMessage());
	    }
    	String regionCodeForNumber = phoneNumberUtil.getRegionCodeForNumber(referencePhonenumber);
	    if(regionCodeForNumber == null) {
	      	throw new Exception("Missing region code by phone number " + phoneNumber);
	    }
	    boolean checkSuccess = PhoneUtil.checkPhoneNumber(referencePhonenumber.getNationalNumber(), referencePhonenumber.getCountryCode());
	    if(!checkSuccess) {
	      	throw new Exception("Not an active number:" + phoneNumber);
	    }
    	String description = geocoder.getDescriptionForNumber(referencePhonenumber, Locale.CHINA);
 		int countryCode = referencePhonenumber.getCountryCode();
    	long nationalNumber = referencePhonenumber.getNationalNumber();
	    JSONObject resultObject = new JSONObject();
	    //区域编码 Locale : HK, US, CN ...
	    resultObject.put("regionCode", regionCodeForNumber);
	    //国号: 86, 1, 852 ... @link: https://blog.csdn.net/wzygis/article/details/45073327
	    resultObject.put("countryCode", countryCode);
	    //去掉+号 和 国号/区号 后的实际号码
	    resultObject.put("nationalNumber", nationalNumber);
	    //所在地区描述信息
	    resultObject.put("description", description);
	    //去掉+号后的号码 (用于阿里云发送短信)
	    resultObject.put("number", String.valueOf(countryCode) + nationalNumber);
	    resultObject.put("fullNumber", phoneNumber);
    	return resultObject;
 	}*/

	/** 初始化手机号码工具类 */
	private static PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

	/** 提供与电话号码相关的运营商信息 */
	private static PhoneNumberToCarrierMapper carrierMapper = PhoneNumberToCarrierMapper.getInstance();

	/** 提供与电话号码有关的地理信息 */
	private static PhoneNumberOfflineGeocoder geocoder = PhoneNumberOfflineGeocoder.getInstance();

	/** 中国大陆区区号 */
	private final static int COUNTRY_CODE = 86;

	/** 直辖市 */
	private final static String[] MUNICIPALITY = { "北京市", "天津市", "上海市", "重庆市" };

	/** 自治区 */
	private final static String[] AUTONOMOUS_REGION = { "新疆", "内蒙古", "西藏", "宁夏", "广西" };


    /** 校验手机是否合规 2020年最全的国内手机号格式 */
    private static final String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";



    /**
	 * 根据手机号 判断手机号是否有效
	 * @param phoneNumber 手机号码
	 * @return true-有效 false-无效
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		long phone = Long.parseLong(phoneNumber);
		Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
		pn.setCountryCode(COUNTRY_CODE);
		pn.setNationalNumber(phone);
		return phoneNumberUtil.isValidNumber(pn);
	}

    /**
     * 校验手机号
     *
     * @param phone 手机号
     * @return boolean true:是  false:否
     */
    public static boolean isMobile(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return Pattern.matches(REGEX_MOBILE, phone);
    }

	/**
	 * 根据手机号 判断手机运营商
	 * @param phoneNumber 手机号码
	 * @return 如：广东省广州市移动
	 */
	public static String getCarrier(String phoneNumber) {
		long phone = Long.parseLong(phoneNumber);
		Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
		pn.setCountryCode(COUNTRY_CODE);
		pn.setNationalNumber(phone);
		// 返回结果只有英文，自己转成成中文
		String carrierEn = carrierMapper.getNameForNumber(pn, Locale.ENGLISH);
		String carrierZh = "";
		switch (carrierEn) {
			case "China Mobile":
				carrierZh += "移动";
				break;
			case "China Unicom":
				carrierZh += "联通";
				break;
			case "China Telecom":
				carrierZh += "电信";
				break;
			default:
				break;
		}
		return carrierZh;
	}

	public static String getCity(String phoneNum){
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();
		String language ="CN";
		Phonenumber.PhoneNumber referencePhonenumber = null;
		try {
			referencePhonenumber = phoneUtil.parse(phoneNum, language);
		} catch (NumberParseException e) {
			e.printStackTrace();
		}
		//手机号码归属城市 city
		String city= phoneNumberOfflineGeocoder.getDescriptionForNumber(referencePhonenumber,Locale.CHINA);
		return city;
	}

	/**
	 * 根据手机号 获取手机归属地
	 * @param phoneNumber 手机号码
	 * @return 如：广东省广州市
	 */
	public static String getGeo(String phoneNumber) {
		long phone = Long.parseLong(phoneNumber);
		Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
		pn.setCountryCode(COUNTRY_CODE);
		pn.setNationalNumber(phone);
		return geocoder.getDescriptionForNumber(pn, Locale.CHINESE);
	}

	/**
	 * 通过手机号获取归属地信息
	 * @param phoneNumber 手机号
	 * @return 正常情况：北京-北京-未知
	 * 		   错误情况：未知-未知-未知
	 */
	public static String getPhoneAddress(String phoneNumber){
		//1.校验手机号码是否有效
		if (!checkPhoneNumber(phoneNumber)) {
			return "未知-未知-未知";
		}
		//2.获取手机号码信息
		String geo = getGeo(phoneNumber);
		if(StringUtils.isEmpty(geo) || "中国".equals(geo)){
			return "未知-未知-未知";
		}
		//--1.如果是直辖市
		for (String val : MUNICIPALITY) {
			if (geo.equals(val)) {
				return val.replace("市", "") + "-" + val.replace("市", "") + "-未知";
			}
		}
		//--2.如果是自治区
		for (String val : AUTONOMOUS_REGION) {
			if (geo.startsWith(val)) {
				return val + "-" + geo.replace(val, "").replace("市", "") + "-未知";
			}
		}
		//--3.如果是其它情况
		String[] splitArr = geo.split("省");
		if (splitArr != null && splitArr.length == 2) {
			return splitArr[0] + "-" + splitArr[1].replace("市", "") + "-未知";
		}
		return "未知-未知-未知";
	}

	public static void main(String[] args) {
		System.out.println(isMobile("1f7560308198"));
	}
}