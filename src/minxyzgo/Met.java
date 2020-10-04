package minxyzgo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Met {
	public static Object Reflection(Object object ,Class<?> cclass,String methodName,Class<?>[] paramclasses, Object... args) {
		
		try {
			
			Method method = cclass.getMethod(methodName,paramclasses);
			method.setAccessible(true);
			return method.invoke(object,args);
			
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

}