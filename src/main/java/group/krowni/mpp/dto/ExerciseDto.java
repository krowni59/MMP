package group.krowni.mpp.dto;

import java.util.List;

public class ExerciseDto {
    private String name;
    private List<String> muscles;

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public List<String> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<String> muscles) {
        this.muscles = muscles;
    }
}
