package group.krowni.mpp.dto;

import java.util.List;

public class ProgrammeDto {
    private String name;
    private String description;
    private List<ProgrammeTemplateEntryDto> template;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<ProgrammeTemplateEntryDto> getTemplate() { return template; }
    public void setTemplate(List<ProgrammeTemplateEntryDto> template) { this.template = template; }
}