package org.eclipse.cdt.core.index;

/*
 * (c) Copyright QNX Software Systems Ltd. 2002.
 * All Rights Reserved.
 */
 
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.internal.core.index.IndexManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;

public class IndexModel {

	static IndexModel indexModel = null;
	static IndexManager manager = null;
	final static String INDEX_MODEL_ID = CCorePlugin.PLUGIN_ID + ".indexmodel";
	final static String ACTIVATION = "enable";

	static QualifiedName activationKey = new QualifiedName(INDEX_MODEL_ID, ACTIVATION);

	public boolean isEnabled(IProject project) {
		String prop = null;
		try {
			if (project != null) {
				prop = project.getPersistentProperty(activationKey);
			}
		} catch (CoreException e) {
		}
		return ((prop != null) && prop.equalsIgnoreCase("true"));
	}

	public void setEnabled(IProject project, boolean on) {
		String prop = new Boolean(on).toString();
		try {
			if (project != null) {
				project.setPersistentProperty(activationKey, prop);
			}
		} catch (CoreException e) {
		}
	}

	/**
	 * Search Project for tag symbol.
	 */
	public ITagEntry[] query (IProject project, String tag) {
		return manager.query(project, tag, true, false);
	}

	/**
	 * Search Project for tag symbol.
	 */
	public ITagEntry[] query (IProject project, String tag, boolean ignoreCase, boolean exactMatch) {
		return manager.query(project, tag, ignoreCase, exactMatch);
	}
	
	/**
	 * Add a resource to be index.  Containers(Folder, projects)
	 * resources are recusively search for C Files as define by
	 * CoreModel.isTranslationUnit().
	 */
	public void addResource(IResource resource) {
		manager.addResource(resource);
	}

	/**
	 * Add all the C files recurively going to all projects
	 * identified as C Projects by CoreModel.
	 */
	public void addAll () {
		manager.addAll();
	}

	/**
	 * Initialize default index Model.
	 */
	public static IndexModel getDefault() {
		if (indexModel == null) {
			indexModel = new IndexModel();
			manager = IndexManager.getDefault();
		}
		return indexModel;
	}

	private IndexModel () {
	}
}
