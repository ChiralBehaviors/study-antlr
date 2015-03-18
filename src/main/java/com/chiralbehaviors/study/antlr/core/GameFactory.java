/**
 * Copyright (c) 2015 Chiral Behaviors, LLC, all rights reserved.
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
package com.chiralbehaviors.study.antlr.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import com.chiralbehaviors.study.antlr.grammar.FieldBaseListener;
import com.chiralbehaviors.study.antlr.grammar.FieldLexer;
import com.chiralbehaviors.study.antlr.grammar.FieldParser;

/**
 * @author hparry
 *
 */
public class GameFactory {
    public Game createGame(InputStream in) throws IOException {
        FieldLexer l = new FieldLexer(new ANTLRInputStream(in));
        FieldParser p = new FieldParser(new CommonTokenStream(l));
        p.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        final AtomicReference<String> name = new AtomicReference<>();
        final Map<String, Integer> points = new HashMap<>();
        final String[][] grid = new String[5][5];

        p.addParseListener(new FieldBaseListener() {
            int x;
            int y;

            @Override
            public void exitField(FieldParser.FieldContext ctx) {
                name.set(ctx.name.getText());
            }

            @Override
            public void exitLocation(FieldParser.LocationContext ctx) {
                x = Integer.parseInt(ctx.x.getText());
                y = Integer.parseInt(ctx.y.getText());
            }

            @Override
            public void exitBurial(FieldParser.BurialContext ctx) {
                grid[x][y] = ctx.treasure.getText();
            }

            @Override
            public void exitPoints(FieldParser.PointsContext ctx) {
                points.put(ctx.treasure.getText(), Integer.parseInt(ctx.value.getText()));
            }
        });
        p.field();

        return new Game(name.get(), points, grid);
    }
}
