package com.abdulahiosoble.healthreach.Models;

/**
 * Created by abdulahiosoble on 9/28/17.
 */

public class Rating {
    private String comment;
    private String problem;
    private Float rating;

    public Rating(String comment, String problem, Float rating) {
        this.comment = comment;
        this.problem = problem;
        this.rating = rating;
    }

    public Rating() {
    }

    public String getComment() {
        return comment;
    }

    public String getProblem() {
        return problem;
    }

    public Float getRating() {
        return rating;
    }
}
