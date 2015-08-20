package my.awttest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.about.SystemProperties;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AwtTestServlet extends HttpServlet {

	private static final int width = 1000;
	private static final int height = 600;
	private static final int rows = 4;
	private static final int columns = 6;
	private static final double scale = 1000d;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if ("/chart.png".equals(req.getRequestURI())) {
			renderChart(resp);
		} else {
			renderIndex(resp);
		}
	}

	private void renderChart(HttpServletResponse response) throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				double value = scale * Math.random();
				String rowkey = String.format("Row %d", i + 1);
				String columnKey = String.format("Column %d", j + 1);
				dataset.addValue(value, rowkey, columnKey);
			}
		}

		JFreeChart chart = ChartFactory.createBarChart("A Chart", "X Axis", "Y Axis", dataset);

		OutputStream out = response.getOutputStream();
		response.setContentType("image/png");
		ChartUtilities.writeChartAsPNG(out, chart, width, height);
	}

	private void renderIndex(HttpServletResponse resp) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>\n");
		sb.append("<html>\n");
		sb.append("  <head>\n");
		sb.append("    <title>AWT Test</title>\n");
		sb.append("  </head>\n");
		sb.append("  <body>\n");
		sb.append("    <img src=\"/chart.png\" alt=\"Chart\" width=\"")
				.append(width)
				.append("\" height=\"")
				.append(height)
				.append("\" /><br/>\n");
		sb.append("    <pre>java.awt.headless=")
				.append(System.getProperty("java.awt.headless"))
				.append("</pre>");
		sb.append("  </body>\n");
		sb.append("</html>");

		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentLength(sb.length());

		resp.getWriter().write(sb.toString());
	}
}
