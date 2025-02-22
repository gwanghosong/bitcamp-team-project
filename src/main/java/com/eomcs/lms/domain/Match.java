package com.eomcs.lms.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Match implements Cloneable, Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private int teamNo;
  private int otherTeamNo;
  private int teamSportsId;
  private String title;
  private String contents;
  private int viewCount;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date modifierDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date playDate;
  private String location;
  private String stadiumName;
  private String stadiumLatitude;
  private String stadiumLongitude;
  private String telephone;
  private int cost;
  
  private Team team;
  private TeamMember teamMember;
  private Team otherTeam;
  private TeamTypeSports teamTypeSports;
  private List<MatchApply> matchApply;
  private TeamAges teamAges;
  private TeamLevel teamLevel;
  private List<Tag> tags;
  private Member member;
  private TopLocation topLocation;
  private MiddleLocation middleLocation;
  private MatchApply matchApplly;
  private TeamType teamType;


  @Override
  public String toString() {
    return "Match [no=" + no + ", teamNo=" + teamNo + ", otherTeamNo=" + otherTeamNo
        + ", teamSportsId=" + teamSportsId + ", title=" + title + ", contents=" + contents
        + ", viewCount=" + viewCount + ", createdDate=" + createdDate + ", modifierDate="
        + modifierDate + ", playDate=" + playDate + ", location=" + location + ", stadiumName="
        + stadiumName + ", stadiumLatitude=" + stadiumLatitude + ", stadiumLongitude="
        + stadiumLongitude + ", telephone=" + telephone + ", cost=" + cost + ", team=" + team
        + ", teamMember=" + teamMember + ", otherTeam=" + otherTeam + ", teamTypeSports="
        + teamTypeSports + ", matchApply=" + matchApply + ", teamAges=" + teamAges + ", teamLevel="
        + teamLevel + ", tags=" + tags + ", member=" + member + ", topLocation=" + topLocation
        + ", middleLocation=" + middleLocation + ", matchApplly=" + matchApplly + ", teamType="
        + teamType + "]";
  }
  public TeamType getTeamType() {
    return teamType;
  }
  public void setTeamType(TeamType teamType) {
    this.teamType = teamType;
  }
  public int getNo() {
    return no;
  }
  public void setNo(int no) {
    this.no = no;
  }
  public int getOtherTeamNo() {
    return otherTeamNo;
  }
  public void setOtherTeamNo(int otherTeamNo) {
    this.otherTeamNo = otherTeamNo;
  }
  public String getContents() {
    return contents;
  }
  public void setContents(String contents) {
    this.contents = contents;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  public Date getModifierDate() {
    return modifierDate;
  }
  public void setModifierDate(Date modifierDate) {
    this.modifierDate = modifierDate;
  }
  public Date getPlayDate() {
    return playDate;
  }
  public void setPlayDate(Date playDate) {
    this.playDate = playDate;
  }
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  public String getStadiumName() {
    return stadiumName;
  }
  public void setStadiumName(String stadiumName) {
    this.stadiumName = stadiumName;
  }
  public String getStadiumLatitude() {
    return stadiumLatitude;
  }
  public void setStadiumLatitude(String stadiumLatitude) {
    this.stadiumLatitude = stadiumLatitude;
  }
  public String getStadiumLongitude() {
    return stadiumLongitude;
  }
  public void setStadiumLongitude(String stadiumLongitude) {
    this.stadiumLongitude = stadiumLongitude;
  }
  public int getCost() {
    return cost;
  }
  public void setCost(int cost) {
    this.cost = cost;
  }
  public Team getOtherTeam() {
    return otherTeam;
  }
  public void setOtherTeam(Team otherTeam) {
    this.otherTeam = otherTeam;
  }
  public List<MatchApply> getMatchApply() {
    return matchApply;
  }
  public void setMatchApply(List<MatchApply> matchApply) {
    this.matchApply = matchApply;
  }
  public List<Tag> getTags() {
    return tags;
  }
  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }
  public Member getMember() {
    return member;
  }
  public void setMember(Member member) {
    this.member = member;
  }
  public MiddleLocation getMiddleLocation() {
    return middleLocation;
  }
  public void setMiddleLocation(MiddleLocation middleLocation) {
    this.middleLocation = middleLocation;
  }
  public MatchApply getMatchApplly() {
    return matchApplly;
  }
  public void setMatchApplly(MatchApply matchApplly) {
    this.matchApplly = matchApplly;
  }
  public int getTeamNo() {
    return teamNo;
  }
  public void setTeamNo(int teamNo) {
    this.teamNo = teamNo;
  }
  public int getTeamSportsId() {
    return teamSportsId;
  }
  public void setTeamSportsId(int teamSportsId) {
    this.teamSportsId = teamSportsId;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public int getViewCount() {
    return viewCount;
  }
  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }
  public String getTelephone() {
    return telephone;
  }
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  public Team getTeam() {
    return team;
  }
  public void setTeam(Team team) {
    this.team = team;
  }
  public TeamMember getTeamMember() {
    return teamMember;
  }
  public void setTeamMember(TeamMember teamMember) {
    this.teamMember = teamMember;
  }
  public TeamTypeSports getTeamTypeSports() {
    return teamTypeSports;
  }
  public void setTeamTypeSports(TeamTypeSports teamTypeSports) {
    this.teamTypeSports = teamTypeSports;
  }
  public TeamAges getTeamAges() {
    return teamAges;
  }
  public void setTeamAges(TeamAges teamAges) {
    this.teamAges = teamAges;
  }
  public TeamLevel getTeamLevel() {
    return teamLevel;
  }
  public void setTeamLevel(TeamLevel teamLevel) {
    this.teamLevel = teamLevel;
  }
  public TopLocation getTopLocation() {
    return topLocation;
  }
  public void setTopLocation(TopLocation topLocation) {
    this.topLocation = topLocation;
  }

}
