/*******************************************************************************
 * Copyright (c) 2005 Jean-Michel Lemieux, Jeff McAffer and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Hyperbola is an RCP application developed for the book
 *     Eclipse Rich Client Platform - 
 *         Designing, Coding, and Packaging Java Applications
 * See http://eclipsercp.org
 *
 * Contributors:
 *     Jean-Michel Lemieux and Jeff McAffer - initial API and implementation
 *******************************************************************************/
package org.eclipse.hyperbola;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipsercp.hyperbola.model.Session;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

public class DebugConsole extends MessageConsole {

    private MessageConsoleStream outMessageStream;

    private MessageConsoleStream inMessageStream;

    private PacketListener outListener = new PacketListener() {
        public void processPacket(Packet arg0) {
            outMessageStream.println(arg0.toXML());
        }
    };

    private PacketListener inListener = new PacketListener() {
        public void processPacket(Packet arg0) {
            inMessageStream.println(arg0.toXML());
        }
    };

    public DebugConsole() {
        super("XMPP Debug", null);
        this.outMessageStream = newMessageStream();
        this.outMessageStream.setColor(Display.getCurrent().getSystemColor(
                SWT.COLOR_BLUE));
        this.inMessageStream = newMessageStream();
        this.inMessageStream.setColor(Display.getCurrent().getSystemColor(
                SWT.COLOR_RED));

        Session.getInstance().getConnection().addPacketWriterListener(outListener, null);
        Session.getInstance().getConnection().addPacketListener(inListener, null);
    }

    protected void dispose() {
        Session.getInstance().getConnection().removePacketWriterListener(outListener);
        Session.getInstance().getConnection().removePacketListener(inListener);
    }
}
