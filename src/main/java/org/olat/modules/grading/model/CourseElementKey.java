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
package org.olat.modules.grading.model;

import java.io.Serializable;

/**
 * 
 * Initial date: 30 janv. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class CourseElementKey implements Serializable {

	private static final long serialVersionUID = 5285625324903542096L;
	private final Long repositoryEntryKey;
	private final String subIdent;
	
	public CourseElementKey(Long repositoryEntryKey, String subIdent) {
		this.repositoryEntryKey = repositoryEntryKey;
		this.subIdent = subIdent;
	}

	public Long getRepositoryEntryKey() {
		return repositoryEntryKey;
	}

	public String getSubIdent() {
		return subIdent;
	}
	
	@Override
	public int hashCode() {
		return repositoryEntryKey.hashCode() + subIdent.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj instanceof CourseElementKey) {
			CourseElementKey el = (CourseElementKey)obj;
			return repositoryEntryKey != null && repositoryEntryKey.equals(el.repositoryEntryKey)
					&& subIdent != null && subIdent.equals(el.subIdent);
		}
		return false;
	}
}
