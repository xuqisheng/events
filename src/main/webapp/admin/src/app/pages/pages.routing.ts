import { Routes, RouterModule }  from '@angular/router';
import { Pages } from './pages.component';
// noinspection TypeScriptValidateTypes
const routes: Routes = [
  {
    path: 'login',
    loadChildren: () => System.import('./user/login/login.module')
  },
  {
    path: 'register',
    loadChildren: () => System.import('./user/register/register.module')
  },
  {
    path: 'forgot-password',
    loadChildren: () => System.import('./user/forgot-password/forgot-password.module')
  },
  {
    path: 'pages',
    component: Pages,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', loadChildren: () => System.import('./dashboard/dashboard.module') },
      { path: 'event', loadChildren: () => System.import('./event/event.module') },
      { path: 'business', loadChildren: () => System.import('./business/business.module') },
      { path: 'personal', loadChildren: () => System.import('./personal/personal.module') },

      { path: 'editors', loadChildren: () => System.import('./editors/editors.module') },
      //{ path: 'components', loadChildren: () => System.import('./components/components.module') }
      { path: 'charts', loadChildren: () => System.import('./charts/charts.module') },
      { path: 'ui', loadChildren: () => System.import('./ui/ui.module') },
      { path: 'forms', loadChildren: () => System.import('./forms/forms.module') },
      { path: 'tables', loadChildren: () => System.import('./tables/tables.module') },
      { path: 'maps', loadChildren: () => System.import('./maps/maps.module') }
    ]
  }
];

export const routing = RouterModule.forChild(routes);
