package com.sean.hrb.controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sean.hrb.dao.ConnectionUtil;

public class GolbalController extends HttpServlet {
	private Map<String,String> userMap=new HashMap<String,String>();
	private Object rs;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("run in doGet");
		resp.sendRedirect("ok.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		String username=req.getParameter("username");
		String password=req.getParameter("password");
		String uri=req.getRequestURI();
		String action =uri.substring(uri.lastIndexOf("/")+1,uri.indexOf(".do"));
		if(action.equals("login")) {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps =null;
			//获取session对象
			HttpSession session =req.getSession();
			try {
				ps=conn.prepareStatement("select * from user where username=?");
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				if(rs!=null&&rs.next()) {
					String dbpwd = rs.getString(2);
					//密码一致，登录成功
					resp.sendRedirect("login_success.jsp");//跳转至成功界面
					if(password.equals(dbpwd)){
						//在登录成功后使用session存储用户名，以供在页面上展示用户名
						session.setAttribute("username", username);
						PreparedStatement ps_second = null;
						//查询所有数据
						ps_second = conn.prepareStatement("select * from user");
						ResultSet rsList = ps_second.executeQuery();
						//定义泛型，存储返回的数据 这里使用了User对数据进行封装
						List<User> userList = new ArrayList<User>();
						//遍历返回数据并将其存储到userList中
						while(rsList.next()) {
							//实例化User
							User user =new User();
							//向user中赋值
							user.setUsername(rsList.getString(1));
							user.setAge(rsList.getInt(3));
							//将user存储到userList
							userList.add(user);
						}
						//将userList存储到session中
						session.setAttribute("userList", userList);
						resp.sendRedirect("login_success.jsp");
					}else {
						resp.sendRedirect("login_pwdFail.jsp");
					}
				}
			
			else {
				resp.sendRedirect("register_fail.jsp");
				}
			}catch(SQLException e) {
				resp.sendRedirect("register_fail.jsp");
				e.printStackTrace();
			}finally {
				if(ps!=null) {
					try {
						ps.close();
					}catch (SQLException e) {
	 					e.printStackTrace();
					}
				}
			}
		}else if(action.equals("login")) {
			Connection conn = ConnectionUtil.getConnection();
			PreparedStatement ps = null;
			try {
				ps=conn.prepareStatement("select* from user where username=?");
			ps.setString(1, username);
				ResultSet rs=ps.executeQuery();
				if(rs!=null&&rs.next()) {
					String dbpwd = rs.getString(2);
					if(password.equals(dbpwd)) {
						resp.sendRedirect("login_success.jsp");
				}else {
						resp.sendRedirect("login_fail.jsp");
					}
					}
				}catch(SQLException e) {
					resp.sendRedirect("login_fail.jsp");
					e.printStackTrace();
				}finally {
					if(ps!=null) {
					try {
							ps.close();
						}catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}			
		}			
		}			
}