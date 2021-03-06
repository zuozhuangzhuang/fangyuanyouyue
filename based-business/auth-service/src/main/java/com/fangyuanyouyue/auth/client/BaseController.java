package com.fangyuanyouyue.auth.client;

import com.fangyuanyouyue.auth.utils.PageResults;
import com.fangyuanyouyue.auth.utils.ReCode;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.IOException;

/**
 * 控制器基类
 *
 */
public class BaseController{

	protected Logger log = Logger.getLogger(this.getClass());

	protected static boolean encoded = false;
	/**
	 * 操作成功
	 * @return
	 * @throws IOException
	 */
	public String toSuccess(String reMsg) throws IOException {
		BaseClientResult result ;
		result = new BaseClientResult(ReCode.SUCCESS.getValue(),reMsg);
		return toJson(result.getMap());
	}
	
	
	/**
	 * 操作失败
	 * @param reCode
	 * @param reMsg
	 * @return
	 * @throws IOException
	 */
	public String toError(String reCode, String reMsg) throws IOException {
		BaseClientResult result ;
		result = new BaseClientResult(reCode,reMsg);
		log.info("controller输出："+result.toString());
		return toJson(result.getMap());
	}
	
	/**
	 * 操作失败
	 * @param reMsg
	 * @return
	 * @throws IOException
	 */
	public String toError(String reMsg) throws IOException {
		return toError(ReCode.FAILD.getValue(), reMsg);
	}
	
	/**
	 * 操作失败
	 * @return
	 * @throws IOException
	 */
	public String toError() throws IOException {
		return toError(ReCode.FAILD.getValue(), "操作失败！");
	}
	
	
	/**
	 * 操作成功
	 * @return
	 * @throws IOException
	 */
	public String toSuccess() throws IOException {
		return toSuccess("操作成功！");
	}

	
	/**
	 * 操作成功
	 * @return
	 * @throws IOException
	 */
	public String toPage(PageResults page, String dataKey) throws IOException {
		BaseClientResult result ;
		result = new BaseClientResult(ReCode.SUCCESS.getValue(),"");
		if(page!=null)
			result.put(dataKey, page.getResults());
		return toJson(result.getMap());
	}
	
	
	
	/**
	 * 将对象转为json字符串
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public String toJson(Object object) throws IOException {
		ObjectMapper mapper = getDefaultObjectMapper();
		try {
			String result = mapper.writeValueAsString(object);
			if(encoded){
				toEncJson(result);
			}
			log.info("controller输出："+result.toString());
			return result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
		
	}
	/**
	 * 返回数据
	 * @param result
	 * @return
	 * @throws IOException
	 */
	public String toResult(BaseClientResult result) throws IOException {
		return toJson(result.getMap());
	}
	
	/**
	 * 将json转为加密后的字符串
	 * @param str
	 * @return
	 */
	public String toEncJson(String str){
		return str;
	}
	
	
	
    private ObjectMapper getDefaultObjectMapper() {  
        ObjectMapper mapper = new ObjectMapper();  
        //设置将对象转换成JSON字符串时候:包含的属性不能为空或"";    
        //Include.Include.ALWAYS 默认    
        //Include.NON_DEFAULT 属性为默认值不序列化    
        //Include.NON_EMPTY 属性为 空（""）  或者为 NULL 都不序列化    
        //Include.NON_NULL 属性为NULL 不序列化    
       // mapper.setSerializationInclusion(Inclusion.NON_EMPTY);  
        //设置将MAP转换为JSON时候只转换值不等于NULL的  
        mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, true);
       // mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);  
       // mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  
        //设置有属性不能映射成PO时不报错  
        //mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);  
        return mapper;  
    }

    

}
