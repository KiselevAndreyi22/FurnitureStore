export interface IUser {
  username: string;
  email: string;
  avatarUrl: string;
}

export interface IUserStats {
  activeProjects: number;
  completedProjects: number;
  overdueTasks: number;
  completedTasks: number;
}
