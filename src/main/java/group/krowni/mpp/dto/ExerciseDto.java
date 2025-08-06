package group.krowni.mpp.dto;

import java.util.List;
import java.util.Set;

public class ExerciseDto {
    private String name;
    private Set<Long> muscleIds;

    public String getName() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public Set<Long> getMuscleIds() {
        return muscleIds;
    }

    public void setMusclesIds(Set<Long> muscleIds) {
        this.muscleIds = muscleIds;
    }
}
