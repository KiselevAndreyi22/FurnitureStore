import { Routes } from '@angular/router';
import { RegisterPageComponent} from './pages/authPages/registerPages/register-page.component';
import { LoginPageComponent} from './pages/authPages/loginPages/login-page.component';
import { LayoutComponent} from './layout/layout.component';
import { DashboardComponent} from './features/dashboard/dashboard.component';
import { ProjectsPageComponent} from './pages/authPages/productPages/product-page.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'profile', component: DashboardComponent },
      { path: 'projects', component: ProjectsPageComponent },
    ],
  },
  {
    path: 'sign-up',
    component: RegisterPageComponent,
  },
  {
    path: 'login',
    component: LoginPageComponent,
  },
];
