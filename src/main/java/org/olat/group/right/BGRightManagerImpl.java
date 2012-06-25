/**
* OLAT - Online Learning and Training<br>
* http://www.olat.org
* <p>
* Licensed under the Apache License, Version 2.0 (the "License"); <br>
* you may not use this file except in compliance with the License.<br>
* You may obtain a copy of the License at
* <p>
* http://www.apache.org/licenses/LICENSE-2.0
* <p>
* Unless required by applicable law or agreed to in writing,<br>
* software distributed under the License is distributed on an "AS IS" BASIS, <br>
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
* See the License for the specific language governing permissions and <br>
* limitations under the License.
* <p>
* Copyright (c) since 2004 at Multimedia- & E-Learning Services (MELS),<br>
* University of Zurich, Switzerland.
* <hr>
* <a href="http://www.openolat.org">
* OpenOLAT - Online Learning and Training</a><br>
* This file has been modified by the OpenOLAT community. Changes are licensed
* under the Apache 2.0 license as the original file.
*/

package org.olat.group.right;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.olat.basesecurity.BaseSecurity;
import org.olat.basesecurity.Policy;
import org.olat.core.id.Identity;
import org.olat.core.logging.AssertException;
import org.olat.core.manager.BasicManager;
import org.olat.group.BusinessGroup;
import org.olat.group.manager.BusinessGroupRelationDAO;
import org.olat.resource.OLATResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:<BR>
 * TODO: Class Description for BGRightManagerImpl Initial Date: Aug 24, 2004
 * 
 * @author gnaegi
 */
@Service("rightManager")
public class BGRightManagerImpl extends BasicManager implements BGRightManager {

	@Autowired
	private BaseSecurity securityManager;
	@Autowired
	private BusinessGroupRelationDAO businessGroupRelationDAO;


	/**
	 * @see org.olat.group.right.BGRightManager#addBGRight(java.lang.String,
	 *      org.olat.group.BusinessGroup)
	 */
	public void addBGRight(String bgRight, BusinessGroup rightGroup) {
		if (bgRight.indexOf(BG_RIGHT_PREFIX) == -1) throw new AssertException("Groups rights must start with prefix '" + BG_RIGHT_PREFIX
				+ "', but given right is ::" + bgRight);
		if (BusinessGroup.TYPE_RIGHTGROUP.equals(rightGroup.getType())) {
			List<OLATResource> resources = businessGroupRelationDAO.findResources(Collections.singletonList(rightGroup), 0, -1);
			for(OLATResource resource:resources) {
				securityManager.createAndPersistPolicy(rightGroup.getPartipiciantGroup(), bgRight, resource);
			}
		} else {
			throw new AssertException("Only right groups can have bg rights, but type was ::" + rightGroup.getType());
		}
	}

	/**
	 * @see org.olat.group.right.BGRightManager#removeBGRight(java.lang.String,
	 *      org.olat.group.BusinessGroup)
	 */
	public void removeBGRight(String bgRight, BusinessGroup rightGroup) {
		if (BusinessGroup.TYPE_RIGHTGROUP.equals(rightGroup.getType())) {
			List<OLATResource> resources = businessGroupRelationDAO.findResources(Collections.singletonList(rightGroup), 0, -1);
			for(OLATResource resource:resources) {
				securityManager.deletePolicy(rightGroup.getPartipiciantGroup(), bgRight, resource);
			}
		} else {
			throw new AssertException("Only right groups can have bg rights, but type was ::" + rightGroup.getType());
		}
	}

	/**
	 * @see org.olat.group.right.BGRightManager#hasBGRight(java.lang.String,
	 *      org.olat.group.BusinessGroup)
	 */
	/*
	 * public boolean hasBGRight(String bgRight, BusinessGroup rightGroup) { if
	 * (BusinessGroup.TYPE_RIGHTGROUP.equals(rightGroup.getType())) { Manager secm =
	 * ManagerFactory.getManager(); return
	 * secm.isGroupPermittedOnResourceable(rightGroup.getPartipiciantGroup(),
	 * bgRight, rightGroup.getGroupContext()); } throw new AssertException("Only
	 * right groups can have bg rights, but type was ::" + rightGroup.getType()); }
	 */

	/**
	 * @see org.olat.group.right.BGRightManager#hasBGRight(java.lang.String,
	 *      org.olat.core.id.Identity, org.olat.group.context.BGContext)
	 */
	public boolean hasBGRight(String bgRight, Identity identity, OLATResource resource) {
		String groupType = "";
		if (BusinessGroup.TYPE_RIGHTGROUP.equals(groupType)) {
			return securityManager.isIdentityPermittedOnResourceable(identity, bgRight, resource);
		}
		throw new AssertException("Only right groups can have bg rights, but type was ::" + groupType);
	}

	/**
	 * @see org.olat.group.right.BGRightManager#findBGRights(org.olat.group.BusinessGroup)
	 */
	public List<String> findBGRights(BusinessGroup rightGroup) {
		List<Policy> results = securityManager.getPoliciesOfSecurityGroup(rightGroup.getPartipiciantGroup());
		// filter all business group rights permissions. group right permissions
		// start with bgr.
		List<String> rights = new ArrayList<String>();
		for (Policy rightPolicy:results) {
			String right = rightPolicy.getPermission();
			if (right.indexOf(BG_RIGHT_PREFIX) == 0) rights.add(right);
		}
		return rights;
	}
}
