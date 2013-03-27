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

import processing.core.PVector;

// ------------------------------------------------------------------------------
public abstract class AbstractInteractivePolygon extends AbstractInteractiveObject {
    protected float width;
    protected float height;

    public AbstractInteractivePolygon(PVector position, float width, float height) {
        super(position);
        this.width = width;
        this.height = height;
    }

    public boolean hitTest(PVector theOtherPosition) {
        return theOtherPosition.x > this.x && theOtherPosition.y > this.y
                && theOtherPosition.x <= (this.x + this.width)
                && theOtherPosition.y <= (this.y + this.height);
    }

}