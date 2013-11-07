package vamish.test.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckCode {

	private final static String URL_PATH = "http://jwgl2.jmu.edu.cn/Common/CheckCode.aspx";// ������·ͼƬ��·��

		public CheckCode() {
		}

		/**
		 * �������л�ȡͼƬ��Ϣ����������ʽ����
		 * 
		 * @return
		 */
		public static InputStream getImageViewInputStream() throws IOException {
			InputStream inputStream = null;

			URL url = new URL(URL_PATH);
			if (url != null) {
				HttpURLConnection httpURLConnection = (HttpURLConnection) url
						.openConnection();
				httpURLConnection.setConnectTimeout(3000);// �������ӳ�ʱ��ʱ��
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setDoInput(true);
				int response_code = httpURLConnection.getResponseCode();
				if (response_code == 200) {
					inputStream = httpURLConnection.getInputStream();
				}
			}
			return inputStream;
		}

		/**
		 * �������л�ȡͼƬ��Ϣ�����ֽ��������ʽ����
		 * 
		 * @return
		 */
		public static byte[] getImageViewArray() {
			byte[] data = null;
			InputStream inputStream = null;
			// ����Ҫ�رյ��������ֱ��д�뵽�ڴ���
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			try {
				URL url = new URL(URL_PATH);
				if (url != null) {
					HttpURLConnection httpURLConnection = (HttpURLConnection) url
							.openConnection();
					httpURLConnection.setConnectTimeout(3000);// �������ӳ�ʱ��ʱ��
					httpURLConnection.setRequestMethod("GET");// ���󷽷�
					httpURLConnection.setDoInput(true);// ��������
					int response_code = httpURLConnection.getResponseCode();
					int len = 0;
					byte[] b_data = new byte[1024];

					if (response_code == 200) {
						inputStream = httpURLConnection.getInputStream();
						while ((len = inputStream.read(b_data)) != -1) {
							outputStream.write(b_data, 0, len);
						}
						data = outputStream.toByteArray();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return data;
		}
}

