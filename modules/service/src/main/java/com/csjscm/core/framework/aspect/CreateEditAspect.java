package com.csjscm.core.framework.aspect;

import com.alibaba.fastjson.JSONObject;
import com.csjscm.core.framework.annotation.Creator;
import com.csjscm.core.framework.annotation.Editor;
import com.csjscm.sweet.framework.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class CreateEditAspect {

    @Before("execution(* com.csjscm.core.framework.dao.*.insert*(..))")
    public void injectCreator(JoinPoint point) throws Throwable{
        Object[] args=point.getArgs();
        if(args!=null && args.length>0){
            Object arg=args[0];
            String creatorFiledName=null;
            String editorFiledName=null;
            char[] name;
            JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
            String userName;
            if(sessionUser==null){
                userName=null;
            }else{
                userName=sessionUser.getString("name");
            }
            for(Field field:arg.getClass().getDeclaredFields()){
                if(field.isAnnotationPresent(Creator.class)){
                    name=field.getName().toCharArray();
                    name[0]-=32;
                    creatorFiledName="set"+String.valueOf(name);
                }
                if(field.isAnnotationPresent(Editor.class)){
                    name=field.getName().toCharArray();
                    name[0]-=32;
                    editorFiledName="set"+String.valueOf(name);
                }
            }
            for(Method method:arg.getClass().getDeclaredMethods()){
                if(method.getName().equals(creatorFiledName) || method.getName().equals(editorFiledName) ){
                    method.invoke(arg,userName);
                }
            }
        }
    }



    @Before("execution(* com.csjscm.core.framework.dao.*.update*(..))")
    public void injectEditor(JoinPoint point) throws Throwable{
        Object[] args=point.getArgs();
        if(args!=null && args.length>0){
            Object arg=args[0];
            String editorFiledName=null;
            char[] name;
            JSONObject sessionUser = (JSONObject) AuthUtils.getSessionUser();
            String userName;
            if(sessionUser==null){
                userName=null;
            }else{
                userName=sessionUser.getString("name");
            }
            for(Field field:arg.getClass().getDeclaredFields()){
                if(field.isAnnotationPresent(Editor.class)){
                    name=field.getName().toCharArray();
                    name[0]-=32;
                    editorFiledName="set"+String.valueOf(name);
                }
            }
            for(Method method:arg.getClass().getDeclaredMethods()){
                if(method.getName().equals(editorFiledName) ){
                    method.invoke(arg,userName);
                }
            }
        }
    }
}
