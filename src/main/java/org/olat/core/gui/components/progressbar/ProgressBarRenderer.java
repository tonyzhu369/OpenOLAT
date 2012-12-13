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
* <p>
*/ 

package org.olat.core.gui.components.progressbar;

import org.olat.core.gui.components.Component;
import org.olat.core.gui.components.ComponentRenderer;
import org.olat.core.gui.render.RenderResult;
import org.olat.core.gui.render.Renderer;
import org.olat.core.gui.render.RenderingState;
import org.olat.core.gui.render.StringOutput;
import org.olat.core.gui.render.URLBuilder;
import org.olat.core.gui.translator.Translator;
import org.olat.core.util.StringHelper;

/**
 * Initial Date: Feb 2, 2004 A <b>ChoiceRenderer </b> is
 * 
 * @author Andreas Ch. Kapp
 */
public class ProgressBarRenderer implements ComponentRenderer {

	/**
	 * This is a singleton. There must be an empty contructor for the
	 * Class.forName() call.
	 */
	public ProgressBarRenderer() {
		super();
	}

	/**
	 * @see org.olat.core.gui.render.ui.ComponentRenderer#render(org.olat.core.gui.render.Renderer,
	 *      org.olat.core.gui.render.StringOutput, org.olat.core.gui.components.Component,
	 *      org.olat.core.gui.render.URLBuilder, org.olat.core.gui.translator.Translator,
	 *      org.olat.core.gui.render.RenderResult, java.lang.String[])
	 */
	public void render(Renderer renderer, StringOutput target, Component source, URLBuilder urlBuilder, Translator translator,
			RenderResult renderResult, String[] args) {
		// Get the model object
		ProgressBar ubar = (ProgressBar) source;
		boolean renderLabels = (args == null) ? true : false;
		float percent = 100;
		if (!ubar.getIsNoMax()) percent = 100 * ubar.getActual() / ubar.getMax();
		if (percent < 0) percent = 0;
		if (percent > 100) percent = 100;
		target.append("<div class=\"b_progress\"><div class=\"b_progress_bar\" style=\"width:")
			.append(ubar.getWidth())
			.append("px;\"><div style=\"width:")
			.append(Math.round(percent * ubar.getWidth() / 100))
			.append("px\" title=\"")
			.append(Math.round(percent * ubar.getWidth() / 100))
			.append("%\"></div></div>");
		if (renderLabels) {
			target.append("<div class=\"b_progress_label\">");
			if (ubar.isPercentagesEnabled()) {
				target.append(Math.round(percent));
				target.append("% (");
			}
			target.append(Math.round(ubar.getActual()));
			target.append("/");
			if (ubar.getIsNoMax()) target.append("-");
			else target.append(Math.round(ubar.getMax()));
			target.append(" ");
			target.append(ubar.getUnitLabel());
			if (ubar.isPercentagesEnabled()) {
				target.append(")");				
			}
			target.append("</div>");				
		}
		String info = ubar.getInfo();
		if(StringHelper.containsNonWhitespace(info)) {
			target.append("<div class=\"b_progress_label\">")
				.append(info)
				.append("</div>");
		}
		target.append("</div>");
	}

	/**
	 * @see org.olat.core.gui.render.ui.ComponentRenderer#renderBodyOnLoadJSFunctionCall(org.olat.core.gui.render.Renderer,
	 *      org.olat.core.gui.render.StringOutput, org.olat.core.gui.components.Component)
	 */
	public void renderBodyOnLoadJSFunctionCall(Renderer renderer, StringOutput sb, Component source, RenderingState rstate) {
	//  
	}

	/**
	 * @see org.olat.core.gui.render.ui.ComponentRenderer#renderHeaderIncludes(org.olat.core.gui.render.Renderer,
	 *      org.olat.core.gui.render.StringOutput, org.olat.core.gui.components.Component,
	 *      org.olat.core.gui.render.URLBuilder, org.olat.core.gui.translator.Translator)
	 */
	public void renderHeaderIncludes(Renderer renderer, StringOutput target, Component source, URLBuilder url, Translator translator,
			RenderingState rstate) {
	//  
	}

}