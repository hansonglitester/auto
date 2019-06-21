package com.hsl.cn.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * HTTP请求工具类
 *
 * @copyright  Copyright (c) 2008- 2017 All rights reserved
 * @created  2017年3月25日 下午3:42:56
 * @since 1.0
 * @author Wenzhong Gu
 * @version 2.0
 */
public class HttpUtils {

	private static final String AMP = "&";
	private static final int SIZE = 1024 * 1024;

	private static final Logger LOG = LogManager.getLogger(HttpUtils.class);

	private HttpUtils() {
	}

	/**
	 * @param strUrl
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String get(String strUrl, Map<String, String> map) throws Exception {
		String strtTotalURL = "";
		StringBuilder result = new StringBuilder();
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL = strUrl + "?" + getReqParam(map);
		} else {
			strtTotalURL = strUrl + "&" + getReqParam(map);
		}
		LOG.info("++++++request provider URL={}++++++", strtTotalURL);
		URL url = new URL(strtTotalURL);
		BufferedReader in = null;
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			HttpURLConnection.setFollowRedirects(true);
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"), SIZE);
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				} else {
					result.append(line);
				}
			}
			LOG.info("++++++request result data={}++++++", result);
		} catch (Exception e) {
			LOG.info("++++++excetpin,strUrl={},Map={}++++++", strUrl, map);
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
		return result.toString();
	}

	public static String get(String reqUrl, String content) throws IOException {
		return get(reqUrl, content, "UTF-8");
	}

	/**
	 * @param reqUrl
	 * @param content
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String get(String reqUrl, String content, String charset) throws IOException {

		String strtTotalURL = "";
		StringBuilder result = new StringBuilder();
		if (strtTotalURL.indexOf("?") == -1) {
			strtTotalURL = reqUrl + "?" + content;
		} else {
			strtTotalURL = reqUrl + "&" + content;
		}
		LOG.info("++++++request provider URL={}++++++", strtTotalURL);
		URL url = new URL(strtTotalURL);
		BufferedReader in = null;
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			HttpURLConnection.setFollowRedirects(true);
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset), SIZE);
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				} else {
					result.append(line);
				}
			}
			LOG.info("++++++request result data={}++++++", result);
		} catch (IOException e) {
			LOG.info("++++++excetpin,strUrl={},content={}++++++", reqUrl, content);
			e.printStackTrace();
		} finally {
			if (in != null){
				in.close();
			}
		}
		return result.toString();
	}
	
	/**
	 * @param reqUrl
	 * @param paramMap
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String post(String reqUrl, Map<String, String> paramMap, String charset) throws IOException {

		String content = getReqParam(paramMap);
		List<String> result = new ArrayList<String>();
		URL url = new URL(reqUrl);
		BufferedReader bin = null;

		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(false);
			con.setUseCaches(false);

			con.setRequestMethod("POST");
			con.setConnectTimeout(30000);// 连接超时 单位毫秒
			con.setDoOutput(true);// 是否输入参数
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

			DataOutputStream dataOutput = new DataOutputStream(con.getOutputStream());

			byte[] bytes = content.toString().getBytes();
			dataOutput.write(bytes);// 输入参数

			dataOutput.flush();
			dataOutput.close();

			bin = new BufferedReader(new InputStreamReader(con.getInputStream(), charset), SIZE);

			while (true) {
				String line = bin.readLine();
				if (line == null) {
					break;
				} else {
					result.add(line);
				}
			}
		} catch (Exception e) {
			LOG.info("++++++excetpin,reqUrl={},Map={}++++++", reqUrl, paramMap);
			e.printStackTrace();
		} finally {
			if (bin != null)
				bin.close();
		}
		return result.toString();
	}

	/**
	 * @param reqUrl
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public static String post(String reqUrl, Map<String, String> paramMap) throws IOException {

		return post(reqUrl, paramMap, "UTF-8");

	}

	/**
	 * @param reqUrl
	 * @param content
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String post(String reqUrl, String content, String charset) throws IOException {

		URL url = new URL(reqUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);

		con.setRequestMethod("POST");
		con.setConnectTimeout(30000);// 连接超时 单位毫秒
		con.setDoOutput(true);// 是否输入参数
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		// con.setRequestProperty("Content-Type","text/plain;charset=" +
		// charset);

		DataOutputStream dataOutput = new DataOutputStream(con.getOutputStream());

		byte[] bytes = content.toString().getBytes(charset);
		dataOutput.write(bytes);// 输入参数

		dataOutput.flush();
		dataOutput.close();

		BufferedReader bin = new BufferedReader(new InputStreamReader(con.getInputStream(), charset), SIZE);

		StringBuilder result = new StringBuilder();
		while (true) {
			String line = bin.readLine();
			if (line == null) {
				break;
			} else {
				result.append(line);
			}
		}
		bin.close();
		return result.toString();
	}

	/**
	 * @param reqUrl
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static String post(String reqUrl, String content) throws IOException {

		return post(reqUrl, content, "UTF-8");

	}

	/**
	 * 此方法由GET和POST共用
	 *
	 * @param paramMap
	 * @return
	 */
	public static String getReqParam(Map<String, String> paramMap) {

		if (null == paramMap || paramMap.keySet().size() == 0) {
			return "";
		}

		StringBuffer url = new StringBuffer();
		Set<Entry<String, String>> entrySet = paramMap.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();

		Entry<String, String> entry;
		while (iterator.hasNext()) {
			entry = iterator.next();
			url.append(entry.getKey()).append("=").append(entry.getValue()).append(AMP);
		}

		String strURL = "";
		strURL = url.toString();
		if (AMP.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		LOG.info("++++++request map toString={}++++++", strURL);
		return strURL;

	}

	
	public static void downloadFile(String urlPath, String downloadDir,String fileName) {

        BufferedInputStream bin = null;
        OutputStream out = null;

        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileName;
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file);

            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                out.write(buf, 0, size);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bin != null) {
                try {
                    bin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}