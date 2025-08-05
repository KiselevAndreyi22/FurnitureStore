import { Component } from '@angular/core';
import { ProjectListComponent} from '../../../features/project/project-list/project-list.component';

@Component({
  selector: 'app-projects-page',
  imports: [
    ProjectListComponent
  ],
  templateUrl: './product-page.component.html',
  styleUrl: './product-page.component.scss',
})
export class ProjectsPageComponent {}
