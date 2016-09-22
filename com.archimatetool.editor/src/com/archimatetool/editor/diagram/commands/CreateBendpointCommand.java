/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.diagram.commands;

import com.archimatetool.model.IArchimateFactory;
import com.archimatetool.model.IDiagramModelBendpoint;


/**
 * Description
 * 
 * @author Phillip Beauvoir
 */
public class CreateBendpointCommand extends BendpointCommand implements IAnimatableCommand {
    
    private IDiagramModelBendpoint fBendpoint;
    
    public CreateBendpointCommand() {
        super(Messages.CreateBendpointCommand_0);
    }

    @Override
    public void execute() {
        fBendpoint = IArchimateFactory.eINSTANCE.createDiagramModelBendpoint();
        
        fBendpoint.setX(getLocation().x);
        fBendpoint.setY(getLocation().y);
        
        redo();
    }

    @Override
    public void undo() {
        getDiagramModelConnection().getBendpoints().remove(fBendpoint);
    }

    @Override
    public void redo() {
        getDiagramModelConnection().getBendpoints().add(getIndex(), fBendpoint);
    }
}
