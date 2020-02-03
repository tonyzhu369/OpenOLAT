/**
 * <a href="http://www.openolat.org">
 * OpenOLAT - Online Learning and Training</a><br>
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); <br>
 * you may not use this file except in compliance with the License.<br>
 * You may obtain a copy of the License at the
 * <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache homepage</a>
 * <p>
 * Unless required by applicable law or agreed to in writing,<br>
 * software distributed under the License is distributed on an "AS IS" BASIS, <br>
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. <br>
 * See the License for the specific language governing permissions and <br>
 * limitations under the License.
 * <p>
 * Initial code contributed and copyrighted by<br>
 * frentix GmbH, http://www.frentix.com
 * <p>
 */
package org.olat.course.nodes.dialog.security;

import org.olat.core.commons.services.notifications.SubscriptionContext;
import org.olat.course.nodes.dialog.DialogSecurityCallback;

/**
 * 
 * Initial date: 2 Feb 2020<br>
 * @author uhensler, urs.hensler@frentix.com, http://www.frentix.com
 *
 */
class SubscriptionContextCallback implements DialogSecurityCallback {
	
	private final DialogSecurityCallback secCallback;
	private final SubscriptionContext subsContext;

	SubscriptionContextCallback(DialogSecurityCallback secCallback, SubscriptionContext subsContext) {
		this.secCallback = secCallback;
		this.subsContext = subsContext;
	}

	@Override
	public boolean mayUsePseudonym() {
		return secCallback.mayUsePseudonym();
	}

	@Override
	public boolean mayOpenNewThread() {
		return secCallback.mayOpenNewThread();
	}

	@Override
	public boolean mayReplyMessage() {
		return secCallback.mayReplyMessage();
	}

	@Override
	public boolean mayEditOwnMessage() {
		return secCallback.mayEditOwnMessage();
	}

	@Override
	public boolean mayDeleteOwnMessage() {
		return secCallback.mayDeleteOwnMessage();
	}

	@Override
	public boolean mayEditMessageAsModerator() {
		return secCallback.mayEditMessageAsModerator();
	}

	@Override
	public boolean mayDeleteMessageAsModerator() {
		return secCallback.mayDeleteMessageAsModerator();
	}

	@Override
	public boolean mayArchiveForum() {
		return secCallback.mayArchiveForum();
	}

	@Override
	public boolean mayFilterForUser() {
		return secCallback.mayFilterForUser();
	}

	@Override
	public SubscriptionContext getSubscriptionContext() {
		return subsContext;
	}

	@Override
	public boolean canCopyFile() {
		return secCallback.canCopyFile();
	}

}
