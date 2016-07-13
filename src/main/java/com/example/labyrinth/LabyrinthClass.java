package com.example.labyrinth;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class LabyrinthClass {
    ArrayList<Integer> pathX = new ArrayList<>();
    ArrayList<Integer> pathY = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> labyrinth = new ArrayList<>();
    private int r;
    private boolean pathNotFound = false;
    private String s;
    private long timeSpent;

    public void Route(Context context, int x1, int x2, int y1, int y2) {
        int d = 1000;
        readData(labyrinth, "lab.txt", context);
        labyrinth.get(x1).set(y1, d);
        labyrinth.get(x2).set(y2, 0);
        long startTime = System.currentTimeMillis();
        boolean stop;
        do {
            stop = true;
            for (int y = 0; y < labyrinth.get(0).size(); ++y) {
                for (int x = 0; x < labyrinth.size(); ++x) {
                    if (labyrinth.get(x).get(y) == d) {
                        try {
                            if (labyrinth.get(x).get(y + 1) == 0 || labyrinth.get(x).get(y + 1) > d + 1) {
                                stop = false;
                                labyrinth.get(x).set(y + 1, d + 1);
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                        try {
                            if (labyrinth.get(x).get(y - 1) == 0 || labyrinth.get(x).get(y - 1) > d + 1) {
                                stop = false;
                                labyrinth.get(x).set(y - 1, d + 1);
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                        try {
                            if (labyrinth.get(x - 1).get(y) == 0 || labyrinth.get(x - 1).get(y) > d + 1) {
                                stop = false;
                                labyrinth.get(x - 1).set(y, d + 1);
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                        try {
                            if (labyrinth.get(x + 1).get(y) == 0 || labyrinth.get(x + 1).get(y) > d + 1) {
                                stop = false;
                                labyrinth.get(x + 1).set(y, d + 1);
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
            ++d;
        } while (!stop & labyrinth.get(x2).get(y2) == 0);
        if (labyrinth.get(x2).get(y2) == 0) {
            pathNotFound = true;
        }
        else {
            pathX.add(x2);
            pathY.add(y2);
            while (d != 1000) {
                d--;
                try {
                    if (labyrinth.get(pathX.get(pathX.size() - 1)).get(pathY.get(pathY.size() - 1) - 1) == d) {
                        pathY.add(pathY.get(pathY.size() - 1) - 1);
                        pathX.add(pathX.get(pathX.size() - 1));
                        continue;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
                try {
                    if (labyrinth.get(pathX.get(pathX.size() - 1)).get(pathY.get(pathY.size() - 1) + 1) == d) {
                        pathY.add(pathY.get(pathY.size() - 1) + 1);
                        pathX.add(pathX.get(pathX.size() - 1));
                        continue;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
                try {
                    if (labyrinth.get(pathX.get(pathX.size() - 1) + 1).get(pathY.get(pathY.size() - 1)) == d) {
                        pathY.add(pathY.get(pathY.size() - 1));
                        pathX.add(pathX.get(pathX.size() - 1) + 1);
                        continue;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
                try {
                    if (labyrinth.get(pathX.get(pathX.size() - 1) - 1).get(pathY.get(pathY.size() - 1)) == d) {
                        pathY.add(pathY.get(pathY.size() - 1));
                        pathX.add(pathX.get(pathX.size() - 1) - 1);
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            timeSpent = System.currentTimeMillis() - startTime;
            Collections.reverse(pathX);
            Collections.reverse(pathY);
            r = 0;
            s = "";
            for (int i = 1; i < pathX.size(); i++) {
                if (pathX.get(i) == pathX.get(i - 1)) {
                    if (pathY.get(i) > pathY.get(i - 1)) {
                        s += "1";
                        r++;
                    }
                    else {
                        s += "3";
                        r++;
                    }
                }
                else {
                    if (pathX.get(i) > pathX.get(i - 1)) {
                        s += "2";
                    }
                    else {
                        s += "0";
                    }
                }
            }
        }
    }


    public boolean isPathNotFound() {
        return pathNotFound;
    }

    public ArrayList<Integer> getPathY() {
        return pathY;
    }

    public ArrayList<Integer> getPathX() {
        return pathX;
    }

    public void readData(ArrayList<ArrayList<Integer>> values, String p, Context cont) {
        BufferedReader is;
        try {
            AssetManager am = cont.getAssets();
            is = new BufferedReader(new InputStreamReader(am.open(p)));
            String line = is.readLine();
            while (line != null) {
                ArrayList<Integer> x = new ArrayList<>();
                try {
                    for (int i = 0; i < line.length(); i++) {
                        x.add(Character.getNumericValue(line.charAt(i)));
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                values.add(x);
                line = is.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getS() {
        return s;
    }

    public long getTime() {
        return timeSpent;
    }

    public int getR() {
        return r;
    }
}