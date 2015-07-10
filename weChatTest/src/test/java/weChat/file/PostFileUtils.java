package weChat.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PostFileUtils {
	
	// 设置代理
	  static{
	   System.setProperty("https.proxyHost", "localhost");
	   System.setProperty("https.proxyPort", "8888");
	  }

	public  static String ip ="http://192.168.82.119:8080/FileService";
	
	private  final  static String  lineEnd = System.getProperty("line.separator");    // The value is "\r\n" in Windows.
	
	private  final  static String twoHyphens = "--";  
	private  final   static String boundary="-------------------------acebdf13572468";
	/**
	 * 上传文件
	 * @param actionPath
	 * @param param
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static String postFile(String actionPath, Map<String, Object> params, String fileformname, String filePath) throws IOException{
		File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return "文件不存在";
        }
        URL url = new URL(ip + actionPath);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
 
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        con.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        con.setRequestProperty("Content-Type",
                "multipart/form-data;boundary=" + boundary);
        DataOutputStream dos = new DataOutputStream(con.getOutputStream());
        StringBuffer sb = new StringBuffer();
        //参数
        if(params != null){
        	 Iterator<Entry<String, Object>> it = params.entrySet().iterator();
        	 while(it.hasNext()){
        		 Entry<String, Object> entry = it.next();
        		 sb.append(twoHyphens + boundary + lineEnd);  
        		   sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + lineEnd);  
                   sb.append(lineEnd);  
                   sb.append(entry.getValue() + lineEnd);  
        	 }
        	 System.out.println("参数是:\n" + sb.toString());
        	 byte[] bytes = sb.toString().getBytes("utf-8");
        	 dos.write(bytes);
        }
        sb =new StringBuffer();
        sb.append(twoHyphens + boundary + lineEnd);
        sb.append("Content-Disposition: form-data; name=\"" + fileformname + "\"; filename=\"" + file.getName() + "\"" + lineEnd);
        sb.append("Content-Type: " + "application/octet-stream" + lineEnd);  
        sb.append(lineEnd);  
        byte[] bytes = sb.toString().getBytes("utf-8");
        dos.write(bytes);
        //文件开始上传
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int length = 0;
        byte[] bufferOut = new byte[1024];
        while ((length = in.read(bufferOut)) != -1) {
            dos.write(bufferOut, 0, length);
        }
        in.close();
        sb = new StringBuffer();
        sb.append(lineEnd);
        //文件上传结束
        dos.write(sb.toString().getBytes("utf-8"));
        sb = new StringBuffer();
        //结束标识
        sb.append(twoHyphens + boundary + twoHyphens + lineEnd);
        dos.write(sb.toString().getBytes("utf-8"));
        dos.flush();
       BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
       sb = new StringBuffer();
       String oneline = null;
       while((oneline = br.readLine()) != null){
    	   sb.append(oneline);
       }
       System.out.println("请求结果是："  + sb.toString());
       return sb.toString();
	}
}
