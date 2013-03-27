/*==============================================================================
 
 Copyright (c) 2009-2013 Christopher Baker <http://christopherbaker.net>
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 
 =============================================================================*/

package net.christopherbaker.interactive;

import processing.core.PConstants;
import processing.core.PVector;
import processing.event.KeyEvent;

// ------------------------------------------------------------------------------
public abstract class AbstractInteractiveObject extends PVector implements PConstants {
    private boolean bIsOver = false;
    private boolean bIsDragging = false;
    private boolean bIsPressed = false;

    public AbstractInteractiveObject(PVector position) {
        super(position.x, position.y, position.z);
    }
    
    public abstract void draw();

    public abstract boolean hitTest(PVector position);
    
    public final void setIsOver(boolean bIsOver) {
        this.bIsOver = bIsOver;
    }
    
    public void keyEventInside(KeyEvent evt) { }
    public void keyEventOutside(KeyEvent evt) { }
    
    public void onDoubleClick() { }
    
//    public void onClick() { }
//    public void onRelease() { }

//    public void onPressOutside() { }
//    public void onReleaseOutside() { }

    public void onMoveIn() { }
    public void onMoveOut() { }
//    
//    public void onDragStart() { }
//    public void onDragEnd() { }
//    public void onDragCancelled() { }
    
    public void onDraggedInside(AbstractInteractiveObject draggable) { }
    public void onDraggedOutside(AbstractInteractiveObject draggable) { }
//    public void onDroppedInside(AbstractInteractiveObject draggable) { }
//    public void onDroppedOutside(AbstractInteractiveObject draggable) { }
//    
//    public void onDoppedOn(AbstractInteractiveObject draggable) { }
    
    public final boolean isOver() {
        return bIsOver;
    }

    public final boolean isDragging() {
        return bIsDragging;
    }

    public final void setIsDragging(boolean bIsDragging) {
        this.bIsDragging = bIsDragging;
    }

    public final boolean isPressed() {
        return bIsPressed;
    }
    
    public final void setIsPressed(boolean bIsPressed) {
        this.bIsPressed = bIsPressed;
    }

}
