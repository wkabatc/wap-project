package wap_project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AvatarUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	}

	String filePath = null;
	String filePathSql = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		PrintWriter out = response.getWriter();
		String cssTag= "<link rel='stylesheet' type='text/css' href='css/style.css'>";
	    out.println("<html>");
	    out.println("<head><title>Avatar</title>"+cssTag+"</head>");
	    out.println("<body>");
		response.setContentType("text/html");
		
		String file_name = null;
		String username = "";

		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			return;
		}
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			String query = "SELECT actualuser FROM actualuser order by id_actualuser desc LIMIT 1";

			Statement stmt = DBManager.getConnection().createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData meta = queryResult.getMetaData();
			int colCount = meta.getColumnCount();
			while (queryResult.next()) {

				for (int col = 1; col <= colCount; col++) {
					Object value = queryResult.getObject(col);

					if (value != null) {
						username = value.toString();
					}
				}
			}
			queryResult.close();
			stmt.close();

			List<FileItem> fields = upload.parseRequest(request);
			Iterator<FileItem> it = fields.iterator();
			if (!it.hasNext()) {
				return;
			}
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					if (file_name == null) {
						if (fileItem.getFieldName().equals("file_name")) {
							file_name = fileItem.getString();
						}
					}
				} else {
					if (fileItem.getSize() > 0) {
						filePathSql = "./avatars/" + fileItem.getName();
						String root = getServletContext().getRealPath("/");
						filePath = root + "avatars\\" + fileItem.getName();

						fileItem.write(new File(filePath));
						String avatarQue = "UPDATE users SET avatar_path = '" + filePathSql + "' WHERE username = '"
								+ username + "'";
						Statement stmt1 = DBManager.getConnection().createStatement();
						stmt1.executeUpdate(avatarQue);
						stmt1.close();
					} else {
						filePath = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (filePath == null) {
				out.println("No file selected!");
			} else {
				out.println("Avatar uploaded successfully!");
			}
			out.println(
					"<form action=\"UserPanelServlet\" method=\"post\"><button class=\"btn3\" type=\"submit\">BACK</button></form>");
			out.close();
		}
	}
}