package astrecognition;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	private static IWorkspaceRoot workspaceRoot;
	private static IJavaModel javaModel;
	private static IJavaProject javaProject;

	public static final String PLUGIN_ID = "ASTRecognition";
	private static Activator plugin;

	public Activator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

	static {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot wsRoot = ws.getRoot();
		workspaceRoot = wsRoot;
		javaModel = JavaCore.create(wsRoot);
	}

	/**
	 * Gets the workspace root ({@link IResource}).
	 * 
	 * @return
	 */
	public static IResource getWorkspaceRoot() {
		return workspaceRoot;
	}

	/**
	 * Gets the current {@link IJavaProject} under analysis.
	 * 
	 * @return
	 */
	public static IJavaProject getIJavaProject() {
		return javaProject;
	}
	
	public static void setIJavaProject(IJavaProject project) {
		javaProject = project;
	}
	
	
	/**
	 * Logs the message in the Error log view.
	 * 
	 * @param status The status code from {@link IStatus}.
	 * Can be one of the following:
	 * <ul> 
	 * <li>{@link IStatus#INFO}</li>
	 * <li>{@link IStatus#CANCEL}</li>
	 * <li>{@link IStatus#WARNING}</li>
	 * <li>{@link IStatus#ERROR}</li>
	 * <li>{@link IStatus#OK}</li>
	 * </ul>
	 * @param message The message to be logged.
	 * @param t The exception that caused this message.
	 */
	public static void log(int status, String message, Throwable t) {
		ILog aLog = plugin.getLog();
		IStatus aStatus = new Status(status, PLUGIN_ID, message, t);
 		aLog.log(aStatus);
	}
	
	public static IJavaModel getJavaModel() {
		return javaModel;
	}
}
