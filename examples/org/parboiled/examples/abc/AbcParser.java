/*
 * Copyright (C) 2009 Mathias Doenitz
 *
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

package org.parboiled.examples.abc;

import org.parboiled.Actions;
import org.parboiled.BaseParser;
import org.parboiled.Rule;

/**
 * A parser for the classic non-context free language example { a^n b^n c^n : n >= 1 }
 * S ← &(A c) a+ B !(a|b|c)
 * A ← a A? b
 * B ← b B? c
 */
@SuppressWarnings({"InfiniteRecursion"})
public class AbcParser extends BaseParser<Object, Actions<Object>> {

    public AbcParser(Actions<Object> actions) {
        super(actions);
    }

    public Rule S() {
        return sequence(
                test(sequence(A(), 'c')),
                oneOrMore('a'),
                B(),
                testNot(firstOf('a', 'b', 'c'))
        );
    }

    public Rule A() {
        return sequence('a', optional(A()), 'b');
    }

    public Rule B() {
        return sequence('b', optional(B()), 'c');
    }

}
