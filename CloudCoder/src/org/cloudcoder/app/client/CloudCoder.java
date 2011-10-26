// CloudCoder - a web-based pedagogical programming environment
// Copyright (C) 2011, Jaime Spacco <jspacco@knox.edu>
// Copyright (C) 2011, David H. Hovemeyer <dhovemey@ycp.edu>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Affero General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Affero General Public License for more details.
//
// You should have received a copy of the GNU Affero General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.cloudcoder.app.client;

import org.cloudcoder.app.client.page.CloudCoderPage;
import org.cloudcoder.app.client.page.CoursesAndProblemsPage;
import org.cloudcoder.app.client.page.LoginPage;
import org.cloudcoder.app.shared.util.DefaultSubscriptionRegistrar;
import org.cloudcoder.app.shared.util.Publisher;
import org.cloudcoder.app.shared.util.Subscriber;
import org.cloudcoder.app.shared.util.SubscriptionRegistrar;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * CloudCoder entry point class.
 */
public class CloudCoder implements EntryPoint, Subscriber {
	private Session session;
	private SubscriptionRegistrar subscriptionRegistrar;
	private CloudCoderPage currentPage;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		session = new Session();
		subscriptionRegistrar = new DefaultSubscriptionRegistrar();

		// Subscribe to all Session events
		session.subscribeToAll(Session.Event.values(), this, subscriptionRegistrar);

		changePage(new LoginPage());
	}
	
	private void changePage(CloudCoderPage page) {
		if (currentPage != null) {
			currentPage.deactivate();
			RootLayoutPanel.get().remove(currentPage);
		}
		page.setSession(session);
		page.activate();
		currentPage = page;
		RootLayoutPanel.get().add(page);
	}
	
	@Override
	public void eventOccurred(Object key, Publisher publisher, Object hint) {
		if (key == Session.Event.LOGIN) {
//			Window.alert("Successful login!");
			changePage(new CoursesAndProblemsPage());
		}
		
	}
}