/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.diagram.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

import com.archimatetool.editor.preferences.IPreferenceConstants;
import com.archimatetool.editor.preferences.Preferences;
import com.archimatetool.model.IConnectable;
import com.archimatetool.model.IDiagramModelConnection;
import com.archimatetool.model.IDiagramModelObject;


/**
 * A command to create a connection between two diagram objects
 * 
 * @author Phillip Beauvoir
 */
public class CreateDiagramConnectionCommand
extends Command {

    protected CreateConnectionRequest fRequest;
    
    protected IDiagramModelConnection fConnection;
    protected IConnectable fSource;
    protected IConnectable fTarget;

    /**
     * Instantiate a command that can create a connection between two shapes.
     * @param request the type of connection request
     */
    public CreateDiagramConnectionCommand(CreateConnectionRequest request) {
        fRequest = request;
        setLabel(Messages.CreateDiagramConnectionCommand_0);
    }
    
    /**
     * Set the source endpoint for the connection.
     * @param source that source endpoint
     * @throws IllegalArgumentException if source is null
     */
    public void setSource(IConnectable source) {
        if(source == null) {
            throw new IllegalArgumentException("Source connected model object cannot be null"); //$NON-NLS-1$
        }
        fSource = source;
    }

    /**
     * Set the target endpoint for the connection.
     * @param target that target endpoint
     * @throws IllegalArgumentException if target is null
     */
    public void setTarget(IConnectable target) {
        if(target == null) {
            throw new IllegalArgumentException("Target connected model object cannot be null"); //$NON-NLS-1$
        }
        fTarget = target;
    }

    @Override
    public boolean canExecute() {
        if(fTarget == null || fSource == null) {
            return false;
        }
        
        // Disallow same node connections if not enabled in Preferences
        boolean allowCircularConnection = Preferences.STORE.getBoolean(IPreferenceConstants.ALLOW_CIRCULAR_CONNECTIONS);
        return allowCircularConnection ? true : fSource != fTarget;
    }

    @Override
    public void execute() {
        // If null create new one
        if(fConnection == null) {
            fConnection = createNewConnection();
        }
        
        // Connect
        fConnection.connect(fSource, fTarget);
        
        // If it's a circular connection, add some bendpoints
        if(fConnection.getSource() == fConnection.getTarget() && fConnection.getTarget() instanceof IDiagramModelObject) {
            createCircularBendPoints((IDiagramModelObject)fConnection.getTarget());
        }
    }

    @Override
    public void redo() {
        fConnection.reconnect();
    }
    
    @Override
    public void undo() {
        fConnection.disconnect();
    }
    
    /**
     * Create a new connection from the request
     * @return The new connection
     */
    protected IDiagramModelConnection createNewConnection() {
        return (IDiagramModelConnection)fRequest.getNewObject();
    }
    
    /**
     * Adding a circular connection requires some bendpoints
     */
    protected void createCircularBendPoints(IDiagramModelObject dmo) {
        
        Rectangle bounds = new Rectangle(dmo.getBounds().getX(), dmo.getBounds().getY(), dmo.getBounds().getWidth(),
                dmo.getBounds().getHeight());
        
        CreateBendpointCommand cmd = new CreateBendpointCommand();
        cmd.setDiagramModelConnection(fConnection);
        
        Point pt = new Point(bounds.getRight().x + 40, bounds.getCenter().y);
        cmd.setLocation(pt);
        cmd.execute();
        
        pt = new Point(bounds.getRight().x + 40, bounds.getBottom().y + 40);
        cmd.setLocation(pt);
        cmd.execute();
        
        pt = new Point(bounds.getCenter().x, bounds.getBottom().y + 40);
        cmd.setLocation(pt);
        cmd.execute();
    }
    
    @Override
    public void dispose() {
        fRequest = null;
        fConnection = null;
        fSource = null;
        fTarget = null;
    }
}
