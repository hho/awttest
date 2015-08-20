package my.awttest;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Init implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		ServletRegistration.Dynamic registration = ctx.addServlet("awttest", new AwtTestServlet());
		registration.addMapping("/*");
	}
}
