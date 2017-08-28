package com.bbieat.screen.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @desc    
 * @author  hasau 
 * @E_Mail  hasau@qq.com
 * @Date    2015年10月16日 下午4:16:11
 * @version 1.0.0 	
 */
public class NumberUtil {
	
	public static int getMinNum(List<BigDecimal> BS){
		int min =80;
		if(BS.size()==1){
			min = 0;
		}else if(!BS.isEmpty()){
			Collections.sort(BS);
			min = BS.get(0).intValue()/10 *10;
			if(min==100) min=0;
		}
		return min;
	}
	
	
	public static int minInt(BigDecimal bd ){
		int min  = bd.intValue()/10*10;
		if(min==100){
			min=0;
		}
		return min;
	}
 
	
}
