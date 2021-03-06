import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'umfrage',
        loadChildren: () => import('./umfrage/umfrage.module').then(m => m.JpollUmfrageModule),
      },
      {
        path: 'antwort',
        loadChildren: () => import('./antwort/antwort.module').then(m => m.JpollAntwortModule),
      },
      {
        path: 'userantwort',
        loadChildren: () => import('./userantwort/userantwort.module').then(m => m.JpollUserantwortModule),
      },
      {
        path: 'user-antwort',
        loadChildren: () => import('./user-antwort/user-antwort.module').then(m => m.JpollUserAntwortModule),
      },
      {
        path: 'antwortby-user',
        loadChildren: () => import('./antwortby-user/antwortby-user.module').then(m => m.JpollAntwortbyUserModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JpollEntityModule {}
