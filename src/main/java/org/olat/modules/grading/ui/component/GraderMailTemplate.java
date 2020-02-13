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
package org.olat.modules.grading.ui.component;

import java.util.Date;
import java.util.Locale;

import org.apache.velocity.VelocityContext;
import org.olat.core.helpers.Settings;
import org.olat.core.id.Identity;
import org.olat.core.id.Preferences;
import org.olat.core.id.context.BusinessControlFactory;
import org.olat.core.util.Formatter;
import org.olat.core.util.StringHelper;
import org.olat.core.util.i18n.I18nManager;
import org.olat.core.util.mail.MailTemplate;
import org.olat.course.nodes.CourseNode;
import org.olat.repository.RepositoryEntry;

/**
 * 
 * Initial date: 29 janv. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class GraderMailTemplate extends MailTemplate {
	
	private Date assessmentDate;
	private RepositoryEntry entry;
	private CourseNode courseNode;
	private RepositoryEntry referenceEntry;
	
	private String taxonomyLevel;
	private String taxonomyLevelPath;
	
	public GraderMailTemplate(String subject, String body) {
		super(subject, body, null);
	}
	
	public GraderMailTemplate(RepositoryEntry entry, CourseNode courseNode, RepositoryEntry referenceEntry) {
		super(null, null, null);
		this.entry = entry;
		this.courseNode = courseNode;
		this.referenceEntry = referenceEntry;
	}
	
	public GraderMailTemplate(String subject, String body, RepositoryEntry entry, CourseNode courseNode, RepositoryEntry referenceEntry) {
		super(subject, body, null);
		this.entry = entry;
		this.courseNode = courseNode;
		this.referenceEntry = referenceEntry;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public RepositoryEntry getEntry() {
		return entry;
	}

	public void setEntry(RepositoryEntry entry) {
		this.entry = entry;
	}

	public CourseNode getCourseNode() {
		return courseNode;
	}

	public void setCourseNode(CourseNode courseNode) {
		this.courseNode = courseNode;
	}

	public RepositoryEntry getReferenceEntry() {
		return referenceEntry;
	}

	public void setReferenceEntry(RepositoryEntry referenceEntry) {
		this.referenceEntry = referenceEntry;
	}

	public String getTaxonomyLevel() {
		return taxonomyLevel;
	}

	public void setTaxonomyLevel(String taxonomyLevel) {
		this.taxonomyLevel = taxonomyLevel;
	}

	public String getTaxonomyLevelPath() {
		return taxonomyLevelPath;
	}

	public void setTaxonomyLevelPath(String taxonomyLevelPath) {
		this.taxonomyLevelPath = taxonomyLevelPath;
	}

	@Override
	public void putVariablesInMailContext(VelocityContext vContext, Identity recipient) {
		if(entry != null) {
			String url = Settings.getServerContextPathURI() + "/url/RepositoryEntry/" + entry.getKey();
			putVariablesInMailContext(vContext, "courseUrl", url);
			putVariablesInMailContext(vContext, "courseName", entry.getDisplayname());
			putVariablesInMailContext(vContext, "courseTitle", entry.getDisplayname());
			if(StringHelper.containsNonWhitespace(entry.getExternalRef())) {
				putVariablesInMailContext(vContext, "courseReference", entry.getExternalRef());
			}
		}
		
		if(courseNode != null) {
			putVariablesInMailContext(vContext, "courseElementTitle", courseNode.getLongTitle());
			putVariablesInMailContext(vContext, "courseElementShortTitle", courseNode.getShortTitle());
		}
		
		if(referenceEntry != null) {
			putVariablesInMailContext(vContext, "testName", referenceEntry.getDisplayname());
			putVariablesInMailContext(vContext, "testTitle", referenceEntry.getDisplayname());
			if(StringHelper.containsNonWhitespace(referenceEntry.getExternalRef())) {
				putVariablesInMailContext(vContext, "testReference", referenceEntry.getExternalRef());
			}
		}
		
		if(StringHelper.containsNonWhitespace(taxonomyLevel)) {
			putVariablesInMailContext(vContext, "testTaxonomie", taxonomyLevel);
			putVariablesInMailContext(vContext, "testTaxonomy", taxonomyLevel);
		}
		
		if(StringHelper.containsNonWhitespace(taxonomyLevelPath)) {
			putVariablesInMailContext(vContext, "testTaxonomiePath", taxonomyLevelPath);
			putVariablesInMailContext(vContext, "testTaxonomyPath", taxonomyLevelPath);
		}
		
		if(assessmentDate != null) {
			Preferences prefs = recipient.getUser().getPreferences();
			Locale locale = I18nManager.getInstance().getLocaleOrDefault(prefs.getLanguage());
			String assignmentDateString = Formatter.getInstance(locale).formatDate(assessmentDate);
			putVariablesInMailContext(vContext, "assessmentDate", assignmentDateString);
		}
		
		String correctionUrl = BusinessControlFactory.getInstance()
				.getURLFromBusinessPathString("[CoachSite:0][Grading:0][Assignments:0]");
		putVariablesInMailContext(vContext, "correctionUrl", correctionUrl);
	}
	
	private void putVariablesInMailContext(VelocityContext vContext, String key, String value) {
		vContext.put(key, value);
		vContext.put(key.toLowerCase(), value);
	}

}
