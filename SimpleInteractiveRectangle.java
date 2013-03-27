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

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.KeyEvent;

public class SimpleInteractiveRectangle extends AbstractInteractiveRectangle {

    int overFillColor;
    int overStrokeColor;
    int draggingFillColor;
    int draggingStrokeColor;
    int pressedFillColor;
    int pressedStrokeColor;
    int fillColor;
    int strokeColor;

    PApplet p;

    int id = -1;

    int keyFader = 0;
    char lastKey;

    // float rollInOutFader = 0;
    // boolean moveIn = true;

    // ------------------------------------------------------------------------------
    public SimpleInteractiveRectangle(PApplet parent, int id, PVector position, float width,
            float height) {
        super(position, width, height);
        this.id = id;
        this.p = parent;

        p.println(position);

        overFillColor = p.color(255, 255, 0, 180);
        draggingFillColor = p.color(255, 0, 255, 180);
        pressedFillColor = p.color(0, 255, 0, 180);
        fillColor = p.color(127, 180);

        overStrokeColor = p.color(255, 255, 0, 255);
        draggingStrokeColor = p.color(255, 0, 255, 255);
        pressedStrokeColor = p.color(0, 255, 0, 255);
        strokeColor = p.color(127, 255);
    }

    // ------------------------------------------------------------------------------
    @Override
    public void draw() {
        p.pushStyle();

        keyFader--;
        keyFader = PApplet.constrain(keyFader, 0, 255);

        // rollInOutFader -= 0.1;
        // rollInOutFader = PApplet.constrain(rollInOutFader, 0, 1);

        p.fill(fillColor);
        p.stroke(strokeColor);

        if (isOver()) {
            p.fill(fillColor);
            p.stroke(strokeColor);
            if (isOver()) {
                p.fill(overFillColor);
                p.stroke(overStrokeColor);
                if (isDragging()) {
                    p.fill(draggingFillColor);
                    p.stroke(draggingStrokeColor);
                    if (isPressed()) {
                        p.fill(pressedFillColor);
                        p.stroke(pressedStrokeColor);
                    }
                }
            }
        }

        p.pushMatrix();

        p.translate(x, y);

        p.rect(0, 0, width, height);

        p.fill(0, 255);

        p.textAlign(CENTER, CENTER);
        p.text(id, width / 2, height / 2);

        p.fill(255,255,0, keyFader);
        p.text(lastKey, width / 2, height / 2 + p.textAscent() + p.textDescent());

        
        p.noFill();
        p.stroke(255, 0, 0);

        // if (rollInOutFader > 0) {
        // p.translate(width / 2, height / 2);
        // p.rectMode(CENTER);
        //
        // p.rect(0, 0, width * (1 + rollInOutFader), height * (1 + rollInOutFader));
        // }
        p.popMatrix();

        p.popStyle();

    }

    // ------------------------------------------------------------------------------
    @Override
    public void keyEventInside(KeyEvent evt) {
        if (evt.getAction() == KeyEvent.TYPED) {
            lastKey = evt.getKey();
            keyFader = 255;
        }
    }

    // ------------------------------------------------------------------------------
    @Override
    public void keyEventOutside(KeyEvent evt) {
        // do nothing
        // System.out.println("[" + id + "]: " + "keyEventOutside");
    }

    // ------------------------------------------------------------------------------
    @Override
    public void onMoveIn() {
        System.out.println("[" + id + "]: " + "onMoveIn");
        // rollInOutFader = 1;
        // moveIn = true;
    }

    // ------------------------------------------------------------------------------
    @Override
    public void onMoveOut() {
        System.out.println("[" + id + "]: " + "onMoveOut");
        // rollInOutFader = 1;
        // moveIn = false;
    }
    
    // ------------------------------------------------------------------------------
    public void onDraggedInside(AbstractInteractiveObject draggable) { 
        if(draggable instanceof SimpleInteractiveRectangle) {
            SimpleInteractiveRectangle d = (SimpleInteractiveRectangle)draggable;
            System.out.println("[" + id + "]: " + "onDraggedInside [" + d.id + "]");
        } else {
            System.out.println("[" + id + "]: " + "onDraggedInside [" + "?"+ "]");
        }
    }

    // ------------------------------------------------------------------------------
    public void onDraggedOutside(AbstractInteractiveObject draggable) { 
        
        
    }


}