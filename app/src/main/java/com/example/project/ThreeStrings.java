package com.example.project;

public class ThreeStrings {
    private String left;
    private String right;
    private String right2;
    private String centre;

    public ThreeStrings(String left, String right, String centre, String right2) {
        this.left = left;
        this.right = right;
        this.right2 = right2;
        this.centre = centre;
    }

    public String getCentre() {
        return centre;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }
    public String getRight2() {
        return right2;
    }
}