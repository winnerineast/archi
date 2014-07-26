package com.archimatetool.editor.diagram.directedit;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.archimatetool.editor.diagram.figures.connections.IDiagramConnectionFigure;
import com.archimatetool.editor.utils.StringUtils;
import com.archimatetool.model.IFontAttribute;
import com.archimatetool.model.INameable;
import com.archimatetool.model.ITextContent;



/**
 * Multiline Text Direct Edit Manager
 * 
 * @author Phillip Beauvoir
 */
public class MultiLineTextDirectEditManager extends AbstractDirectEditManager {
    
    public MultiLineTextDirectEditManager(GraphicalEditPart source) {
        super(source, MultiLineCellEditor.class, null);
        setLocator(new MultiLineCellEditorLocator());
    }

    @Override
    protected CellEditor createCellEditorOn(Composite composite) {
        Object model = getEditPart().getModel();
        
        int alignment = SWT.CENTER;
        
        if(model instanceof IFontAttribute) {
            alignment = ((IFontAttribute)model).getTextAlignment();
            if(alignment == IFontAttribute.TEXT_ALIGNMENT_CENTER) {
                alignment = SWT.CENTER;
            }
            else if(alignment == IFontAttribute.TEXT_ALIGNMENT_RIGHT) {
                alignment = SWT.RIGHT;
            }
            else {
                alignment = SWT.LEFT;
            }
        }
        
        return new MultiLineCellEditor(composite, alignment);
    }

    @Override
    protected void initCellEditor() {
        super.initCellEditor();
        
        Text textControl = (Text)getCellEditor().getControl();
        Object model = getEditPart().getModel();
        
        if(model instanceof ITextContent) {
            String value = ((ITextContent)model).getContent();
            getCellEditor().setValue(StringUtils.safeString(value));
        }
        else if(model instanceof INameable) {
            String value = ((INameable)model).getName();
            getCellEditor().setValue(StringUtils.safeString(value));
        }

        IFigure figure = getEditPart().getFigure();
        textControl.setFont(figure.getFont());
    }

    /**
     * CellEditorLocator
     */
    class MultiLineCellEditorLocator implements CellEditorLocator {
        public void relocate(CellEditor celleditor) {
            IFigure figure = getEditPart().getFigure();
            Text text = (Text)celleditor.getControl();
            
            // Connection
            if(figure instanceof IDiagramConnectionFigure) {
                IFigure label = ((IDiagramConnectionFigure)figure).getConnectionLabel();
                Rectangle rect = label.getBounds().getCopy();
                label.translateToAbsolute(rect);
                text.setBounds(rect.x, rect.y, Math.max(150, rect.width + 30), Math.max(40, rect.height));
            }
            // Other
            else {
                Rectangle rect = figure.getBounds().getCopy();
                figure.translateToAbsolute(rect);
                text.setBounds(rect.x + 5, rect.y + 5, rect.width, rect.height);
            }
        }
    }
    
    class MultiLineCellEditor extends TextCellEditor {
        public MultiLineCellEditor(Composite composite, int style) {
            super(composite, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | style);
        }
    }

}
