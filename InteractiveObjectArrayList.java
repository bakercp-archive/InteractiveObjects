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

import java.util.Vector;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

// ------------------------------------------------------------------------------
public class InteractiveObjectArrayList<T extends AbstractInteractiveObject> extends Vector<T>
        implements PConstants {

    T currentInteractiveObject = null;

    PVector dragStart = null;
    PVector dragOffset = null;

    PApplet parent = null;

    // ------------------------------------------------------------------------------
    public InteractiveObjectArrayList(PApplet parent) {
        parent.registerMethod("mouseEvent", this); // so we will get mouse updates
        parent.registerMethod("keyEvent", this); // so we will get mouse updates
    }

    // ------------------------------------------------------------------------------
    public void draw() {
        // render in reverse
        for (int i = size() - 1; i >= 0; i--) {
            get(i).draw();
        }
    }

    // ------------------------------------------------------------------------------
    public void mouseDragged(PVector mouse) {
        if (currentInteractiveObject != null) {
            PVector newHandlePosition = PVector.sub(mouse, dragOffset);
            currentInteractiveObject.set(newHandlePosition);
            currentInteractiveObject.setIsDragging(true);

            T handler = null;

            for (T iObject : this) {
                if (handler == null && iObject.hitTest(mouse)) { // re-evaluate
                    handler = iObject;
                    handler.onDraggedInside(currentInteractiveObject);
                } else {
                    iObject.onDraggedOutside(currentInteractiveObject);
                }
            }
        } else {

        }
    }

    // ------------------------------------------------------------------------------
    public void mouseMoved(PVector mouse) {
        T handler = null;

        for (T iObject : this) {
            boolean bDidHit = iObject.hitTest(mouse);
            boolean bWasAlreadyHandled = (handler != null);

            if (iObject.isOver()) { // was it over already?
                // if it didn't hit or was already handled,
                // then set isOver to false and move out.
                if (!bDidHit || bWasAlreadyHandled) {
                    iObject.setIsOver(false);
                    iObject.onMoveOut();
                } else {
                    handler = iObject;
                }
            } else {
                // it was not over already
                if (bDidHit && !bWasAlreadyHandled) {
                    handler = iObject;
                    iObject.setIsOver(true);
                    iObject.onMoveIn();
                }
            }
        }
    }

    // public void onClick() { }
    // public void onRelease() { }
    //
    // public void onPressOutside() { }
    // public void onReleaseOutside() { }

    // public void onMoveIn() { }
    // public void onMouseOut() { }

    // ------------------------------------------------------------------------------
    public void mousePressed(PVector mouse) {
        boolean foundOne = false;
        for (T iObject : this) {
            iObject.setIsPressed(false); // reset
            iObject.setIsOver(false); // reset
            if (!foundOne) {
                if (iObject.hitTest(mouse)) {
                    foundOne = true;
                    iObject.setIsPressed(true); // reset
                    iObject.setIsOver(true); // reset
                    currentInteractiveObject = iObject; // set the interactive object
                    dragStart = mouse.get(); // get the mouse vector (copy)
                    dragOffset = PVector.sub(dragStart, iObject);
                }
            }
        }

        if (foundOne) {
            remove(currentInteractiveObject); // remove it and move it to the top of the stack
            add(0, currentInteractiveObject); // add to the front
        }
    }

    // ------------------------------------------------------------------------------
    public void mouseDoubleClicked(PVector mouse) {
        boolean foundOne = false;
        for (T iObject : this) {
            if (!foundOne) {
                if (iObject.hitTest(mouse)) {
                    foundOne = true;
                    iObject.onDoubleClick();
                }
            }
        }
    }

    // ------------------------------------------------------------------------------
    public void mouseReleased(PVector mouse) {
        boolean foundOne = false;
        for (T iObject : this) {
            iObject.setIsPressed(false);  // reset
            iObject.setIsOver(false);     // reset
            iObject.setIsDragging(false); // reset

            if (!foundOne) {
                if (iObject.hitTest(mouse)) {
                    iObject.setIsOver(true);
                }
            }
        }

        currentInteractiveObject = null;
        dragOffset = null;
    }

    // ------------------------------------------------------------------------------
    public void keyEvent(KeyEvent event) {
        for (int i = 0; i < size(); i++) {
            if (i == 0) {
                get(i).keyEventInside(event);
            } else {
                get(i).keyEventOutside(event);
            }
        }
    }

    // ------------------------------------------------------------------------------
    public void mouseEvent(MouseEvent event) {

        PVector mousePosition = new PVector(event.getX(), event.getY());

        // double-click!
        if (event.getClickCount() == 2) {
            mouseDoubleClicked(mousePosition);
            return;
        }

        switch (event.getAction()) {
        case MouseEvent.PRESSED:
            mousePressed(mousePosition);
            break;
        case MouseEvent.RELEASED:
            mouseReleased(mousePosition);
            break;
        case MouseEvent.CLICKED:
            mousePressed(mousePosition);
            break;
        case MouseEvent.DRAGGED:
            mouseDragged(mousePosition);
            break;
        case MouseEvent.MOVED:
            mouseMoved(mousePosition);
            break;
        }
    }
}
