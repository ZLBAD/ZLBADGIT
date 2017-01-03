package com.zl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public abstract class BasicServlet<T> extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected String op;
	
	protected void outJson(Object obj,HttpServletResponse response) throws IOException{
		Gson gson=new Gson();
		String jsonString=gson.toJson(obj);
		
		//以流的方式写出客户端
		//取流  requuest.getWriter()
		//设定回传的数据类型  json     contentype
		response.setContentType("text/json;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(jsonString);
		out.close();
	}
	
	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		arg0.setCharacterEncoding("utf-8");
		op=arg0.getParameter("op");
		super.service(arg0, arg1);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * 
	 * @param request
	 * @param c
	 * @return
	 */
	protected T parseRequest(HttpServletRequest request, Class<T> c) {
		Map<String, String[]> map = request.getParameterMap();
		Set<String> methodNames = getMethodName(map.keySet());
		Method[] ms = c.getMethods();
		T t = null;
		try {
			t = (T) c.newInstance();

			for (Method method : ms) {
				for (String mn : methodNames) {
					if (method.getName().equals(mn)) {
						String keyname = mn.substring(3, 4).toLowerCase() + mn.substring(4);

						String typeName = method.getParameterTypes()[0].getName();

						String[] value = map.get(keyname);
						if (value != null && value.length == 1 && !"".equals(value[0])) {

							try {
								if ("java.lang.Integer".equals(typeName) || "int".equals(typeName)) {
									method.invoke(t, Integer.parseInt(value[0]));
								} else if ("java.lang.Double".equals(typeName) || "double".equals(typeName)) {
									method.invoke(t, Double.parseDouble(value[0]));
								} else if ("java.lang.Float".equals(typeName) || "float".equals(typeName)) {
									method.invoke(t, Float.parseFloat(value[0]));
								} else if ("java.lang.Long".equals(typeName) || "long".equals(typeName)) {
									method.invoke(t, Long.parseLong(value[0]));
								} else {
									method.invoke(t, value[0]);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						break;
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return t;
	}

	private Set<String> getMethodName(Set<String> keys) {
		Set<String> result = new HashSet<String>();
		for (String key : keys) {
			String newName = key.substring(0, 1).toUpperCase() + key.substring(1);
			result.add("set" + newName);
		}
		return result;
	}
}
