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

import java.util.Map;
import java.util.Scanner;

/**
 * @author hparry
 *
 */
public class Game {
    private String name;
    private Map<String,Integer> points;
    private String[][] grid;
    private int score = 0;

    public Game(String name, Map<String, Integer> points, String[][] grid) {
        this.name = name;
        this.points = points;
        this.grid = grid;
    }

    public void play() {
        Scanner in = new Scanner(System.in);

        System.out.println("You're playing " + name + ".") ;

        while (true) {
            System.out.println("Where do you want to dig (enter x then y)?");

            int x = in.nextInt();
            int y = in.nextInt();

            if (grid[x][y] != null) {
                String treasure = grid[x][y];
                score += points.get(treasure);
                grid[x][y] = null;
                System.out.println("You found " + treasure + "! Your score is " + score + ".");
            } else {
                System.out.println("Sorry, nothing there!");
            }
        }
    }
}
