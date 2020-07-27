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
package org.olat.ims.qti21.ui;

import java.util.Collections;
import java.util.List;

import org.olat.core.commons.persistence.DB;
import org.olat.core.gui.UserRequest;
import org.olat.core.gui.components.form.flexible.FormItem;
import org.olat.core.gui.components.form.flexible.FormItemContainer;
import org.olat.core.gui.components.form.flexible.elements.FormLink;
import org.olat.core.gui.components.form.flexible.impl.FormBasicController;
import org.olat.core.gui.components.form.flexible.impl.FormEvent;
import org.olat.core.gui.components.form.flexible.impl.FormLayoutContainer;
import org.olat.core.gui.components.link.Link;
import org.olat.core.gui.control.Controller;
import org.olat.core.gui.control.Event;
import org.olat.core.gui.control.WindowControl;
import org.olat.course.nodes.IQTESTCourseNode;
import org.olat.course.run.userview.UserCourseEnvironment;
import org.olat.ims.qti21.AssessmentTestSession;
import org.olat.ims.qti21.QTI21Service;
import org.olat.modules.assessment.Role;
import org.olat.repository.RepositoryEntry;
import org.olat.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * Initial date: 27 juil. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class ConfirmAssessmentTestSessionRevalidationController extends FormBasicController {
	
	private FormLink revalidateButton;
	
	private AssessmentTestSession session;
	private final IQTESTCourseNode courseNode;
	private final boolean canUpdateAssessmentEntry;
	private final UserCourseEnvironment assessedUserCourseEnv;
	
	@Autowired
	private DB dbInstance;
	@Autowired
	private QTI21Service qtiService;
	@Autowired
	private UserManager userManager;
	
	public ConfirmAssessmentTestSessionRevalidationController(UserRequest ureq, WindowControl wControl,
			AssessmentTestSession session, IQTESTCourseNode courseNode, UserCourseEnvironment assessedUserCourseEnv) {
		super(ureq, wControl, "confirm_reval_test_session");
		this.session = session;
		this.courseNode = courseNode;
		this.assessedUserCourseEnv = assessedUserCourseEnv;
		canUpdateAssessmentEntry = canBeNextLastSession();
		initForm(ureq);
	}

	@Override
	protected void initForm(FormItemContainer formLayout, Controller listener, UserRequest ureq) {
		if(formLayout instanceof FormLayoutContainer) {
			String fullname = userManager.getUserDisplayName(session.getIdentity());
			String text = translate("revalidate.test.confirm.text", new String[]{ fullname });
			((FormLayoutContainer) formLayout).contextPut("msg", text);
		}

		uifactory.addFormCancelButton("cancel", formLayout, ureq, getWindowControl());
		if(canUpdateAssessmentEntry) {
			uifactory.addFormSubmitButton("revalidate.overwrite", formLayout);
			revalidateButton = uifactory.addFormLink("revalidate", formLayout, Link.BUTTON);
		} else {
			uifactory.addFormSubmitButton("revalidate", formLayout);
		}
	}

	@Override
	protected void doDispose() {
		//
	}

	@Override
	protected void formOK(UserRequest ureq) {
		doInvalidateSession(ureq, canUpdateAssessmentEntry);
	}
	
	@Override
	protected void formInnerEvent(UserRequest ureq, FormItem source, FormEvent event) {
		if(revalidateButton == source) {
			doInvalidateSession(ureq, false);
		}
		super.formInnerEvent(ureq, source, event);
	}

	@Override
	protected void formCancelled(UserRequest ureq) {
		fireEvent(ureq, Event.CANCELLED_EVENT);
	}

	private void doInvalidateSession(UserRequest ureq, boolean updateEntryResults) {
		session.setCancelled(false);
		session = qtiService.updateAssessmentTestSession(session);
		dbInstance.commit();
		if(updateEntryResults) {
			courseNode.promoteAssessmentTestSession(session, assessedUserCourseEnv, getIdentity(), Role.coach);
		}
		fireEvent(ureq, Event.CHANGED_EVENT);
	}
	
	private boolean canBeNextLastSession() {
		RepositoryEntry courseEntry = assessedUserCourseEnv.getCourseEnvironment().getCourseGroupManager().getCourseEntry();
		List<AssessmentTestSession> sessions = qtiService.getAssessmentTestSessions(courseEntry, courseNode.getIdent(),
				assessedUserCourseEnv.getIdentityEnvironment().getIdentity(), true);
		if(sessions.isEmpty()) {
			return true;
		}
		
		sessions.add(session);
		Collections.sort(sessions, new AssessmentTestSessionComparator(false));
		return session.equals(sessions.get(0));
	}
}
