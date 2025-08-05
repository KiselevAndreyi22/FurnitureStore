import { Routes } from '@angular/router';
import { RegisterPageComponent} from './pages/authPages/registerPages/register-page.component';
import { LoginPageComponent} from './pages/authPages/loginPages/login-page.component';
import { LayoutComponent} from './layout/layout.component';
import { DashboardComponent} from './features/dashboard/dashboard.component';
import { ProjectsPageComponent} from './pages/authPages/productPages/product-page.component';
import { canActivateAuth } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'profile', component: DashboardComponent },
      { path: 'projects', component: ProjectsPageComponent },
    ],
    canActivate: [canActivateAuth],
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
