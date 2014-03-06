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
package tern.server.nodejs.angular;

import tern.TernException;
import tern.TernProject;
import tern.server.ITernServer;
import tern.server.nodejs.NodejsTernServerFactory;
import tern.server.protocol.angular.AbstractAngularControllerCompletionTest;

public class NodejsAngularControllerCompletionTest extends
		AbstractAngularControllerCompletionTest {

	@Override
	protected ITernServer createServer(TernProject project)
			throws TernException {
		return NodejsTernServerFactory.createServer(project);
	}
}
