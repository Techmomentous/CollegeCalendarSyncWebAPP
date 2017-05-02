
package com.cc.server.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

public class Utility {
	public static String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		System.out.println("Part Header = {0}" + partHeader);
		for (String content : part.getHeader("content-disposition").split(";"))
		{
			if (content.trim().startsWith("filename"))
			{
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	public static boolean saveUploadFiles(Part part, String path, String fileName) throws IOException {
		boolean success = false;
		OutputStream out = null;
		InputStream filecontent = null;
		try
		{

			out = new FileOutputStream(new File(path + File.separator + fileName));
			filecontent = part.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1)
			{
				out.write(bytes, 0, read);
			}
			success = true;
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Error While Saving File : " + e);
		}
		catch (IOException e)
		{
			System.err.println("Error While Saving File : " + e);
		}
		catch (Exception e)
		{
			System.err.println("Error While Saving File : " + e);
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
			if (filecontent != null)
			{
				filecontent.close();
			}
		}
		return success;
	}
}
