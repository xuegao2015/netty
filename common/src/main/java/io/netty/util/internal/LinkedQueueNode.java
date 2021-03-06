/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.netty.util.internal;

/**
 * Forked from <a href="https://github.com/JCTools/JCTools">JCTools</a>.
 */
final class LinkedQueueNode<E> {
    private static final long NEXT_OFFSET;
    static {
        try {
            NEXT_OFFSET = PlatformDependent0.objectFieldOffset(LinkedQueueNode.class.getDeclaredField("next"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    private E value;
    private volatile LinkedQueueNode<E> next;

    LinkedQueueNode() {
        this(null);
    }

    LinkedQueueNode(E val) {
        spValue(val);
    }

    /**
     * Gets the current value and nulls out the reference to it from this node.
     *
     * @return value
     */
    public E getAndNullValue() {
        E temp = lpValue();
        spValue(null);
        return temp;
    }

    public E lpValue() {
        return value;
    }

    public void spValue(E newValue) {
        value =  newValue;
    }

    public void soNext(LinkedQueueNode<E> n) {
        PlatformDependent0.putOrderedObject(this, NEXT_OFFSET, n);
    }

    public LinkedQueueNode<E> lvNext() {
        return next;
    }
}
