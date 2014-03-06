/**
 *  Copyright (c) 2013-20A4 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package tern.eclipse.ide.internal.ui.properties;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import tern.eclipse.ide.core.IDETernProject;
import tern.eclipse.ide.core.scriptpath.ITernScriptPath;
import tern.eclipse.ide.internal.ui.Trace;
import tern.eclipse.ide.ui.ImageResource;
import tern.eclipse.ide.ui.TernUIPlugin;

/**
 * Tern script paths property page.
 * 
 */
public class TernScriptPathsPropertyPage extends AbstractTernPropertyPage
		implements IWorkbenchPreferencePage {

	private TernScriptPathsBlock scriptPathsBlock;

	public TernScriptPathsPropertyPage() {
		super();
		setImageDescriptor(ImageResource
				.getImageDescriptor(ImageResource.IMG_LOGO));
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(TernUIPlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);

		noDefaultAndApplyButton();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);

		IDETernProject ternProject = null;
		try {
			ternProject = getTernProject();
		} catch (CoreException e1) {
		}
		scriptPathsBlock = new TernScriptPathsBlock(ternProject);
		scriptPathsBlock.createControl(parent);

		Control control = scriptPathsBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		loadScriptPaths();

		applyDialogFont(parent);
		return parent;
	}

	@Override
	public boolean performOk() {
		// save column settings
		scriptPathsBlock.saveColumnSettings();
		// save the checked scriptPaths in the tern project
		List<ITernScriptPath> scriptPaths = scriptPathsBlock
				.getTernScriptPaths();
		try {
			IDETernProject ternProject = getTernProject();
			ternProject.setScriptPaths(scriptPaths);
			ternProject.save();
		} catch (Exception e) {
			Trace.trace(Trace.SEVERE, "Error while saving tern project", e);
		}
		return super.performOk();
	}

	/**
	 * Load scriptPaths from tern project.
	 */
	private void loadScriptPaths() {
		try {
			IDETernProject ternProject = getTernProject();
			scriptPathsBlock.setTernScriptPaths(ternProject.getScriptPaths());
		} catch (CoreException e) {
			Trace.trace(Trace.SEVERE, "Error while loading scriptPaths.", e);
		}
	}

}
