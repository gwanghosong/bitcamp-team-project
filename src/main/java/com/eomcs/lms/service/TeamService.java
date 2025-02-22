package com.eomcs.lms.service;

import java.util.List;
import com.eomcs.lms.domain.Member;
import com.eomcs.lms.domain.MiddleLocation;
import com.eomcs.lms.domain.Team;
import com.eomcs.lms.domain.TeamAges;
import com.eomcs.lms.domain.TeamLevel;
import com.eomcs.lms.domain.TeamMember;
import com.eomcs.lms.domain.TeamType;
import com.eomcs.lms.domain.TeamTypeSports;
import com.eomcs.lms.domain.TopLocation;

public interface TeamService {
  List<Team> teamList1(); // 관리자 팀조회
  List<Team> teamList2(); // 유저 팀조회
  List<Member> memberList(int pageNo, int pageSize); 
  List<TeamMember> getTeamMemberListByMemberNo(int no);
  List<TeamLevel> teamLevelList(); 
  
  List<TeamType> teamTypeList();
  List<TeamAges> teamAgeList();
  List<TeamTypeSports> sportsTypeList();
  List<TeamMember> teamMemberList();
  List<TeamMember> getTeamMember(int no);
  List<Team> search(String keyword);
  Team getTeamSportsType(int no);
  int addTeam(Team team);
  int addTeamLeader(TeamMember teamMember);
  int addMember(Member member);
  Team getTeam(int no);
  Member getMember(int no);
  int update(Team team);
  int deleteTeam(int no);
  int deleteMember(int no);
  int teamSize();
  int memberSize();
  int size();
  Team checkName(String teamName);
  List<TopLocation> topLocationList();
  List<MiddleLocation> middleLocationList(int no);
}
