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

import java.util.Date;

import org.olat.basesecurity.IdentityRef;

/**
 * 
 * Initial date: 29 janv. 2020<br>
 * @author srosse, stephane.rosse@frentix.com, http://www.frentix.com
 *
 */
public class GraderStatistics implements IdentityRef {
	
	private final Long identityKey;
	private final long total;
	private final long done;
	private final long open;
	private final long overdue;
	private final Date oldest;
	private final long timeInSeconds;
	
	public GraderStatistics(Long identityKey, long total, long done, long open, long overdue, Date oldest, long timeInSeconds) {
		this.identityKey = identityKey;
		this.total = total;
		this.done = done;
		this.open = open;
		this.overdue = overdue;
		this.oldest = oldest;
		this.timeInSeconds = timeInSeconds;
	}
	
	@Override
	public Long getKey() {
		return identityKey;
	}
	
	public long getRecordedTimeInSeconds() {
		return timeInSeconds;
	}
	
	public long getTotalAssignments() {
		return total;
	}
	
	public long getNumOfDoneAssignments() {
		return done;
	}
	
	public long getNumOfOpenAssignments() {
		return open;
	}
	
	public long getNumOfOverdueAssignments() {
		return overdue;
	}
	
	public Date getOldestOpenAssignment() {
		return oldest;
	}
}
