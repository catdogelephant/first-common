package com.mx.goldnurse.utils;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

/**
 * PDF生成辅助类
 * @author Goofy http://www.xdemo.org
 *
 */
@SuppressWarnings("all")
public class PdfHelper {
	
	public static ITextRenderer getRender() throws DocumentException, IOException {  
        
		ITextRenderer render = new ITextRenderer();  
        String path = getPath();  
        //添加字体，以支持中文
        render.getFontResolver().addFont(path + "pdf/arialuni.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);  
        render.getFontResolver().addFont(path + "pdf/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);  
        return render;  
    } 
	
	//获取要写入PDF的内容
	public static String getPdfContent(String ftlPath, String ftlName, Object o) 
			throws Exception {
		return useTemplate(ftlPath, ftlName, o);  
		
    }  
	
	//使用freemarker得到html内容
	public static String useTemplate(String ftlPath, String ftlName, Object o) 
			throws Exception {
        
		String html = null;  
        Template tpl = getFreemarkerConfig(ftlPath).getTemplate(ftlName);
        tpl.setEncoding("UTF-8");  
        StringWriter writer = new StringWriter();  
        tpl.process(o, writer);  
        writer.flush();  
        html = writer.toString();  
        return html;  
    } 
	
	/**
     * 获取Freemarker配置
     * @param templatePath
     * @return
     * @throws IOException
     */
	private static Configuration getFreemarkerConfig(String templatePath) throws IOException {  
        
		freemarker.template.Version version = new freemarker.template.Version("2.3.22");  
        Configuration config = new Configuration(version);
        config.setDirectoryForTemplateLoading(new File(templatePath));  
        config.setEncoding(Locale.CHINA, "utf-8");  
        return config;  
    }
	
	/**
     * 获取类路径
     * @return
     */
	public static String getPath(){
		return PdfHelper.class.getResource("/").getPath().substring(1);
    }

    public static void main(String[] args) {
        /*String htmlString = "<!DOCTYPE html [ <!ENTITY nbsp \"&#160;\"> ]>"
                + "<html lang=\"en\">"
                + "	<head>"
                + "   	<meta charset=\"UTF-8\"></meta>"
                + "    	<title>Issue Payment Receipt</title>"
                + "    	<style type=\"text/css\">"
                + "    			body {font-family: Arial Unicode MS;}"
                + "    		</style>"
                + "	</head>"
                + "	<body>"
                + "<p style=\" display: block; border: 0px; margin-top: 0.63em; margin-bottom: 1.8em; counter-reset: list-1 0 list-2 0 list-3 0 list-4 0 list-5 0 list-6 0 list-7 0 list-8 0 list-9 0; color: rgb(25, 25, 25); word-break:break-all;\">&nbsp; &nbsp; &nbsp; &nbsp; 王沪宁在会上指出，习近平总书记的重要讲话深刻阐述国庆70周年活动成功举办的重大意义，充分肯定庆祝活动筹办工作。</p>"
                + "<p><img src=\"https://i.goldnurse.com/upload/JP20190215122156-35711.png\"/></p>"
                + "	</body>"
                + "</html>";*/
        /*String htmlString = "<!DOCTYPE html> \n" +
                "<html lang=\"zh\"> \n" +
                "<head> \n" +
                "  <meta charset=\"UTF-8\"></meta> \n" +
                "  <title>#{知情同意书}</title> \n);" + //知情同意书名称：informed_consent_agreement：name
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no\"></meta> \n" +
                "  <meta name=\"apple-mobile-web-app-capable\" content=\"yes\"></meta> \n" +
                "  <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\"></meta> \n" +
                "  <style type=\"text/css\"> \n" +
                "               body {font-family: Arial Unicode MS;} \n" +
                "    #app{height: 100%;position: fixed;overflow: auto;} \n" +
                "    .part1{padding: 15px 20px; font-size: 14px;  font-weight: 400; text-align: left; color: #333333; line-height: 21px; min-height: calc(100% - 150px); overflow: auto;} \n" +
                "    .signInformation{height: 120px;display: flex;display: -webkit-flex;justify-content: space-between;} \n" +
                "    .sign{ width: 111px;}.signDiv{align-items: center;text-align: center; margin-top: 25px;padding-right: 46px;} \n" +
                "    .pName{height: 21px;font-size: 15px; font-weight: 500;text-align: left; color: #333333; line-height: 21px;} \n" +
                "    .pPerson{height: 17px;font-size: 12px;font-weight: 400;text-align: left;color: #333333;line-height: 17px;} \n" +
                "  </style> \n" +
                "</head> \n"  +
                "<body style=\"background: #fff;\"> \n" +
                "<div id=\"app\"> \n" +
                "  <!-- 知情同意书内容 --> \n" +
                "  <div class=\"part1\" style=\"display: block;\">这里是知情同意书内容这里是知情同意书内容这里是知情同意书内容这里是知情同意书内容这里是知情同意书内容王沪宁在会上指出，习近平总书记的重要讲话深刻阐述国庆70周年活动成功举办的重大意义，充分肯定庆祝活动筹办工作。</div> \n" +
                "  <!-- 签名信息 --> \n" +
                "  <div class=\"signInformation\"> \n" +
                "    <div style=\"padding-left: 30px;\"> \n" +
                "      <div class=\"pName\">患者或家属签名:</div> \n" +
                "      <div class=\"pPerson\" style=\"margin-top:15px;\">签订人关系：#{签订人关系}}</div> \n" + //签订人关系：order_other：patientNexus（字符串）
                "      <div class=\"pPerson\" style=\"margin-top:5px;\">王美丽</div> \n" +
                "    </div> \n" +
                "    <div class=\"signDiv\"><img class=\"sign\" src='https://i.goldnurse.com/upload/JP20190215122156-35711.png' /></div> \n" +
                "  </div> \n" +
                "</div> \n" +
                "</body> \n" +
                "</html> \n";*/
        /*String htmlString  = "<!DOCTYPE html> \n" +
                "<html lang=\"zh\"> \n" +
                "<head> \n" +
                "  <meta charset=\"UTF-8\"></meta> \n" +
                "  <title>#{知情同意书}</title> \n" + //知情同意书名称：informed_consent_agreement：name
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no\"></meta> \n" +
                "  <meta name=\"apple-mobile-web-app-capable\" content=\"yes\"></meta> \n" +
                "  <meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\"></meta> \n" +
                "  <style type=\"text/css\"> \n" +
                "    body {font-family: Arial Unicode MS;background: #f00;} \n" +
                "    #app{height: 100%;position: fixed;overflow: auto;} \n" +
                "    .part1{padding: 15px 20px; font-size: 14px; font-family: PingFangSC, PingFangSC-Regular; font-weight: 400; text-align: left; color: #333333; line-height: 21px; min-height: calc(100% - 150px); overflow: auto;} \n" +
                "    .signInformation{height: 120px;display: flex;display: -webkit-flex;justify-content: space-between;} \n" +
                "    .sign{ width: 111px;}.signDiv{align-items: center;text-align: center; margin-top: 25px;padding-right: 46px;} \n" +
                "    .pName{height: 21px;font-size: 15px;font-family: PingFangSC, PingFangSC-Medium; font-weight: 500;text-align: left; color: #333333; line-height: 21px;} \n" +
                "    .pPerson{height: 17px;font-size: 12px;font-family: PingFangSC, PingFangSC-Regular;font-weight: 400;text-align: left;color: #333333;line-height: 17px;} \n" +
                "  </style> \n" +
                "</head> \n" +
                "<body style=\"background: #fff;\"> \n" +
                "<div id=\"app\"> \n" +
                "  <!-- 知情同意书内容 --> \n" +
                "  <div class=\"part1\">这里是知情同意书内容这里是知情同意书内容这里是知情同意书内容这里是知情同意书内容这里是知情同意书内容</div> \\n" +
                "  <!-- 签名信息 --> \n" +
                "  <div class=\"signInformation\"> \n" +
                "    <div style=\"padding-left: 30px;\"> \n" +
                "      <div class=\"pName\">患者或家属签名:</div> \n" +
                "      <div class=\"pPerson\" style=\"margin-top:15px;\">签订人关系：#{签订人关系}}</div> \n" +//签订人关系：order_other：patientNexus（字符串）
                "      <div class=\"pPerson\" style=\"margin-top:5px;\">王美丽</div> \n" +
                "    </div> \n" +
                "    <div class=\"signDiv\"><img class=\"sign\" src='' /></div> \n" +
                "  </div> \n" +
                "</div> \n" +
                "</body> \n" +
                "</html> \n";*/
        // <!DOCTYPE html [ <!ENTITY nbsp "&#160;"> ]>
        String htmlString = "<!DOCTYPE html [ <!ENTITY nbsp \"&#160;\"> <!ENTITY  ldquo \"“\"> <!ENTITY rdquo \"”\">  ]>"
                + "<html lang=\"en\">"
                + "  <head>"
                + "     <meta charset=\"UTF-8\"></meta>"
                + "      <title>Issue Payment Receipt</title>"
                + "      <style type=\"text/css\">"
                + "          body {font-family: Arial Unicode MS;}"
                + "        </style>"
                + "  </head>"
                + "  <body>"
                + "<p>尊敬的患者及家属：</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>感谢您选择金牌护士居家护理服务！为<span style=\"color: #00ffff; text-decoration: underline;\">更好的给被服务方（以下简称为&ldquo;您&rdquo;）提供&nbsp; &nbsp;金牌护士平台 &nbsp;护理服务，请务必仔细阅读如下服务条款，并在知情同意书上签字确认。</span></em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline; color: #00ffff;\"><em>1.您需主动向护士出示国家正规医院医生开具的证明文件。包含但不仅限于：诊断证明书、医嘱处方、出院治疗单、维护记录单等。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em><span style=\"color: #00ffff; text-decoration: underline;\">2.本次服务过程中，使用的所有药品及耗材应由您提供（有效期内</span>），您需对药品的来源及安全性负责,您也可选择平台提供相关耗材套餐服务（保证来源及安全性），或护士代买耗材服务，凡需护士代买的，客户可要求护士提供耗材渠道来源、票据信息，如发生质量安全问题平台概不负责。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>3.为保障您的安全，护士服务前根据意识、病情、药品、耗材评估后，判定客户存在明显服务风险的，有权不提供该项服务。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>4.任何操作均有可能引起一定的不良反应、并发症等现象，为降低风险，请务必确保家中有成年家属陪伴监护，保持患者居住屋内的空气的洁净度，保持温湿度适宜，现需告知以下常见情况：</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>在护理服务过程中，主要提供的为技术服务，出现任何不适症状包括但不仅限于疼痛、出血、局部组织受损等；服务后出现任何不适症状包括但不仅限于继发感染、发热等；</em></span></p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>以上护理过程中出现不适症状护士应立即停止操作，并采取相应处理措施；情况严重时需立即与医院联系，及时就诊。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>5.在服务过程中，请务必听从护士安排。未经护士允许的情况下，不要有影响护士操作或对操作有不良效果的行为，以免发生意外。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>6.金牌护士平台向您承诺，所有提供护理技术服务的护士全部拥有护士资格证、护士执业证，卫生部（即国家卫生健康委员会）批准认证的执业护士，您可要求提供服务的护士出示护士资格证、护士执业证等相关证明（电子版视为具备同等证明）。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>7.为您提供服务的护士，仅提供您在金牌护士平台预约的服务，不提供该项服务范围外的任何服务。如护士提供服务范围外的任何服务，产生任何问题均与本平台无关。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>8.对于因客户与护士自行约定开展订单以外服务内容的、护士技能不匹配,客户仍坚持由该人提供服务等情况，造成任何肢体疾病与经济财产损失的，平台概不负责。</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>如有需要特别说明事项，请单独注明。</em></span></p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em><strong>&nbsp;</strong></em></span></p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em><strong>特别提示：</strong></em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>为提升服务质量，请您在每次服务后，对提供服务护士的技术水平、服务态度等进行真实、客观的评价，有任何意见及建议可与我们联系，感谢您的支持。祝您早日康复！</em></span></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p><span style=\"text-decoration: underline;\"><em>我已仔细阅读上文并理解其含义，经慎重考虑，我同意预约护士为我（或我的家属）进行护理技术服务，我已了解本次服务可能存在一定的风险，如在护理过程中产生任何风险，均与护士和金牌护士平台无关，护士和金牌护士平台无需承担由此产生的任何责任</em></span></p>\n"
                + "<p><img src='https://i.goldnurse.com/upload/JP20190215122156-35711.png'/></p>"
                + "  </body>"
                + "</html>";
        boolean b = PdfUtils.htmlToPdf("F:/testPDF/agreement2.pdf", htmlString, "F:/testPDF", "F:/testPDF/ttf/arial unicode ms.ttf");
        System.out.println(b);
    }
}
