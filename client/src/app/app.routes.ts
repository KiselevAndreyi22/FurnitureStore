// или если создавал форму:
import { ProductFormComponent } from './product-form/product-form.component';
import { Routes } from '@angular/router';
import { RegisterPageComponent} from './pages/authPages/registerPages/register-page.component';
import { LoginPageComponent} from './pages/authPages/loginPages/login-page.component';
import { LayoutComponent} from './layout/layout.component';
import { DashboardComponent} from './features/dashboard/dashboard.component';
import { canActivateAuth } from './core/guards/auth.guard';
import {CataloguePageComponent} from './pages/cataloguePage/catalogue-page.component';

export const routes: Routes = [
  {
    path: '',
    component: LayoutComponent,
    children: [
      { path: 'profile', component: DashboardComponent },
      { path: 'catalogue', component: CataloguePageComponent },
      { path: 'create-product', component: ProductFormComponent }
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
