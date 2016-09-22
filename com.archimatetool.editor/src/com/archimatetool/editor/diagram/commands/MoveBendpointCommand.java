/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.diagram.commands;

import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IDiagramModelBendpoint;


/**
 * Move Bendpoint Command
 * 
 * @author Phillip Beauvoir
 */
public class MoveBendpointCommand extends BendpointCommand implements IAnimatableCommand {

    private IDiagramModelBendpoint fOldBendpoint, fNewBendpoint;
    
    public MoveBendpointCommand() {
        super(Messages.MoveBendpointCommand_0);
    }

    @Override
    public void execute() {
        fOldBendpoint = getDiagramModelConnection().getBendpoints().get(getIndex());
        
        fNewBendpoint = IArchimateFactory.eINSTANCE.createDiagramModelBendpoint();
        
        fNewBendpoint.setX(getLocation().x);
        fNewBendpoint.setY(getLocation().y);
        
        redo();
    }

    @Override
    public void undo() {
        getDiagramModelConnection().getBendpoints().set(getIndex(), fOldBendpoint);
    }

    @Override
    public void redo() {
        getDiagramModelConnection().getBendpoints().set(getIndex(), fNewBendpoint);
    }
}
