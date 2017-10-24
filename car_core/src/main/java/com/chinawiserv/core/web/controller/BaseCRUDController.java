package com.chinawiserv.core.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用Controller
 * Created by sungang on 2016/9/26.
 */
public abstract class BaseCRUDController<T extends Serializable> extends AbstarctBaseController {


    @Autowired
    public MessageSource message;

//    private Logger log = Logger.getLogger(BaseCRUDController.class);

    /**
     * 线程安全初始化reque，respose对象
     *
     * @param request
     * @param response
     * @ModelAttribute 表示该Controller的所有方法在调用前，先执行此@ModelAttribute方法
     */
    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response) {
        //此处可以验证每个方法的访问权限
        currentRequest.set(request);
        currentResponse.set(response);
    }

//

    /**
     * 线程安全
     */
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) currentResponse.get();
    }

    /**
     * 线程安全
     */
    public HttpServletRequest getRequest() {
        return (HttpServletRequest) currentRequest.get();
    }


    /**
     * 得到ModelAndView
     *
     * @return
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }


    /**
     * 添加Model消息
     *
     * @param messages
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, JSON.toJSONString(object), "application/json");
    }

    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Map<String, Object> getParams() {
        HttpServletRequest request = getRequest();
        Map<String, Object> parameters = Maps.newHashMap();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            if (value.length > 1) {
                String temp = "";
                for (int i = 0; i < value.length; i++) {
                    String val = value[i];
                    temp += val + ",";
                }
                temp = temp.substring(0, temp.length() - 1);
                //下面是防止恶意代码
//                Pattern pattern = Pattern.compile("<(\\s*)script(.*?)>");
//                Matcher matcher = pattern.matcher(temp);
//                String temp1 = matcher.replaceAll("#script#");
//                pattern = Pattern.compile("<(\\s*)/script(\\s*)>");
//                matcher = pattern.matcher(temp1);
//                temp1 = matcher.replaceAll("#/script#");
                parameters.put(key, temp);
            } else {
                //下面是防止恶意代码
//                Pattern pattern = Pattern.compile("<(\\s*)script(.*?)>");
//                Matcher matcher = pattern.matcher(value[0]);
//                String temp1 = matcher.replaceAll("#script#");
//                pattern = Pattern.compile("<(\\s*)/script(\\s*)>");
//                matcher = pattern.matcher(temp1);
//                temp1 = matcher.replaceAll("#/script#");

                parameters.put(key, value[0]);
            }
        }
        return parameters;
    }


    public synchronized void doPaginationOther(Integer pageNum,Integer pageSize) {
        pageNum = pageNum != null ? Integer.valueOf(pageNum) : 1; //从第几条数据开始检索
        pageSize = pageSize != null ? Integer.valueOf(pageSize) : 15; //从第几条数据开始检索
//        if (pageNum == 0) {
//            pageNum = 1;
//        } else {
//            pageNum = pageNum / pageSize + 1;
//        }
        PageHelper.startPage(pageNum, pageSize, true);
    }


    public synchronized JSONObject getReturnJson(List<Map<String, Object>> dataList) {
        JSONObject jsonObject = new JSONObject();
        PageInfo pageInfo = new PageInfo<>(dataList);
        jsonObject.put("data", dataList);
        jsonObject.put("recordsTotal", dataList.size());
        jsonObject.put("recordsFiltered", pageInfo.getTotal());
        jsonObject.put("pages", pageInfo.getPages());
        jsonObject.put("current_page", pageInfo.getPageNum());
        return jsonObject;
    }


    public synchronized void doPagination(Map<String, Object> params) {
        HttpServletRequest request = getRequest();
        String iDisplayStart = request.getParameter("iDisplayStart");
        String iDisplayLength = request.getParameter("iDisplayLength");
        Integer pageNum = iDisplayStart != null ? Integer.valueOf(iDisplayStart) : 1; //从第几条数据开始检索
        Integer pageSize = iDisplayLength != null ? Integer.valueOf(iDisplayLength) : 15; //从第几条数据开始检索
        if (pageNum == 0) {
            pageNum = 1;
        } else {
            pageNum = pageNum / pageSize + 1;
        }
        params.put("pageNum", pageNum);
        params.put("pageSize", pageSize);
    }


    public String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }


    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     *
     * @param request
     * @param name    cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    public static String getCookieValByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     *
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }


}
