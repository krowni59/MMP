package group.krowni.mpp.dto;

import group.krowni.mpp.entity.Exercise;
import group.krowni.mpp.entity.User;

import java.util.List;
import java.util.Map;

public class SeanceDto {
    private String name;
    private String description;
    private List<Long> exerciseIds;   // plus clair pour l’API
    private Map<Long,Integer> reps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getExerciseIds() {
        return exerciseIds;
    }

    public void setExerciseIds(List<Long> exerciseIds) {
        this.exerciseIds = exerciseIds;
    }

    public Map<Long, Integer> getReps() {
        return reps;
    }

    public void setReps(Map<Long, Integer> reps) {
        this.reps = reps;
    }
}
