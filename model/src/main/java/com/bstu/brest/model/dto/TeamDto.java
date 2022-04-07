package com.bstu.brest.model.dto;


import java.math.BigDecimal;

/**
 * POJO Team for model.
 */
public class TeamDto {

    /**
     * Team Id.
     */
    private Integer teamId;

    /**
     * Team Name.
     */
    private String teamName;

    /**
     * Average salary of the Team.
     */
    private BigDecimal avgSalary;

    /**
     * Number of footballers of the Team.
     */
    private Integer numberOfFootballers;

    /**
     * Constructor without arguments.
     */
    public TeamDto() {
    }

    /**
     * Constructor with team name.
     *
     * @param teamName team name
     */
    public TeamDto(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Returns <code>Integer</code> representation of this teamId.
     *
     * @return teamId Team Id.
     */
    public Integer getTeamId() {
        return teamId;
    }

    /**
     * Sets the team's identifier.
     *
     * @param teamId team Id.
     */
    public void setTeamId(final Integer teamId) {
        this.teamId = teamId;
    }

    /**
     * Returns <code>String</code> representation of this teamName.
     *
     * @return teamName team Name.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Sets the team's name.
     *
     * @param teamName team Name.
     */
    public void setTeamName(final String teamName) {
        this.teamName = teamName;
    }

    /**
     * Returns <code>BigDecimal</code> representation of average salary
     * for the Team.
     *
     * @return teamId.
     */
    public BigDecimal getAvgSalary() {
        return avgSalary;
    }

    /**
     * Sets the team's number of footballers.
     *
     * @param numberOfFootballers number of footballers.
     */
    public void setNumberOfFootballers(final Integer numberOfFootballers) {
        this.numberOfFootballers = numberOfFootballers;
    }

    /**
     * Returns <code>Integer</code> representation of number of footballers
     * for the Team.
     *
     * @return teamId.
     */
    public Integer getNumberOfFootballers() {
        return numberOfFootballers;
    }

        /**
         * Sets the team's average salary.
         *
         * @param avgSalary Average salary.
         */
    public void setAvgSalary(final BigDecimal avgSalary) {
        this.avgSalary = avgSalary;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "TeamDto{"
                + "teamId=" + teamId
                + ", teamName='" + teamName + '\''
                + ", numberOfFootballer='" + numberOfFootballers + '\''
                + ", avgSalary=" + avgSalary
                + '}';
    }
}
