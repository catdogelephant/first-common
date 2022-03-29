package com.mx.goldnurse.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 获取经纬度
 *
 * @author jueyue 返回格式：Map<String,Object> map map.put("status",
 * reader.nextString());//状态 map.put("result", list);//查询结果
 * list<map<String,String>>
 * 密钥:C0a9eecc5b366bde41202sljdflsjfsldjf   需要自己去申请
 * Created by hellcat on 2016/7/27.
 */
public class GetLatAndLngByBaidu {

    public static String[] cityArray = {"普兰店市",
            "七台河市",
            "黔东南苗族侗族自治州",
            "黔南布依族苗族自治州",
            "黔西南布依族苗族自治州",
            "钦州市",
            "庆阳市",
            "任丘市",
            "三河市",
            "三亚市",
            "沙河市",
            "商洛市",
            "尚志市",
            "深州市",
            "舒兰市",
            "双城市",
            "双辽市",
            "双鸭山市",
            "四子王旗",
            "苏尼特右旗",
            "苏尼特左旗",
            "随州市",
            "太仆寺旗",
            "洮南市",
            "调兵山市",
            "萍乡市",
            "平凉市",
            "磐石市",
            "怒江傈僳族自治州",
            "讷河市",
            "南宫市",
            "奈曼旗",
            "莫力达瓦达斡尔族自治旗",
            "密山市",
            "梅州市",
            "梅河口市",
            "眉山市",
            "满洲里市",
            "漯河市",
            "潞城市",
            "鹿泉市",
            "陇南市",
            "龙井市",
            "六盘水市",
            "凌源市",
            "凌海市",
            "铜川市",
            "铜陵市",
            "图们市",
            "土默特右旗",
            "土默特左旗",
            "瓦房店市",
            "文山壮族苗族自治州",
            "翁牛特旗",
            "乌海市",
            "乌拉特后旗",
            "乌拉特前旗",
            "乌拉特中旗",
            "乌兰察布市",
            "乌兰浩特市",
            "乌审旗",
            "梧州市",
            "五常市",
            "武安市",
            "西双版纳傣族自治州",
            "西乌珠穆沁旗",
            "临夏回族自治州",
            "临江市",
            "临沧市",
            "辽源市",
            "凉山彝族自治州",
            "莱芜市",
            "来宾市",
            "库伦旗",
            "克孜勒苏柯尔克孜自治州",
            "克什克腾旗",
            "科尔沁左翼中旗",
            "科尔沁左翼后旗",
            "锡林郭勒盟",
            "锡林浩特市",
            "咸宁市",
            "湘西土家族苗族自治州",
            "襄樊市",
            "镶黄旗",
            "孝义市",
            "辛集市",
            "忻州市",
            "新巴尔虎右旗",
            "新巴尔虎左旗",
            "新乐市",
            "新民市",
            "新余市",
            "兴安盟",
            "兴城市",
            "宣城市",
            "牙克石市",
            "延边朝鲜族自治州",
            "延吉市",
            "阳泉市",
            "伊春市",
            "伊金霍洛旗",
            "伊犁哈萨克自治州",
            "鹰潭市",
            "永济市",
            "榆树市",
            "玉树藏族自治州",
            "原平市",
            "云浮市",
            "扎赉特旗",
            "扎兰屯市",
            "扎鲁特旗",
            "张家界市",
            "正蓝旗",
            "正镶白旗",
            "中卫市",
            "舟山市",
            "庄河市",
            "准格尔旗",
            "涿州市",
            "遵化市",
            "科尔沁右翼中旗",
            "科尔沁右翼前旗",
            "开原市",
            "喀喇沁旗",
            "九台市",
            "景德镇市",
            "晋州市",
            "金昌市",
            "介休市",
            "蛟河市",
            "佳木斯市",
            "冀州市",
            "集安市",
            "鸡西市",
            "霍州市",
            "霍林郭勒市",
            "珲春市",
            "黄山市",
            "黄南藏族自治州",
            "黄骅市",
            "淮南市",
            "淮北市",
            "桦甸市",
            "虎林市",
            "侯马市",
            "红河哈尼族彝族自治州",
            "衡阳市",
            "黑河市",
            "鹤岗市",
            "鹤壁市",
            "贺州市",
            "河源市",
            "河津市",
            "河间市",
            "河池市",
            "和龙市",
            "杭锦旗",
            "杭锦后旗",
            "汉中市",
            "海西蒙古族藏族自治州",
            "海南藏族自治州",
            "海城市",
            "海北藏族自治州",
            "果洛藏族自治州",
            "贵港市",
            "固原市",
            "古交市",
            "公主岭市",
            "根河市",
            "藁城市",
            "高平市",
            "高碑店市",
            "甘孜藏族自治州",
            "甘南藏族自治州",
            "盖州市",
            "阜新市",
            "抚州市",
            "凤城市",
            "丰镇市",
            "汾阳市",
            "防城港市",
            "二连浩特市",
            "恩施土家族苗族自治州",
            "鄂州市",
            "鄂温克族自治旗",
            "鄂托克前旗",
            "鄂托克旗",
            "鄂伦春自治旗",
            "额济纳旗",
            "额尔古纳市",
            "敦化市",
            "东乌珠穆沁旗",
            "东港市",
            "定州市",
            "定西市",
            "迪庆藏族自治州",
            "灯塔市",
            "德惠市",
            "德宏傣族景颇族自治州",
            "大石桥市",
            "大理白族自治州",
            "大安市",
            "达拉特旗市",
            "达尔罕茂明安联合旗",
            "楚雄彝族自治州",
            "崇左市",
            "池州市",
            "陈巴尔虎旗",
            "郴州市",
            "潮州市",
            "巢湖市",
            "昌吉回族自治州",
            "察哈尔右翼中旗",
            "察哈尔右翼前旗",
            "察哈尔右翼后旗",
            "博尔塔拉蒙古自治州",
            "亳州市",
            "泊头市",
            "北镇市",
            "北票市",
            "白银市",
            "白山市",
            "白城市",
            "霸州市",
            "巴音郭楞蒙古自治州",
            "巴彦淖尔市",
            "巴林左旗",
            "巴林右旗",
            "敖汉旗",
            "安顺市",
            "安康市",
            "安国市",
            "阿荣旗",
            "阿鲁科尔沁旗",
            "阿拉善左旗",
            "阿拉善右旗",
            "阿拉善盟",
            "阿尔山市",
            "阿坝藏族羌族自治州",
            "阿巴嘎旗",
            "韶关市",
            "蚌埠市",
            "丽水市",
            "通化市",
            "自贡市",
            "阳江市",
            "宿州市",
            "六安市",
            "毕节市",
            "朝阳市",
            "鳌江-龙港镇市",
            "大同市",
            "十堰市",
            "长安镇",
            "虎门镇市",
            "百色市",
            "北海市",
            "阜阳市",
            "滁州市",
            "吴忠市",
            "内江市",
            "石嘴山市",
            "张掖市",
            "衢州市",
            "武威市",
            "嘉峪关市",
            "攀枝花市",
            "酒泉市",
            "都江堰市",
            "天水市",
            "资阳市",
            "遂宁市",
            "巴中市",
            "晋中市",
            "广元市",
            "广安市",
            "濮阳市",
            "南平市",
            "雅安市",
            "辽阳市",
            "西昌市",
            "玉溪市",
            "昭通市",
            "娄底市",
            "保山市",
            "吉安市",
            "普洱市",
            "朔州市",
            "丽江市",
            "浏阳市",
            "海宁市",
            "晋城市",
            "奉化市",
            "铁岭市",
            "余姚市",
            "丹东市",
            "益阳市",
            "崇明市",
            "常熟市",
            "衡水市",
            "邵阳市",
            "玉环市",
            "清远市",
            "丹阳市",
            "泸州市",
            "诸暨市",
            "黄石市",
            "怀化市",
            "永州市",
            "增城市",
            "绥化市",
            "晋江市",
            "运城市",
            "石河子市",
            "宁德市",
            "荆门市",
            "牡丹江市",
            "孝感市",
            "玉林市",
            "本溪市",
            "秦皇岛市",
            "三门峡市",
            "齐齐哈尔市",
            "阿克苏市",
            "承德市",
            "四平市",
            "喀什市",
            "黄冈市",
            "伊宁市",
            "荆州市",
            "哈密市",
            "莆田市",
            "开封市",
            "昌吉市",
            "渭南市",
            "临汾市",
            "抚顺市",
            "吕梁市",
            "马鞍山市",
            "库尔勒市",
            "张家口市",
            "克拉玛依市",
            "拉萨市",
            "葫芦岛市",
            "宜宾市",
            "宜春市",
            "锦州市",
            "上饶市",
            "延安市",
            "盘锦市",
            "达州市",
            "乐山市",
            "南充市",
            "德阳市",
            "湘潭市",
            "长治市",
            "呼伦贝尔市",
            "三明市",
            "遵义市",
            "日照市",
            "安庆市",
            "龙岩市",
            "揭阳市",
            "营口市",
            "驻马店市",
            "信阳市",
            "汕头市",
            "商丘市",
            "九江市",
            "曲靖市",
            "肇庆市",
            "桂林市",
            "平顶山市",
            "赣州市",
            "宿迁市",
            "彬州市",
            "赤峰市",
            "焦作市",
            "周口市",
            "安阳市",
            "连云港市",
            "松原市",
            "咸阳市",
            "新乡市",
            "湖州市",
            "通辽市",
            "许昌市",
            "枣庄市",
            "株洲市",
            "绵阳市",
            "珠海市",
            "宝鸡市",
            "柳州市",
            "菏泽市",
            "廊坊市",
            "湛江市",
            "芜湖市",
            "江门市",
            "淮安市",
            "茂名市",
            "滨州市",
            "漳州市",
            "常德市",
            "聊城市",
            "岳阳市",
            "德州市",
            "威海市",
            "南阳市",
            "惠州市",
            "中山市",
            "襄阳市",
            "宜昌市",
            "泰安市",
            "鞍山市",
            "吉林市",
            "保定市",
            "金华市",
            "义乌市",
            "张家港市",
            "江阴市",
            "昆山市",
            "镇江市",
            "泰州市",
            "榆林市",
            "沧州市",
            "嘉兴市",
            "台州市",
            "扬州市",
            "东营市",
            "洛阳市",
            "临沂市",
            "邯郸市",
            "盐城市",
            "济宁市",
            "绍兴市",
            "鄂尔多斯市",
            "常州市",
            "潍坊市",
            "徐州市",
            "大庆市",
            "南通市",
            "包头市",
            "泉州市",
            "呼和浩特市",
            "西宁市",
            "银川市",
            "兰州市",
            "海口市",
            "贵阳市",
            "乌鲁木齐市",
            "唐山市",
            "淄博市",
            "温州市",
            "昆明市",
            "南宁市",
            "南昌市",
            "合肥市",
            "太原市",
            "烟台市",
            "无锡市",
            "东莞市",
            "佛山市",
            "宁波市",
            "邢台市",
            "大理市",
            "暂无城市",
            "石家庄市",
            "沈阳市",
            "西安市",
            "哈尔滨市",
            "上海市",
            "郑州市",
            "长春市",
            "重庆市",
            "苏州市",
            "武汉市",
            "成都市",
            "广州市",
            "深圳市",
            "济南市",
            "福州市",
            "大连市",
            "杭州市",
            "天津市",
            "厦门市",
            "青岛市",
            "南京市",
            "长沙市",
            "北京市",
            "大同"};

    // public static List<City> cityList= new ArrayList<City>(cityArray.length);

    /**
     * 此方法调用百度接口 返回输入地址的经纬度坐标 key lng(经度),lat(纬度)；
     */
    public static Map<String, String> getGeocoderLatitude(String address) {
        BufferedReader in = null;
        try {
            Map<String, String> paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("address", address);
            paramsMap.put("output", "json");
            paramsMap.put("ak", "m1WWtclDZXOme0b3IIqLDUrqWGj3IPQZ");
            String quest = SnCal.toQueryString(paramsMap);
            URL tirc = new URL("http://api.map.baidu.com/geocoder/v2/?" + quest + "&sn=" + SnCal.result(paramsMap));

            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            Map<String, String> map = null;
            if (StringUtils.isNotEmpty(str)) {
                int lngStart = str.indexOf("lng\":");
                int lngEnd = str.indexOf(",\"lat");
                int latEnd = str.indexOf("},\"precise");
                if (lngStart > 0 && lngEnd > 0 && latEnd > 0) {
                    String lng = str.substring(lngStart + 5, lngEnd);
                    String lat = str.substring(lngEnd + 7, latEnd);
                    map = new HashMap<String, String>();
                    map.put("lng", lng);
                    map.put("lat", lat);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static Map<String, String> getlnglat(String paht) {
        Map<String, String> json = GetLatAndLngByBaidu.getGeocoderLatitude(paht);
        return json;
    }

    /**
     * 根据高德地图获取的经纬度坐标，使用高德api中的路径规划接口来获取两个经纬度之间的距离，单位为米
     * 直线距离
     *
     * @param startLonLat 116.413731,39.979324
     * @param endLonLat   116.417537,39.97722
     * @return
     * @author fyh
     * @date 2019/12/17 19:52
     */
    public static Long getDistance(String startLonLat, String endLonLat) {
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = 0L;
        String queryUrl = "type=0&key=a47c4a84f7b38efa625746c7f9fa57f2&origins=" + startLonLat + "&destination=" + endLonLat;

        String queryResult = HttpRequest.sendGet("http://restapi.amap.com/v3/distance", queryUrl, "");
        JSONObject jo = JSON.parseObject(queryResult);
        JSONArray ja = jo.getJSONArray("results");
        Object obj = JSONObject.parseObject(ja.getString(0)).get("distance");
        if (obj == null) {
            return result;
        }
        result = Long.parseLong(obj.toString());
        return result;

    }

    /**
     * 高德驾车距离
     *
     * @return
     * @author fyh
     * @date 2019/12/27 18:13
     * @params origin 出发地 116.413731,39.979324
     * @params destination 目的地 116.413731,39.979324
     */
    public static Long getDirectionDistance(String origin, String destination) {
        //返回起始地startAddr与目的地endAddr之间的距离，单位：米
        Long result = 0L;
        String queryUrl = "extensions=base&key=a47c4a84f7b38efa625746c7f9fa57f2&origin=" + origin + "&destination=" + destination;
        String queryResult = HttpRequest.sendGet("https://restapi.amap.com/v3/direction/driving", queryUrl, "");
        JSONObject jo = JSON.parseObject(queryResult);
        JSONObject routeJSON = jo.getJSONObject("route");
        JSONArray ja = routeJSON.getJSONArray("paths");
        Object obj = JSONObject.parseObject(ja.getString(0)).get("distance");
        if (obj == null) {
            return result;
        }
        result = Long.parseLong(obj.toString());
        return result;
    }

    /**
     * 通过地址获取该地址在高德地图的经纬度
     *
     * @param address
     * @return 为空时地址信息有误；否则返回 {lon=116.397499, lat=39.908722}
     * @author gaop
     * @date 2019年4月3日
     */
    public static Map<String, String> getlonlatByGaoDe(String address) {
        address = address.replaceAll("\\s", "");
        String param = "key=a47c4a84f7b38efa625746c7f9fa57f2&output=JSON&address=" + address;
        //String result = HttpRequest.sendGet("http://restapi.amap.com/v3/geocode/geo", url,"");
        //在本地请求通过post请求可以获取经纬度以及地址信息
//		String result = HttpRequest.sendPost("", url,"");
        String result = HttpRequest.sendGet("http://restapi.amap.com/v3/geocode/geo", param);
        JSONObject parseObject = JSON.parseObject(result);
        String count = parseObject.get("count").toString();
        if (count.equals("0")) {
            return null;
        }
        JSONObject geocodes = (JSONObject) ((JSONArray) parseObject.get("geocodes")).get(0);
        String location = geocodes.get("location").toString();
        String[] split = location.split(",");
        Map<String, String> map = new HashMap<>();
        map.put("lon", split[0]);
        map.put("lat", split[1]);
        return map;
    }

}
