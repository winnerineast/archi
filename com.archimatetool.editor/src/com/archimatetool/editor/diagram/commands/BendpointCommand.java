/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.diagram.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import com.archimatetool.model.IDiagramModelConnection;


/**
 * Bendpoint Command
 * 
 * @author Phillip Beauvoir
 */
public abstract class BendpointCommand extends Command {

    protected int index;

    protected Point location;

    protected IDiagramModelConnection connection;

    protected BendpointCommand(String label) {
        super(label);
    }

    protected int getIndex() {
        return index;
    }

    protected Point getLocation() {
        return location;
    }

    protected IDiagramModelConnection getDiagramModelConnection() {
        return connection;
    }

    public void setIndex(int i) {
        index = i;
    }

    public void setLocation(Point p) {
        location = p;
    }

    public void setDiagramModelConnection(IDiagramModelConnection connection) {
        this.connection = connection;
    }

}
