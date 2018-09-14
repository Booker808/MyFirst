package com.csjscm.core.framework.common.util;


import org.springframework.beans.BeanUtils;

public class BeanutilsCopy {
    /**
     * copy单个对象
     * @param dest
     * @param orig
     */

    public static void copyProperties(Object dest, Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            throw  new  BussinessException("copy对象异常");
        }

    }
}
