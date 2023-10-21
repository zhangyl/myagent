package com.zyl.poi;

import java.lang.annotation.*;
@Documented
@Target({ElementType.METHOD, ElementType.FIELD,ElementType.PARAMETER,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {
	
	/**
	 * 
	 * @Title: head 
	 * @Description: excel导出数据表表头
	 * @Author ouyangli
	 * @Date 2019年4月7日 15:10:56
	 * @Version 1.0.1
	 * @return
	 */
    String head() default "";
    
    /**
     * 
     * @Title: isDefault 
     * @Description: 是否为默认导出字段
     * @Author ouyangli
     * @Date 2019年4月7日 15:11:03
     * @Version 1.0.1
     * @return
     */
    boolean isDefault() default false;
    
}

