package weChat.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import weChat.file.FileDigest;
import weChat.file.PostFileUtils;

public class UploadFileControllerTest {

	@Test
	public void testUploadfile() throws IOException{
		PostFileUtils.ip ="http://123.59.55.176:5081/FileService";
		String actionPath ="/uploadfile";
		String filePath ="C:\\Users\\Administrator\\Pictures\\林龙灯照片-照相馆.JPG";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fileext", "jpg");
		params.put("chunk", 0);
		params.put("chunks", 1);
		params.put("md5value", FileDigest.getFileMD5(filePath));
		params.put("file_type", -1);
		PostFileUtils.postFile(actionPath, params, "file", filePath);
	}
	@Test
	public void testTestFile() throws IOException{
		String actionPath ="/testFile/testFile";
		PostFileUtils.ip ="http://localhost:8080/weChat";
		String filePath ="C:\\Users\\Administrator\\Documents\\settings.xml";
		Map<String, Object> params = new HashMap<String, Object>();
		PostFileUtils.postFile(actionPath, params, "file", filePath);
	}
}
